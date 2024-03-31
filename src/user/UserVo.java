package user;

public class UserVo {

	private String no;
	private String id;
	private String name;
	private String pwd;
	private String email;

	public UserVo(String no, String id, String name, String pwd, String email) {
		super();
		this.no = no;
		this.id = id;
		this.name = name;
		this.pwd = pwd;
		this.email = email;
	}

	public UserVo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "UserVo [no=" + no + ", id=" + id + ", name=" + name + ", pwd=" + pwd + ", email=" + email + "]";
	}

}
