package practice.domain;

import java.util.List;

/**
 * 掲示板の記事を表すドメイン.
 * 
 * @author yu.terauchi
 *
 */
public class Article {
	/** 記事のID */
	private Integer id;
	/** 記事投稿者の名前 */
	private String name;
	/** 記事内容 */
	private String content;
	/**コメントのリスト*/
	private List<Comment>commentList;

	public Article() {
		id = 0;
		name = null;
		content = null;
		commentList = null;
	}

	public Article(Integer id, String name, String content, List<Comment> commentList) {
		super();
		this.id = id;
		this.name = name;
		this.content = content;
		this.commentList = commentList;
	}

	@Override
	public String toString() {
		return "Article [id=" + id + ", name=" + name + ", content=" + content + ", commentList=" + commentList + "]";
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

	public List<Comment> getCommentList() {
		return commentList;
	}

	public void setCommentList(List<Comment> commentList) {
		this.commentList = commentList;
	}
	
}

