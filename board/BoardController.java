package board;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import main.Main;

public class BoardController {

	public void printMenu() throws Exception {
		System.out.println("----- BOARD -----");

		System.out.println("1. 게시글 작성");
		System.out.println("2. 게시글 수정 (제목)");
		System.out.println("3. 게시글 수정 (내용)");
		System.out.println("4. 게시글 삭제");
		System.out.println("5. 게시글 목록 조회");
		System.out.println("6. 게시글 상세 조회");
		System.out.println("7. 게시글 검색 (제목)");
		System.out.println("8. 게시글 검색 (내용)");
		System.out.print("번호를 입력하세요 : ");
		String input = Main.SC.nextLine();

		switch (input) {
		case "1":
			write();
			break;
		case "2":
			editTitle();
			break;
		case "3":
			editContent();
			break;
		case "4":
			delete();
			break;
		case "5":
			selectBoardOList();
			break;
		case "6":
			selectBoardOne();
			break;
		case "7":
			searchByTitle();
			break;
		case "8":
			searchByContent();
			break;
		default:
			System.out.println("잘못 입력하셨습니다.");
			return;
		}

	}

	// 게시글 작성
	private void write() throws Exception {

		// conn 준비
		Connection conn = getCon();

		// sql 준비
		String sql = "INSERT INTO BOARD(NO,TITLE,CONTENT) VALUES (SEQ_BOARD_NO.NEXTVAL,?,?)";

		System.out.print("제목 : ");
		String title = Main.SC.nextLine();
		System.out.print("내용 : ");
		String content = Main.SC.nextLine();

		// sql 실행
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, title);
		pstmt.setString(2, content);

		int result = pstmt.executeUpdate();

		// 결과 처리
		if (result != 1) {
			System.out.println("실패");
			return;
		}

