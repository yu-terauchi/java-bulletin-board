package practice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import practice.domain.Article;
import practice.domain.Comment;
import practice.form.ArticleForm;
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

	/**
	 * 記事情報とコメント情報をすべて読み出し、掲示板のページへ移動するメソッド.
	 * 
	 * @param model　モデル
	 * @param comment　コメント
	 * @return　掲示板画面
	 */
	@RequestMapping("/index")
	public String index(Model model,Comment comment) {
		List<Article> articleList = articleRepository.findAll();
		for(Article article :articleList) {//articleの中のidを使ってcommentListを取ってくる
			List<Comment> commentList = commentRepository.findByArticleId(article.getId());
			article.setCommentList(commentList);
		}
		
		model.addAttribute("articleList", articleList);
		
		
		System.out.println("掲示板へ飛びます");
		return "/PutArticle";
	}

	/**
	 * 掲示板に記事を投稿するメソッド.
	 * 
	 * @param name　記事の投稿者名
	 * @param content　記事内容
	 * @return 記事が投稿された掲示板画面
	 */
	@RequestMapping("/insertArticle")
	public String insertArticle(String name, String content) {
		Article article = new Article();
		article.setName(name);
		article.setContent(content);
		articleRepository.Insert(article);

		return "redirect:/article/index";
	}

	/**
	 * 記事にコメントを追加するメソッド.
	 * 
	 * @param name　コメント者名
	 * @param content　コメント内容
	 * @param articleId　コメントを投稿する記事のID
	 * @return 記事に対するコメントを追加した掲示板画面
	 */
	@RequestMapping("/insertComment")
	public String insertComment(String name, String content,String articleId) {
		System.out.println(articleId);
		Comment comment = new Comment();
		comment.setName(name);
		comment.setContent(content);
		comment.setArticleId(Integer.parseInt(articleId));
		commentRepository.Insert(comment);
		return "redirect:/article/index";
	}

	/**
	 * 投稿された記事および記事に付随するコメントを削除するメソッド.
	 * 
	 * @return 投稿された記事および記事に付随するコメントを削除した後の掲示板画面
	 */
	@RequestMapping("/deleteArticle")
	public void deleteArticle(Integer id) {
		articleRepository.deleteById(id);
		commentRepository.deleteById(id);
	}

}
