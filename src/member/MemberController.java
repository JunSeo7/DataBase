package member;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import main.Main;

public class MemberController {

	public void printMenu() throws Exception {
		System.out.println("----- MEMBER -----");
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
			System.out.println("잘못 입력하셨습니다.");
			return;
		}

	}

	private void join() throws Exception {

		System.out.println("----- 회원가입 -----");
		
		Connection conn = getCon();
		System.out.print("아이디 : ");
		String id1 = Main.SC.nextLine();
		System.out.print("비밀번호 : ");
		String pwd1 = Main.SC.nextLine();
		System.out.print("닉네임 : ");
		String nick1 = Main.SC.nextLine();

		String sql = "INSERT INTO MEMBER(ID,PWD,NICK) VALUES( ?, ?, ? )";

		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, id1);
		pstmt.setString(2, pwd1);
		pstmt.setString(3, nick1);

		int result = pstmt.executeUpdate();

		if (result != 1) {
			throw new RuntimeException("회원가입 실패 ...");
		}
		System.out.println("회원가입 성공 !");

	}

	private void login() throws Exception {
		System.out.println("----- 로그인 -----");

		Connection conn = getCon();
		System.out.print("아이디 : ");
		String id1 = Main.SC.nextLine();
		System.out.print("비밀번호 : ");
		String pwd1 = Main.SC.nextLine();

		String sql = "SELECT * FROM MEMBER WHERE ID = ? AND PWD = ?";

		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, id1);
		pstmt.setString(2, pwd1);

		ResultSet rs = pstmt.executeQuery();

		if (rs.next()) {
			String inputId = rs.getString("ID");
			String inputPwd = rs.getString("PWD");
			String inputNick = rs.getString("NICK");

			MemberVo mvo = new MemberVo(inputId, inputPwd, inputNick);
			System.out.println(mvo.getId() + "님 환영합니다 !");
		} else {
			throw new RuntimeException("로그인 실패...");
		}

	}

	private Connection getCon() throws Exception {

		// JDBC 연동준비
		Class.forName("oracle.jdbc.driver.OracleDriver");

		// 데이터 베이스 연결 정보 준비
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String id = "C##KH";
		String pwd = "1234";

		// 데이터베이스 연결 == 커넥션 객체 얻기
		Connection conn = DriverManager.getConnection(url, id, pwd);

		return conn;
	}

}