		System.out.println("성공");

	}

	// 게시글 수정 (제목 변경 - 번호이용해서)
	private void editTitle() throws Exception {

		Connection conn = getCon();

		String sql = "UPDATE BOARD SET TITLE = ? WHERE NO = ?";

		System.out.print("번호 : ");
		String no = Main.SC.nextLine();
		System.out.print("제목 : ");
		String title = Main.SC.nextLine();

		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, title);
		pstmt.setString(2, no);

		int result = pstmt.executeUpdate();

		// 결과 처리
		if (result != 1) {
			System.out.println("실패");
			return;
		}

		System.out.println("성공");

	}

	// 게시글 수정 (내용 변경 - 번호이용해서)
	private void editContent() throws Exception {

		Connection conn = getCon();

		String sql = "UPDATE BOARD SET CONTENT = ? WHERE NO = ?";

		System.out.print("번호 : ");
		String no = Main.SC.nextLine();
		System.out.print("내용 : ");
		String content = Main.SC.nextLine();

		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, content);
		pstmt.setString(2, no);

		int result = pstmt.executeUpdate();

		// 결과 처리
		if (result != 1) {
			System.out.println("실패");
			return;
		}

		System.out.println("성공");

	}

	// 게시글 삭제
	private void delete() throws Exception {

		Connection conn = getCon();

		String sql = "DELETE BOARD WHERE NO = ?";

		System.out.print("삭제할 번호 입력 : ");
		String no = Main.SC.nextLine();

		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, no);

		int result = pstmt.executeUpdate();

		if (result != 1) {
			System.out.println("삭제 실패");
			return;
		}
		System.out.println("삭제 성공");

	}

	// 게시글 목록 조회 (최신순)
	private void selectBoardOList() throws Exception {

		Connection conn = getCon();

		String sql = "SELECT NO, TITLE, TO_CHAR(ENROLL_DATE, 'MM/DD HH:MI') 날짜 FROM BOARD ORDER BY NO DESC";

		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();

		BoardVo vo = null;

		ArrayList<BoardVo> voList = new ArrayList<BoardVo>();
		while (rs.next()) {
			String dbNo = rs.getString("NO");
			String dbTitle = rs.getString("TITLE");
			String dbEnrolldate = rs.getString("날짜");

			vo = new BoardVo(dbNo, dbTitle, null, dbEnrolldate);
			voList.add(vo);
		}

		System.out.print("번호");
		System.out.print(" | ");
		System.out.print("제목");
		System.out.print(" | ");
		System.out.print("작성일시");
		System.out.println();

		for (BoardVo s : voList) {
			System.out.print(s.getNo());
			System.out.print(" | ");
			System.out.print(s.getTitle());
			System.out.print(" | ");
			System.out.print(s.getEnrolldate());
			System.out.println();
		}
		if (vo == null) {
			System.out.println("목록 조회 실패");
			return;
		}

	}

	// 게시글 상세 조회 (번호 이용해서)
	private void selectBoardOne() throws Exception {

		Connection conn = getCon();

		System.out.print("상세 조회 번호 : ");
		String num = Main.SC.nextLine();

		String sql = "SELECT * FROM BOARD WHERE NO = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, num);

		ResultSet rs = pstmt.executeQuery();

		BoardVo vo = null;
		if (rs.next()) {
			String dbNo = rs.getString("NO");
			String dbTitle = rs.getString("TITLE");
			String dbContent = rs.getString("CONTENT");
			String dbEnrolldate = rs.getString("ENROLL_DATE");

			vo = new BoardVo(dbNo, dbTitle, dbContent, dbEnrolldate);
		}

		if (vo == null) {
			System.out.println("상세 조회 실패");
			return;
		}
		System.out.println("상세 조회 성공");
		System.out.println("-------------");
		System.out.println("번호 : " + vo.getNo());
		System.out.println("제목 : " + vo.getTitle());
		System.out.println("내용 : " + vo.getContent());
		System.out.println("날짜 : " + vo.getEnrolldate());
		System.out.println("-------------");
	}

	// 게시글 검색 (제목으로)
	private void searchByTitle() throws Exception {

		Connection conn = getCon();

		System.out.print("상세 조회 제목 : ");
		String title = Main.SC.nextLine();

		String sql = "SELECT * FROM BOARD WHERE TITLE LIKE '%' || ? || '%'";

		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, title);

		ResultSet rs = pstmt.executeQuery();

		BoardVo vo = null;
		ArrayList<BoardVo> voList = new ArrayList<BoardVo>();

		while (rs.next()) {
			String dbNo = rs.getString("NO");
			String dbTitle = rs.getString("TITLE");
			String dbEnrolldate = rs.getString("ENROLL_DATE");

			vo = new BoardVo(dbNo, dbTitle, null, dbEnrolldate);
			voList.add(vo);
		}

		System.out.print("번호");
		System.out.print(" | ");
		System.out.print("제목");
		System.out.print(" | ");
		System.out.print("작성일시");
		System.out.println();

		for (BoardVo s : voList) {
			System.out.print(s.getNo());
			System.out.print(" | ");
			System.out.print(s.getTitle());
			System.out.print(" | ");
			System.out.print(s.getEnrolldate());
			System.out.println();
		}
		if (vo == null) {
			System.out.println("목록 조회 실패");
			return;
		}
	}

	// 게시글 검색 (내용으로)
	private void searchByContent() throws Exception {

		Connection conn = getCon();

		System.out.print("상세 조회 내용 : ");
		String content = Main.SC.nextLine();

		String sql = "SELECT * FROM BOARD WHERE CONTENT LIKE '%' || ? || '%'";

		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, content);

		ResultSet rs = pstmt.executeQuery();

		BoardVo vo = null;
		ArrayList<BoardVo> voList = new ArrayList<BoardVo>();

		while (rs.next()) {
			String dbNo = rs.getString("NO");
			String dbTitle = rs.getString("TITLE");
			String dbContent = rs.getString("CONTENT");
			String dbEnrolldate = rs.getString("ENROLL_DATE");

			vo = new BoardVo(dbNo, dbTitle, dbContent, dbEnrolldate);
			voList.add(vo);
		}

		System.out.print("번호");
		System.out.print(" | ");
		System.out.print("제목");
		System.out.print(" | ");
		System.out.print("작성일시");
		System.out.println();

		for (BoardVo s : voList) {
			System.out.print(s.getNo());
			System.out.print(" | ");
			System.out.print(s.getTitle());
			System.out.print(" | ");
			System.out.print(s.getContent());
			System.out.print(" | ");
			System.out.print(s.getEnrolldate());
			System.out.println();
		}
		if (vo == null) {
			System.out.println("목록 조회 실패");
			return;
		}

	}

	private Connection getCon() throws Exception {
		String driver = "oracle.jdbc.driver.OracleDriver";
		Class.forName(driver);

		// 데이터 베이스 연결 정보 준비
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String id = "C##KH";
		String pwd = "1234";

		// 데이터베이스 연결 == 커넥션 객체 얻기
		Connection conn = DriverManager.getConnection(url, id, pwd);

		return conn;
	}

}
