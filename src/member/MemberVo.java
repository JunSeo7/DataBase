package member;

public class MemberVo {

	private String id;
	private String pwd;
	private String nick;

	public MemberVo(String id, String pwd, String nick) {
		super();
		this.id = id;
		this.pwd = pwd;
		this.nick = nick;
	}

	public MemberVo() {

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	@Override
	public String toString() {
		return "MemberVo [아이디=" + id + ", 비번=" + pwd + ", 닉네임=" + nick + "]";
	}

}
