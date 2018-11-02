package practice.domain;

/**
 * コメントを表すドメイン.
 * 
 * @author yu.terauchi
 *
 */
public class Comment {
	/** ID */
	private Integer id;
	/** コメント主の名前 */
	private String name;
	/** コメント内容 */
	private String content;
	/** コメントした記事のID */
	private Integer articleId;

	public Comment() {
		id = 0;
		name = null;
		content = null;
		articleId = 0;
	}

	public Comment(Integer id, String name, String content, Integer articleID) {
		super();
		this.id = id;
		this.name = name;
		this.content = content;
		this.articleId = articleID;
	}

	@Override
	public String toString() {
		return "Comment [id=" + id + ", name=" + name + ", content=" + content + ", articleID=" + articleId + "]";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getArticleId() {
		return articleId;
	}

	public void setArticleId(Integer articleId) {
		this.articleId = articleId;
	}

}
