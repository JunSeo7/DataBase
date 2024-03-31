package event;

public class EventVo {

	private String eventno;
	private String eventname;
	private String content;
	private String eventdate;
	private String userid;

	public EventVo(String eventno, String eventname, String content, String eventdate, String userid) {
		super();
		this.eventno = eventno;
		this.eventname = eventname;
		this.content = content;
		this.eventdate = eventdate;
		this.userid = userid;
	}

	public EventVo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getEventno() {
		return eventno;
	}

	public void setEventno(String eventno) {
		this.eventno = eventno;
	}

	public String getEventname() {
		return eventname;
	}

	public void setEventname(String eventname) {
		this.eventname = eventname;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getEventdate() {
		return eventdate;
	}

	public void setEventdate(String eventdate) {
		this.eventdate = eventdate;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	@Override
	public String toString() {
		return "EventVo [eventno=" + eventno + ", eventname=" + eventname + ", content=" + content + ", eventdate="
				+ eventdate + ", userid=" + userid + "]";
	}

}
