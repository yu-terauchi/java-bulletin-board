package practice.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import practice.domain.Article;

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

	private static final RowMapper<Article> ARTICLE_ROW_MAPPER = (rs, i) -> {
		Article article = new Article();
		article.setId(rs.getInt("id"));
		article.setName(rs.getString("name"));
		article.setContent(rs.getString("content"));
		return article;
	};

	/**
	 * 投稿されたすべての記事情報を引き出すメソッド.
	 * 
	 * @return　記事リスト
	 */
	public List<Article> findAll() {
		String sql = "SELECT id,name,content FROM articles ORDER BY id DESC;";
		List<Article> articleList = template.query(sql,ARTICLE_ROW_MAPPER);
		return articleList;
	}

	/**
	 * 新規記事情報をarticlesテーブルに追加するメソッド.
	 * 
	 * @param article 記事情報をもつオブジェクト
	 */
	public void Insert(Article article) {
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
		String sql = "DELETE FROM articles WHERE id = :id ";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		template.update(sql, param);
	}
}
