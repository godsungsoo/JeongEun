package bank.model.controller;

import java.util.ArrayList;

import bank.exception.BankException;
import bank.model.service.BankService;
import bank.model.vo.Bank;
import bank.view.BankMenu;

public class BankController {
	private BankService bService;
	
	public BankController() {
		try {
			bService = new BankService();
		} catch (Exception e) {
			new BankMenu().printError(e.getMessage());
		}
	}

	public void insertDeposit(Bank bank) {
		try {
			if(bService.insertDeposit(bank) > 0)
				System.out.println("입금 성공!");
		} catch (Exception e) {
			new BankMenu().printError(e.getMessage());
		}
	}
	
	public void insertWithdraw(Bank bank) {
		try {
			if(bService.insertWithdraw(bank) > 0)
				System.out.println("출금 성공!");
		} catch (Exception e) {
			new BankMenu().printError(e.getMessage());
		}
	}
	
	public void bankNewInsert(Bank bank) {
		try {
			if(bService.bankNewInsert(bank) > 0)
				System.out.println("개설 성공!");
		} catch (Exception e) {
			new BankMenu().printError(e.getMessage());
		}
	}
	
	public void bankOldInsert(Bank bank) {
		try {
			if(bService.bankOldInsert(bank) > 0)
				System.out.println("개설 성공!");
		} catch (Exception e) {
			new BankMenu().printError(e.getMessage());
		}
	}
	
	public void selectAll() {
		BankMenu bm = new BankMenu();
		try {
			bm.printAll(bService.selectAll());
		} catch (Exception e) {
			bm.printError(e.getMessage());
		}
	}
	
	public void selectName(String userName) {
		BankMenu bm = new BankMenu();
		try {
			ArrayList<Bank> bankList = bService.selectName(userName);
			bm.printAll(bankList);
			
			if(bankList.size() > 0)
				System.out.println("조회 성공!");
		} catch (BankException e) {
			bm.printError(e.getMessage());
		}
	}
	
	public void selectAccount(String accountNo) {
		BankMenu bm = new BankMenu();
		try {
			ArrayList<Bank> bankList = bService.selectAccount(accountNo);
			bm.selectOne(bankList);
			
			if(bankList.size() > 0)
				System.out.println("조회 성공!");
		} catch (Exception e) {
			bm.printError(e.getMessage());
		}
	}
	
	public void updatePhone(Bank bank) {
		try {
			if(bService.updatePhone(bank) > 0)
				System.out.println("변경에 성공하였습니다!");
		} catch (Exception e) {
			new BankMenu().printError(e.getMessage());
		}
	}
	
	public void deleteAccount(Bank bank) {
		try {
			if(bService.deleteAccount(bank) > 0)
				System.out.println("삭제가 성공적으로 완료되었습니다!");
		} catch (Exception e) {
			new BankMenu().printError(e.getMessage());
		}
	}
	
	public void insertTransaction(String myAccNo, String theyAccNo, int transMoney) {
		Bank bank = new Bank();
		bank.setAccountNo(theyAccNo);
		//System.out.println(bank.getBalance());
		try {
			if(theyAccNo == bank.getAccountNo()) {
				bService.insertTransaction(myAccNo, theyAccNo, transMoney);
				System.out.println("이체 성공!");
			}
		} catch (Exception e) {
			new BankMenu().printError(e.getMessage());
		}
	}

}
