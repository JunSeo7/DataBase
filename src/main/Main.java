package main;

import java.util.Scanner;

import event.EventController;
import user.UserController;
import user.UserVo;

public class Main {

	public static final Scanner SC = new Scanner(System.in);
	public static UserVo loginUser = null; // 로그인한 유저정보를 담아둘 변수

	public static void main(String[] args) throws Exception {

		UserController uc = new UserController();
		EventController ec = new EventController();

		while (true) {
			System.out.println("--------------");
			System.out.println("0. 종료하기");
			System.out.println("1. USER");
			System.out.println("2. EVENT");
			System.out.print("번호 입력 : ");
			String num = Main.SC.nextLine();

			switch (num) {
			case "1":
				uc.printMenu();
				break;
			case "2":
				ec.printMenu();
				break;
			case "9":
				System.out.println("종료");
				return;
			default:
				System.out.println("잘못 된 입력입니다.");
			}
		}

	}// main

}// class