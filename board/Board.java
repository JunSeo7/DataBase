package board;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import main.Main;
import util.JDBCTemplate;

public class Board {

	public void printMenu() throws Exception {

		System.out.println("1. 게시물 작성");
		System.out.println("2. 게시물 삭제 (자신이 작성한 게시물)");
		System.out.println("3. 게시물 제목 수정 (자신이 작성한 게시물)");
		System.out.println("4. 게시물 내용 수정 (자신이 작성한 게시물)");
		System.out.println("5. 게시물 목록 조회");
		System.out.println("6. 게시물 작성자명으로 조회");

		String num = Main.SC.nextLine();

		switch (num) {
		case "1":
			write();
			break;
		case "2":
			delete();
			break;
		case "3":
			editTitle();
			break;
		case "4":
			editContent();
			break;
		case "5":
			selectBoardList();
			break;
		case "6":
			selectByWriterName();
			break;
		}

	}

	private void write() throws Exception {

		isLogin();

		Connection conn = JDBCTemplate.getConn();

		String sql = "INSERT INTO BOARD(NO, TITLE, CONTENT, WRITER_NO) VALUES(SEQ_BOARD_NO.NEXTVAL , ? , ?, ?)";

		System.out.print("제목 : ");
		String title = Main.SC.nextLine();
		System.out.print("내용 : ");
		String content = Main.SC.nextLine();

		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, title);
		pstmt.setString(2, content);
		pstmt.setString(3, Main.loginMember.getNo());

		int result = pstmt.executeUpdate();

		if (result != 1) {
			System.out.println("게시글 작성 실패하셨습니다.");
			return;
		}
		System.out.println("게시글 작성 성공하셨습니다.");

	}

	private void delete() throws Exception {

		isLogin();

		Connection conn = JDBCTemplate.getConn();

		String sql = "UPDATE BOARD SET DEL_YN = 'Y' WHERE NO = ? AND WRITER_NO = ?";
		System.out.print("삭제할 게시물 번호");
		String no = Main.SC.nextLine();

		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, no);
		pstmt.setString(2, Main.loginMember.getNo());

		int result = pstmt.executeUpdate();

		if (result != 1) {
			System.out.println("자신의 게시물만 삭제할 수 있습니다.");
			return;
		}
		System.out.println("게시물이 삭제되었습니다.");

	}

	private void editTitle() {
		// TODO Auto-generated method stub

	}

	private void editContent() {
		// TODO Auto-generated method stub

	}

	private void selectBoardList() throws Exception {

		Connection conn = JDBCTemplate.getConn();

		String sql = "SELECT NO, TITLE, ENROLL_DATE FROM BOARD WHERE DEL_YN = 'N' ORDER BY NO";

		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();

		List<BoardVo> voList = new ArrayList<BoardVo>();
		BoardVo bVo = null;
		while (rs.next()) {
			String no = rs.getString("NO");
			String title = rs.getString("TITLE");
			String enrollDate = rs.getString("ENROLL_DATE");

			bVo = new BoardVo(no, title, null, enrollDate, null, null);
			voList.add(bVo);
		}

		System.out.print("번호");
		System.out.print(" | ");
		System.out.print("제목");
		System.out.print(" | ");
		System.out.print("작성일시");
		System.out.println();

		for (BoardVo vo : voList) {
			System.out.print(vo.getNo());
			System.out.print(" | ");
			System.out.print(vo.getTitle());
			System.out.print(" | ");
			System.out.print(vo.getEnrollDate());
			System.out.println();
		}

	}

	private void selectByWriterName() throws Exception {

	}

	private void isLogin() {
		if (Main.loginMember == null) {
			System.out.println("로그인 후 이용 가능합니다.");
			return;
		}
	}

}