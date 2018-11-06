package practice.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * コメントフォーム.
 * 
 * @author yu.terauchi
 *
 */
public class CommentForm {
	/**記事のID*/
	private Integer articleId;
	/**コメント者名*/
	@NotBlank(message = "名前を入力してください")
	@Size(min=1,max=50,message = "名前は50文字以内で入力してください")
	private String name;
	/**コメント内容*/
	@NotBlank(message = "コメントを入力してください")
	@Size(min=1,max=300,message = "コメント内容は300字以内で入力してください")
	private String content;
	
	public CommentForm() {
		articleId = null;
		name = null;
		content = null;
		
	}

	public CommentForm(Integer articleId, String name, String content) {
		super();
		this.articleId = articleId;
		this.name = name;
		this.content = content;
	}
	
	@Override
	public String toString() {
		return "CommentForm [articleId=" + articleId + ", name=" + name + ", content=" + content + "]";
	}
	public Integer getArticleId() {
		return articleId;
	}
	public void setArticleId(Integer articleId) {
		this.articleId = articleId;
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
	

}
