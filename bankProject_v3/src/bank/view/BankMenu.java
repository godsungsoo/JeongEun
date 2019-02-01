package bank.view;

import java.util.ArrayList;
import java.util.Scanner;
import bank.model.controller.BankController;
import bank.model.vo.Bank;;

public class BankMenu {
	private Scanner sc = new Scanner(System.in);
	private BankController bController = new BankController();
		
	public BankMenu() {}	
	
	public void displayMenu() {
		do {
			System.out.println("-- 계좌 관리 프로그램 --");
			System.out.println("1. 관리자메뉴"	
							 + "\n2. 사용자메뉴"
							 + "\n3. 프로그램 종료");
			System.out.print("\n메뉴 선택 : ");
			switch(sc.nextInt()) {
			case 1 : adminMenu();	break;
			case 2 : userMenu();	break;
			case 3 : System.out.println("종료하시겠습니까? (y/n)");
					if(sc.next().toLowerCase().charAt(0) == 'y') 
						return;
					else
						break;
				default : System.out.println("잘못 입력하셨습니다.");
						  System.out.println("확인 후 다시 입력하세요.");
						  break;
			}			
		} while(true);
	}	
	
	public void adminMenu() {
		do {
			System.out.println("-- 관리자 메뉴 --");
			System.out.println("1. 신규 고객 통장개설"
							+ "\n2. 기존고객 통장개설"
							+ "\n3. 전체 고객 통장 조회"
							+ "\n4. 고객 이름으로 통장 조회"
							+ "\n5. 계좌번호로 거래내역 조회"
							+ "\n6. 고객 휴대폰 번호 변경"
							+ "\n7. 통장삭제"
							+ "\n8. 이전으로 돌아가기");
			System.out.print("\n메뉴 선택 : ");
			switch(sc.nextInt()) {
			case 1 : bController.bankNewInsert(bankNewInsert());	break;
			case 2 : bController.bankOldInsert(bankOldInsert());	break;
			case 3 : bController.selectAll();						break;
			case 4 : bController.selectName(inputUserName());		break;
			case 5 : bController.selectAccount(inputAccountNo());	break;
			case 6 : bController.updatePhone(inputPhone());			break;
			case 7 : bController.deleteAccount(deleteAccount());	break;
			case 8 : System.out.println("이전메뉴로 돌아갑니다."); 
					return;
				default : System.out.println("잘못 입력하셨습니다.");
						  System.out.println("확인 후 다시 입력해주세요.");
						  }
		} while(true);
	}
		
	public void userMenu() {
		do {
			System.out.println("-- 사용자메뉴 --");
			System.out.println("1. 입금"
							+ "\n2. 출금"
							+ "\n3. 계좌이체"
							+ "\n4. 계좌조회"
							+ "\n5. 이전으로 돌아가기");
			System.out.print("\n메뉴 선택 : ");
			switch(sc.nextInt()) {
			case 1 : bController.insertDeposit(insertDeposit());	break;
			case 2 : bController.insertWithdraw(insertWithdraw());  break;
			case 3 : insertTransaction(); break;
			case 4 : bController.selectAccount(inputAccountNo());	break;
			case 5 : System.out.println("이전메뉴로 돌아갑니다"); 
					return;
				default : System.out.println("잘못입력하셨습니다.");
						  System.out.println("확인 후 다시 시도해주세요.");
			}
		} while(true);
		
	}
		
	public Bank bankNewInsert() {
		Bank bank = new Bank();
		System.out.print("고객명 입력 :");
		bank.setUserName(sc.next());
		
		System.out.print("주민등록번호 입력(000000-0000000) : ");
		bank.setUserSsn(sc.next());
		System.out.print("휴대폰 번호 입력(-포함) : ");
		bank.setPhone(sc.next());
		System.out.print("첫 입금액 입력(최소 1000원 입금) : ");
		bank.setDeposit(sc.nextInt());
		
		return bank;
	}
	
	public Bank bankOldInsert() {
		Bank bank = new Bank();
		System.out.print("고객명 입력 :");
		bank.setUserName(sc.next());
		
		System.out.print("주민등록번호 입력(000000-0000000) : ");
		bank.setUserSsn(sc.next());
		
		System.out.print("입금액 입력(최소 1000원 입금) : ");
		bank.setDeposit(sc.nextInt());
		
		return bank;
	}
		
	public void printAll(ArrayList<Bank> bankList) {
		System.out.println("=== 조회된 목록 : " + bankList.size() + " ===");
		for(Bank bank : bankList) {
			System.out.println(bank);
		}
			
	}
		
	public String inputAccountNo() {
		System.out.print("계좌번호 입력[-포함] : ");
		return sc.next();
	}
		
	public String inputUserName() {
		System.out.println("고객이름 : ");
		return sc.next();
	}
		
	public void selectOne(ArrayList<Bank> bankList) {
		System.out.println("=== 조회 결과 ===");
		for(Bank bank : bankList) {
			String s =
					bank.getUsereNo() + ", " + bank.getAccountNo() + ", " + bank.getTransDate() + ", " + bank.getTypeNo() +
					", " + bank.getTransContent() + ", " + bank.getDeposit() + ", " + bank.getWithdraw() + ", " + bank.getBalance();
			System.out.println(s);
		}

	}
		
	public Bank insertDeposit() {
		Bank bank = new Bank();
		System.out.print("계좌번호 입력[-포함] : ");
		bank.setAccountNo(sc.next());
		System.out.print("입금액 : ");
		bank.setDeposit(sc.nextInt());
		
		return  bank;
	}
		
	public Bank insertWithdraw() {
		Bank bank = new Bank();
		System.out.print("계좌번호 입력[-포함] : ");
		bank.setAccountNo(sc.next());
		System.out.print("출금액 : ");
		bank.setWithdraw(sc.nextInt());
		
		return  bank;
	}
		
	public Bank deleteAccount() {
		Bank bank = new Bank();
		System.out.println("고객명 : ");
		bank.setUserName(sc.next());
		System.out.println("해당 고객의 주민등록번호[-포함] : ");
		bank.setUserSsn(sc.next());
		System.out.print("삭제할 계좌번호[-포함] : ");
		bank.setAccountNo(sc.next());
		
		return bank;
	}
	
	public Bank inputPhone() {
		Bank bank = new Bank();
		System.out.print("고객명 : ");
		bank.setUserName(sc.next());
		System.out.println("주민등록번호 입력[-포함]");
		bank.setUserSsn(sc.next());
		System.out.println("변경된 전화번호(숫자만입력) : ");
		bank.setPhone(sc.next());
		
		return bank;
	}
	
	public void printError(String message) {
		System.out.println("\n프로그램 오류 발생!");
		System.out.println("시스템 관리자에게 문의하세요");
		System.out.println("ERROR! [" + message + "]");	
	}
	
	public void insertTransaction() {
		System.out.print("본인 계좌 입력 : ");
		String myAccNo = sc.next();
		System.out.print("송금액 : ");
		int transMoney = sc.nextInt();
		System.out.print("송금 계좌 입력 : ");
		String theyAccNo = sc.next();
		bController.insertTransaction(myAccNo, theyAccNo, transMoney);
	}
	
	


}
