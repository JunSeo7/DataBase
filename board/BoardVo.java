package board;

public class BoardVo {

	private String no;
	private String title;
	private String content;
	private String writerNo;
	private String enrollDate;
	private String delYn;

	public BoardVo() {

	}

	public BoardVo(String no, String title, String content, String writerNo, String enrollDate, String delYn) {
		this.no = no;
		this.title = title;
		this.content = content;
		this.writerNo = writerNo;
		this.enrollDate = enrollDate;
		this.delYn = delYn;
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

	public String getWriterNo() {
		return writerNo;
	}

	public void setWriterNo(String writerNo) {
		this.writerNo = writerNo;
	}

	public String getEnrollDate() {
		return enrollDate;
	}

	public void setEnrollDate(String enrollDate) {
		this.enrollDate = enrollDate;
	}

	public String getDelYn() {
		return delYn;
	}

	public void setDelYn(String delYn) {
		this.delYn = delYn;
	}

	@Override
	public String toString() {
		return "BoardVo [no=" + no + ", title=" + title + ", content=" + content + ", writerNo=" + writerNo
				+ ", enrollDate=" + enrollDate + ", delYn=" + delYn + "]";
	}
	
	

}
