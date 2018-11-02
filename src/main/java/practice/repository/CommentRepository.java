package practice.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import practice.domain.Comment;

/**
 * commentsテーブルにアクセスしコメント情報を操作するリポジトリ.
 * 
 * @author yu.terauchi
 *
 */
@Repository
public class CommentRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;
	private static final RowMapper<Comment> COMMENT_ROW_MAPPER = (rs, i) -> {
		Comment comment = new Comment();
		comment.setId(rs.getInt("id"));
		comment.setName(rs.getString("name"));
		comment.setContent(rs.getString("content"));
		comment.setArticleId(rs.getInt("article_id"));
		return comment;
	};

	/**
	 * 投稿されたすべての記事情報を引き出すメソッド.
	 * 
	 * @return 記事リスト
	 */
	public List<Comment> findByArticleId(int articleId) {
		String sql = "SELECT id,name,content,article_id FROM comments WHERE article_id = :article_id ORDER BY id DESC;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("article_id", articleId);
		List<Comment> commentList = template.query(sql, param, COMMENT_ROW_MAPPER);
		return commentList;
	}

	/**
	 * 新規記事情報をarticlesテーブルに追加するメソッド.
	 * 
	 * @param article　記事情報をもつオブジェクト
	 */
	public void Insert(Comment comment) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(comment);
		String Sql = "INSERT INTO comments(name,content,article_id) VALUES(:name,:content,:articleId);";
		template.update(Sql, param);
	}

	/**
	 * 指定されたidの記事を削除するメソッド.
	 * 
	 * @param id 記事のID
	 */
	public void deleteByArticleId(Integer id) {
		String sql = "DELETE FROM comments WHERE id = :id ";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		template.update(sql, param);
	}
}
