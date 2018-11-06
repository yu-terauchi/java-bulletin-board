package practice.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import practice.domain.Article;
import practice.domain.Comment;
import practice.form.ArticleForm;
import practice.form.CommentForm;
import practice.repository.ArticleRepository;
import practice.repository.CommentRepository;

/**
 * 掲示板の記事および記事に付随するコメントを操作するコントローラ.
 * 
 * @author yu.terauchi
 *
 */
@Controller
@Transactional
@RequestMapping("/article")
public class ArticleController {

	@Autowired
	private ArticleRepository articleRepository;

	@Autowired
	private CommentRepository commentRepository;

	@ModelAttribute
	public ArticleForm setUpForm() {
		return new ArticleForm();
	}
	@ModelAttribute
	public CommentForm setUpForm2() {
		return new CommentForm();
	}

	/**
	 * 記事情報とコメント情報をすべて読み出し、掲示板のページへ移動するメソッド.
	 * 
	 * @param model
	 *            モデル
	 * @param comment
	 *            コメント
	 * @return 掲示板画面
	 */
	@RequestMapping("/index")
	public String index(Model model) {
		List<Article> articleList = articleRepository.findAllver2();
		// for(Article article :articleList) {//articleの中のidを使ってcommentListを取ってくる
		// List<Comment> commentList =
		// commentRepository.findByArticleId(article.getId());
		// article.setCommentList(commentList);
		// }

		model.addAttribute("articleList", articleList);

		System.out.println("掲示板へ飛びます");
		return "/PutArticle";
	}

	/**
	 * 掲示板に記事を投稿するメソッド.
	 * 
	 * @param name
	 *            記事の投稿者名
	 * @param content
	 *            記事内容
	 * @return 記事が投稿された掲示板画面
	 */
	@RequestMapping("/insertArticle")
	public String insertArticle(@Validated ArticleForm articleForm, BindingResult result,
			RedirectAttributes redirectAttributes, Model model) {
		if (result.hasErrors()) {
			return index(model);//index()メソッドを呼んで引数にmodelを渡してあげることでリクエストスコ―プを指定することができるので最初のindexメソッドの中身の処理を再現できる
		}
		Article article = new Article();

		//articleFormの中身をarticleオブジェクトにコピー
		BeanUtils.copyProperties(articleForm, article);

		//リポジトリにはformではなくドメインに詰めて送る
		articleRepository.insert(article);

		return "redirect:/article/index"; // redirect:を付けることで2重送信防止
	}

	/**
	 * 記事にコメントを追加するメソッド.
	 * 
	 * @param name
	 *            コメント者名
	 * @param content
	 *            コメント内容
	 * @param articleId
	 *            コメントを投稿する記事のID
	 * @return 記事に対するコメントを追加した掲示板画面
	 */
	@RequestMapping("/insertComment")
	public String insertComment(@Validated CommentForm commentForm, BindingResult commentResult,
			RedirectAttributes redirectAttributes, Model model) {
		
		
		if (commentResult.hasErrors()) {
			return index(model);
		}

		Comment comment = new Comment();
		BeanUtils.copyProperties(commentForm, comment);
		commentRepository.insert(comment);// commentオブジェクトにリクエストパラメータを詰めてリポジトリのInsertへ
		return "redirect:/article/index";
	}

	/**
	 * 投稿された記事および記事に付随するコメントを削除するメソッド.
	 * 
	 */
	@RequestMapping("/deleteArticle")
	public String deleteArticle(Integer articleId) {
		System.out.println("掲示板の記事を削除します");
		commentRepository.deleteByArticleId(articleId);
		articleRepository.deleteById(articleId);
		return "redirect:/article/index";
	}

}
