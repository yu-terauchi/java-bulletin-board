package practice.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 掲示板で入力された値を入れるフォーム
 * 
 * @author yu.terauchi
 *
 */
public class ArticleForm {
	/** 記事投稿者の名前 */
	@NotBlank(message = "名前を入力してください")
	@Size(min=1,max=50,message = "名前は50文字以内で入力してください")	
	private String name;
	/** 記事内容 */
	@NotBlank(message = "コメントを入力してください")
	@Size(min=1,max=300,message = "記事内容は300字以内で入力してください")
	private String content;

	public ArticleForm() {
		name = null;
		content = null;
	}

	public ArticleForm(String name, String content) {
		super();
		this.name = name;
		this.content = content;

	}

	@Override
	public String toString() {
		return "ArticleForm [name=" + name + ", content=" + content + "]";
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
