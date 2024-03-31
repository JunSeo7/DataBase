package user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import main.Main;
import util.JDBCTemplate;

public class UserController {

	public void printMenu() throws Exception {

		System.out.println("1. 회원가입");
		System.out.println("2. 로그인");

		System.out.print("번호를 입력하세요 : ");
		String input = Main.SC.nextLine();

		switch (input) {
		case "1":
			join();
			break;
		case "2":
			login();
			break;
		default:
			System.out.println("잘못입력하셨습니다.");
		}

	}

	private void join() throws Exception {
		System.out.println("-----------------------");

		Connection conn = JDBCTemplate.getConn();

		String sql = "INSERT INTO USERS(NO, ID,NAME,PWD,EMAIL) VALUES(SEQ_USER_ID.NEXTVAL,?,?,?,?)";

		System.out.print("아이디 : ");
		String id = Main.SC.nextLine();
		System.out.print("이름 : ");
		String name = Main.SC.nextLine();
		System.out.print("패스워드 : ");
		String pwd = Main.SC.nextLine();
		System.out.print("이메일 : ");
		String email = Main.SC.nextLine();

		// SQL 실행을 위한 statement 준비
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, id);
		pstmt.setString(2, name);
		pstmt.setString(3, pwd);
		pstmt.setString(4, email);

		// statement 에 SQL 담아주고 실행 및 결과 리턴받기
		int result = pstmt.executeUpdate();

		// 결과 출력
		if (result != 1) {
			throw new RuntimeException("회원가입 실패");
		}

		System.out.println("회원가입 성공");

	}

	private void login() throws Exception {
		System.out.println("-----------------------");

		Connection conn = JDBCTemplate.getConn();

		String sql = "SELECT * FROM USERS WHERE ID = ? AND PWD = ?";

		System.out.print("아이디 : ");
		String id = Main.SC.nextLine();
		System.out.print("패스워드 : ");
		String pwd = Main.SC.nextLine();

		// SQL 실행을 위한 statement 준비
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, id);
		pstmt.setString(2, pwd);

		// statement 에 SQL 담아주고 실행 및 결과 리턴받기
		ResultSet rs = pstmt.executeQuery();

		// 결과 출력
		UserVo vo = null;
		if (rs.next()) {
			String dbNo = rs.getString("NO");
			String dbId = rs.getString("ID");
			String dbName = rs.getString("NAME");
			String dbPwd = rs.getString("PWD");
			String dbEmail = rs.getString("EMAIL");

			vo = new UserVo(dbNo, dbId, dbName, dbPwd, dbEmail);

		}
		if (vo == null) {
			System.out.println("로그인 실패");
		}
		System.out.println(vo.getName() + " 님 환영합니다 ~ !");
		Main.loginUser = vo;
	}

}
