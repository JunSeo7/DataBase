package event;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import main.Main;
import util.JDBCTemplate;

public class EventController {

	public void printMenu() throws Exception {
		System.out.println("-----BOARD-----");
		System.out.println("1. 일정 작성");
		System.out.println("2. 일정 삭제");
		System.out.println("3. 일정 목록 조회");
		System.out.println("4. 일정 상세 조회");
		System.out.println("5. 일정 검색 (년)");
		System.out.println("6. 일정 검색 (월)");

		System.out.print("메뉴 번호 입력 : ");
		String num = Main.SC.nextLine();

		switch (num) {
		case "1":
			write();
			break;
		case "2":
			deleteEvent();
			break;
		case "3":
			selectEventList();
			break;
		case "4":
			selectEventOne();
			break;
		case "5":
			searchByYear();
			break;
		case "6":
			searchByMonth();
			break;
		case "7":
			searchByUserId();
			break;
		default:
			System.out.println("잘못 입력하셨습니다.");
		}

	}

	private void write() throws Exception {
		if (Main.loginUser == null) {
			System.out.println("로그인 후 이용 가능합니다.");
			return;
		}

		Connection conn = JDBCTemplate.getConn();

		System.out.print("이름 : ");
		String eventName = Main.SC.nextLine();
		System.out.print("내용 : ");
		String content = Main.SC.nextLine();
		System.out.print("날짜 : ");
		String eventDate = Main.SC.nextLine();

		String sql = "INSERT INTO EVENTS (EVENTNO, EVENTNAME, CONTENT, EVENTDATE, USERID) VALUES (SEQ_EVENT_ID.NEXTVAL, ?, ?, ?, ?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, eventName);
		pstmt.setString(2, content);
		pstmt.setString(3, eventDate);
		pstmt.setString(4, Main.loginUser.getNo());
		int result = pstmt.executeUpdate();

		if (result != 1) {
			System.out.println("일정 작성 실패");
			return;
		}
		System.out.println("일정 작성 성공");

	}

	private void deleteEvent() throws Exception {

		Connection conn = JDBCTemplate.getConn();

		System.out.print("삭제할 일정 번호 :");
		String no = Main.SC.nextLine();

		String sql = "DELETE EVENTS WHERE EVENTNO = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, no);
		int result = pstmt.executeUpdate();

		if (result != 1) {
			System.out.println("일정 삭제 실패");
			return;
		}
		System.out.println("일정 삭제");

	}

	private void selectEventList() throws Exception {

		Connection conn = JDBCTemplate.getConn();

		String sql = "SELECT EVENTNO, EVENTNAME,CONTENT,EVENTDATE FROM EVENTS ORDER BY EVENTNO";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();

		List<EventVo> voList = new ArrayList<EventVo>();
		while (rs.next()) {
			String no = rs.getString("EVENTNO");
			String eventName = rs.getString("EVENTNAME");
			String eventDate = rs.getString("EVENTDATE");

			EventVo vo = new EventVo(no, eventName, null, eventDate, null);
			voList.add(vo);
		}

		System.out.print("번호");
		System.out.print(" | ");
		System.out.print("이름");
		System.out.print(" | ");
		System.out.print("날짜");
		System.out.println();

		for (EventVo vo : voList) {
			System.out.print(vo.getEventno());
			System.out.print(" | ");
			System.out.print(vo.getEventname());
			System.out.print(" | ");
			System.out.print(vo.getEventdate());
			System.out.println();
		}

	}

	private void selectEventOne() throws Exception {

		Connection conn = JDBCTemplate.getConn();

		System.out.print("상세조회할 게시글 번호 : ");
		String num = Main.SC.nextLine();

		String sql = "SELECT * FROM EVENTS WHERE EVENTNO = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, num);
		ResultSet rs = pstmt.executeQuery();

		EventVo vo = null;
		if (rs.next()) {
			String no = rs.getString("EVENTNO");
			String eventName = rs.getString("EVENTNAME");
			String content = rs.getString("CONTENT");
			String eventDate = rs.getString("EVENTDATE");
			String id = rs.getString("USERID");

			vo = new EventVo(no, eventName, content, eventDate, id);
		}

		if (vo == null) {
			System.out.println("일정 상세조회 실패");
			return;
		}

		System.out.println("일정 상세 조회 성공");
		System.out.println("-------------");
		System.out.println("번호 : " + vo.getEventno());
		System.out.println("이름 : " + vo.getEventname());
		System.out.println("날짜 : " + vo.getEventdate());
		System.out.println("내용 : " + vo.getContent());
		System.out.println("-------------");

	}

	private void searchByYear() throws Exception {

		Connection conn = JDBCTemplate.getConn();

		System.out.print("검색할 년도를 입력하세요 (YYYY): ");
		String year = Main.SC.nextLine();

		String sql = "SELECT * FROM EVENTS WHERE TO_CHAR(EVENTDATE, 'YYYY') = ? ORDER BY EVENTNO";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, year);
		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {
			String no = rs.getString("EVENTNO");
			String eventName = rs.getString("EVENTNAME");
			String content = rs.getString("CONTENT");
			String eventDate = rs.getString("EVENTDATE");
			String userId = rs.getString("USERID");

			System.out.println("사용자 ID	: " + userId);
			System.out.println("일정 번호	: " + no);
			System.out.println("일정 이름	: " + eventName);
			System.out.println("내용	: " + content);
			System.out.println("일정 날짜	: " + eventDate);
			System.out.println("---------------------------");
		}

	}

	private void searchByMonth() throws Exception {

		Connection conn = JDBCTemplate.getConn();

		System.out.print("검색할 월을 입력하세요 (MM): ");
		String month = Main.SC.nextLine();

		String sql = "SELECT * FROM EVENTS WHERE TO_CHAR(EVENTDATE, 'MM') = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, month);
		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {
			String no = rs.getString("EVENTNO");
			String eventName = rs.getString("EVENTNAME");
			String content = rs.getString("CONTENT");
			String eventDate = rs.getString("EVENTDATE");
			String userId = rs.getString("USERID");

			System.out.println("사용자 ID	: " + userId);
			System.out.println("일정 번호	: " + no);
			System.out.println("일정 이름	: " + eventName);
			System.out.println("내용	: " + content);
			System.out.println("일정 날짜	: " + eventDate);
			System.out.println("---------------------------");
		}

	}

	private void searchByUserId() throws Exception {
		Connection conn = JDBCTemplate.getConn();

		System.out.print("검색할 사용자 ID를 입력하세요: ");
		String userId = Main.SC.nextLine();

		String sql = "SELECT * FROM EVENTS WHERE USERID = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, userId);
		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {
			String no = rs.getString("EVENTNO");
			String eventName = rs.getString("EVENTNAME");
			String content = rs.getString("CONTENT");
			String eventDate = rs.getString("EVENTDATE");

			System.out.println("일정 번호	: " + no);
			System.out.println("일정 이름	: " + eventName);
			System.out.println("내용	: " + content);
			System.out.println("일정 날짜	: " + eventDate);
			System.out.println("---------------------------");
		}
	}

}
