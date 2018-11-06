package practice.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import practice.domain.Article;
import practice.domain.Comment;

/**
 * articlesテーブルにアクセスし、記事の追加や削除の操作をするリポジトリ.
 * 
 * @author yu.terauchi
 *
 */
@Repository
public class ArticleRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	// private static final RowMapper<Article> ARTICLE_ROW_MAPPER = (rs, i) -> {
	// Article article = new Article();
	// article.setId(rs.getInt("a_id"));
	// article.setName(rs.getString("a_name"));
	// article.setContent(rs.getString("a_content"));
	// return article;
	// };

	/**
	 * ARTICLE_RESULT_SET_EXTRACTORにListをセット
	 */
	private static final ResultSetExtractor<List<Article>> ARTICLE_RESULT_SET_EXTRACTOR = (rs) -> {
		List<Article> articleList = new ArrayList<Article>();
		List<Comment> commentList = null;

		Article article = null;
		int previousArticleId = 0;

		while (rs.next()) {
			int nowArticleId = rs.getInt("a_id");

			// 前の記事のidと今の記事のidが異なるとき
			// → 新しい記事を投稿するときに新しい記事オブジェクトを作って中身をセット
			if (previousArticleId != nowArticleId) {
				article = new Article();
				article.setId(nowArticleId);
				article.setName(rs.getString("a_name"));
				article.setContent(rs.getString("a_content"));

				commentList = new ArrayList<Comment>();
				article.setCommentList(commentList);
			}

			// articleオブジェクトに情報を詰めてArticle型のlistにadd
			articleList.add(article);
			// while文の直下にコメントオブジェクトを作ってコメントの情報をつて詰めてcommentListにaddする
			// articleがないと新しいcom_idは自動生成されない（＝0）
			// 逆説的に言うと、何もコメントしてないときにコメントIDが0,nameとcontentがnullで表示されるなら
			// コメントIDが0の場合はコメントリストに0,nullをセットしてaddしないように条件を付ければよい
			int comId = rs.getInt("com_id");
			if (comId != 0) {
				Comment comment = new Comment();
				comment.setId(rs.getInt("com_id"));
				comment.setName(rs.getString("com_name"));
				comment.setContent(rs.getString("com_content"));
				comment.setArticleId(rs.getInt("article_id"));
				commentList.add(comment);
			}
			// nextする前の記事のidをpreviousArticleIdに保管しておく
			previousArticleId = article.getId();
			// nextしてwhile文内をループします
		}
		return articleList;
	};

	/**
	 * 投稿されたすべての記事情報を引き出すメソッド.
	 * 
	 * @return 記事リスト
	 */
	// public List<Article> findAll() {
	// String sql = "SELECT id,name,content FROM articles ORDER BY id DESC";
	// List<Article> articleList = template.query(sql,ARTICLE_ROW_MAPPER);
	// return articleList;
	// }

	/**
	 * 記事とコメントの両方を一括で読み込む
	 * 
	 * @return 記事コメントリスト
	 */
	public List<Article> findAllver2() {
		String sql = "SELECT a.id AS a_id,a.name AS a_name,a.content AS a_content,com.id AS com_id,com.name AS com_name,com.content AS com_content,com.article_id FROM articles a LEFT OUTER JOIN comments com ON a.id = com.article_id ORDER BY a.id DESC;";
		List<Article> articleList = template.query(sql, ARTICLE_RESULT_SET_EXTRACTOR);
		return articleList;
	}

	/**
	 * 新規記事情報をarticlesテーブルに追加するメソッド.
	 * 
	 * @param article
	 *            記事情報をもつオブジェクト
	 */
	public void insert(Article article) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(article);
		String Sql = "INSERT INTO articles(name,content) VALUES(:name,:content);";
		template.update(Sql, param);
	}

	/**
	 * 指定されたidの記事を削除するメソッド.
	 * 
	 * @param id
	 *            記事のID
	 */
	public void deleteById(Integer id) {
		System.out.println(id);
		String sql = "DELETE FROM articles WHERE id = :id ";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		template.update(sql, param);
	}
}
