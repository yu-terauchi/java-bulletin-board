package practice.form;

/**
 * 掲示板で入力された値を入れるフォーム
 * 
 * @author yu.terauchi
 *
 */
public class ArticleForm {
	/** 記事投稿者の名前 */
	private String name;
	/** 記事内容 */
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
