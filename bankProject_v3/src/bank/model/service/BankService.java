package bank.model.service;

import java.sql.Connection;
import java.util.ArrayList;

import bank.exception.BankException;
import bank.model.dao.BankDao;
import bank.model.vo.Bank;

import static common.BankTemp.*;

public class BankService {
	private BankDao bDao;
	
	public BankService() throws BankException{
		bDao = new BankDao();
	}

	public int insertDeposit(Bank bank) throws BankException{
		Connection conn = getConnection();
		int re = bDao.insertDeposit(conn, bank);
		if(re > 0)
			commit(conn);
		close(conn);
		
		return re;
	}
	
	public int insertWithdraw(Bank bank) throws BankException{
		Connection conn = getConnection();
		int re = bDao.insertWithdraw(conn, bank);
		if(re > 0)
			commit(conn);
		close(conn);
		
		return re;
	}
	
	public int updatePhone(Bank bank) throws BankException {
		Connection conn = getConnection();
		int re = bDao.updatePhone(conn, bank);
		if(re > 0)
			commit(conn);
		close(conn);
		
		return re;
	}
	
	public int bankNewInsert(Bank bank) throws BankException {
		Connection conn = getConnection();
		int re = bDao.bankNewInsert(conn, bank);
		if(re > 0)
			commit(conn);
		close(conn);
		
		return re;
	}
	
	public int bankOldInsert(Bank bank) throws BankException {
		Connection conn = getConnection();
		int re = bDao.bankOldInsert(conn, bank);
		if(re > 0)
			commit(conn);
		close(conn);
		
		return re;
	}
	
	public ArrayList<Bank> selectAll() throws BankException {
		Connection conn = getConnection();
		ArrayList<Bank> bankList = bDao.selectAll(conn);
		close(conn);
		
		return bankList;
	}
	
	public ArrayList<Bank> selectAccount(String accountNo) throws BankException {
		Connection conn = getConnection();
		ArrayList<Bank> bankList = bDao.selectAccount(conn, accountNo);
		close(conn);
		
		return bankList;
	}
	
	public ArrayList<Bank> selectName(String userName) throws BankException {
		Connection conn = getConnection();
		ArrayList<Bank> bankList = bDao.selectName(conn, userName);
		close(conn);
		
		return bankList;
	}
	
	public int deleteAccount(Bank bank) throws BankException{
		Connection conn = getConnection();
		int re = bDao.delectAccount(conn, bank);
		if(re > 0)
			commit(conn);
		close(conn);
		
		return re;
	}
	
	public void insertTransaction(String myAccNo, String theyAccNo, int transMoney) throws BankException {
		Connection conn = getConnection();
		Bank bank = new Bank();
		bank.setAccountNo(myAccNo);
		bank.setWithdraw(transMoney);
		bank.setTransContent(theyAccNo + "로 입금");
		int	re = bDao.insertWithdraw(conn, bank);
		
		Bank bb = new Bank();
		bb.setAccountNo(theyAccNo);
		bb.setDeposit(transMoney);
		bb.setTransContent(myAccNo + "송금");
		int	result = bDao.insertDeposit(conn, bb);
		
		if(re > 0 && result > 0)
			commit(conn);
		close(conn);
	}
}