package board;

public class BoardVo {

	private String no;
	private String title;
	private String content;
	private String enrolldate;

	public BoardVo(String no, String title, String content, String enrolldate) {
		this.no = no;
		this.title = title;
		this.content = content;
		this.enrolldate = enrolldate;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getEnrolldate() {
		return enrolldate;
	}

	public void setEnroll_date(String enrolldate) {
		this.enrolldate = enrolldate;
	}

	@Override
	public String toString() {
		return "BoardVo [no=" + no + ", title=" + title + ", content=" + content + ", enrolldate=" + enrolldate + "]";
	}

}
