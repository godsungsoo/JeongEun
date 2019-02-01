package bank.model.dao;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

import bank.exception.BankException;
import bank.model.vo.Bank;

import static common.BankTemp.*;

public class BankDao {
	private Properties pr = new Properties();
	private final static String bno = "02-";
	
	public BankDao() throws BankException{
		try {
			pr.load(new FileReader("properties/query.properties"));
		} catch (Exception e) {
			throw new BankException(e.getMessage());
		}
	}

	public int bankNewInsert(Connection conn, Bank bank) throws BankException {
		int re = 0;
		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(pr.getProperty("newinsert"));
			
			stat.setString(1, bno);
			stat.setString(2, bank.getUserName());
			stat.setString(3, bank.getUserSsn());
			stat.setString(4, bank.getPhone());
			stat.setString(5, bno);
			stat.setInt(6, 1);
			stat.setString(7, "통장개설");
			stat.setInt(8, bank.getDeposit());
			stat.setInt(9, bank.getDeposit());
			
			re = stat.executeUpdate();
			
			if(re <= 0) {
				rollback(conn);
				throw new BankException("새 고객 등록에 실패했습니다.");
			}
		} catch (Exception e) {
			rollback(conn);
			throw new BankException(e.getMessage());
		} finally {
			close(stat);
		}
		return re;
	}
	
	public int bankOldInsert(Connection conn, Bank bank) throws BankException {
		int re = 0;
		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(pr.getProperty("oldinsert"));
			
			stat.setString(1, bno);
			stat.setString(2, bank.getUserName());
			stat.setString(3, bank.getUserSsn());
			stat.setString(4, bno);
			stat.setInt(5, 1);
			stat.setString(6, "통장 개설");
			stat.setInt(7, bank.getDeposit());
			stat.setInt(8, bank.getDeposit());
			
			re = stat.executeUpdate();
			
			if(re <= 0) {
				rollback(conn);
				throw new BankException("고객 등록에 실패했습니다.");
			}
		} catch (Exception e) {
			rollback(conn);
			throw new BankException(e.getMessage());
		} finally {
			close(stat);
		}
		return re;
	}
	
	public ArrayList<Bank> selectAll(Connection conn) throws BankException{
		ArrayList<Bank> bankList = new ArrayList<>();
		Statement stat = null;
		ResultSet reSet = null;
		
		try {
			stat = conn.createStatement();
			reSet = stat.executeQuery(pr.getProperty("selectAll"));
			
			while(reSet.next()) {
				Bank bank = new Bank();
				bank.setUsereNo(reSet.getInt("user_no"));
				bank.setUserName(reSet.getString("user_name"));
				bank.setAccountNo(reSet.getString("account_no"));
				bank.setBalance(reSet.getInt("balance"));
				bank.setOpenDate(reSet.getDate("open_date"));
				bank.setTransDate(reSet.getDate("trans_date"));
				
				bankList.add(bank);
			}
			if(bankList.size() == 0)
				throw new BankException("조회된 목록이 없습니다.");
		} catch (Exception e) {
			throw new BankException(e.getMessage());
		} finally {
			close(reSet);
			close(stat);
		}
		return bankList;
	}
	
	public ArrayList<Bank> selectName(Connection conn, String userName) throws BankException {
		ArrayList<Bank> bankList = new ArrayList<>();
		PreparedStatement stat = null;
		ResultSet reSet = null;
		
		try {
			stat = conn.prepareStatement(pr.getProperty("selectname"));
			stat.setString(1, userName);
			reSet = stat.executeQuery();
			
			while(reSet.next()) {
				Bank bank = new Bank();
				
				bank.setUsereNo(reSet.getInt("user_no"));
				bank.setUserName(reSet.getString("user_name"));
				bank.setAccountNo(reSet.getString("account_no"));
				bank.setBalance(reSet.getInt("balance"));
				bank.setOpenDate(reSet.getDate("open_date"));
				bank.setTransDate(reSet.getDate("trans_date"));
				bank.setPhone(reSet.getString("phone"));
				
				bankList.add(bank);
			}
			if(bankList.size() == 0)
				throw new BankException(userName + " 이름의 고객 조회에 실패하였습니다.");
		} catch (Exception e) {
			throw new BankException(e.getMessage());
		} finally {
			close(reSet);
			close(stat);
		}
		return bankList;
	}
	
	public ArrayList<Bank> selectAccount(Connection conn, String accountNo) throws BankException {
		ArrayList<Bank> bankList = new ArrayList<>();
		PreparedStatement stat = null;
		ResultSet reSet = null;
		
		try {
			stat = conn.prepareStatement(pr.getProperty("selectmybank"));
			stat.setString(1, accountNo);
			reSet = stat.executeQuery();
			
			while(reSet.next()) {
				Bank bank = new Bank();
				
				bank.setUsereNo(reSet.getInt("user_no"));
				bank.setAccountNo(accountNo);
				bank.setTransDate(reSet.getDate("trans_date"));
				bank.setTypeNo(reSet.getInt("type_no"));
				bank.setTransContent(reSet.getString("trans_content"));
				bank.setDeposit(reSet.getInt("deposit"));
				bank.setWithdraw(reSet.getInt("withdraw"));
				bank.setBalance(reSet.getInt("balance"));
				
				bankList.add(bank);
			}
			if(bankList.size() == 0)
				throw new BankException(accountNo + "의 계좌번호 조회에 실패하였습니다.");
			
		} catch (Exception e) {
			throw new BankException(e.getMessage()); 
		} finally {
			close(reSet);
			close(stat);
		}
		return bankList;
	}
	
	public int updatePhone(Connection conn, Bank bank) throws BankException {
		int re = 0;
		PreparedStatement stat = null;
		
		try {
			stat = conn.prepareStatement(pr.getProperty("updatePhone"));
			stat.setString(1, bank.getPhone());
			stat.setString(2, bank.getUserName());
			stat.setString(3, bank.getUserSsn());
			
			re = stat.executeUpdate();
			
			if(re <= 0) {
				rollback(conn);
				throw new BankException(bank.getPhone() + "인 연락처가 존재하지 않습니다.");
			}
		} catch (Exception e) {
			rollback(conn);
			throw new BankException(e.getMessage());
		}
		return re;
	}
	
	public int delectAccount(Connection conn, Bank bank) throws BankException {
		int re = 0;
		PreparedStatement stat = null;
		
		try {
			stat = conn.prepareStatement(pr.getProperty("delete"));
			
			stat.setString(1, bank.getAccountNo());
			stat.setString(2, bank.getUserName());
			stat.setString(3, bank.getUserSsn());
			
			re = stat.executeUpdate();
			
			if(re <= 0) {
				rollback(conn);
				throw new BankException(bank.getAccountNo() + "인 계좌번호는 존재하지 않습니다.");
			}
		} catch (Exception e) {
			rollback(conn);
			throw new BankException(e.getMessage());
		}
		return re;
	}

	public int insertDeposit(Connection conn, Bank bank) throws BankException {
		int re = 0;
		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(pr.getProperty("insertDeposit"));
			
			stat.setString(1, bank.getAccountNo());
			stat.setString(2, bank.getAccountNo());
			stat.setString(3, bank.getTransContent());
			stat.setInt(4, bank.getDeposit());
			stat.setInt(5, bank.getDeposit());
			stat.setString(6, bank.getAccountNo());
			
			re = stat.executeUpdate();
			if(re <= 0) {
				rollback(conn);
				throw new BankException(bank.getAccountNo() + "에 입금실패");
			}
		} catch (Exception e) {
			rollback(conn);
			throw new BankException(e.getMessage());
		}
		return re;
	}
	
	public int insertWithdraw(Connection conn, Bank bank) throws BankException {
		int re = 0;
		PreparedStatement stat = null;
		
		try {
			stat = conn.prepareStatement(pr.getProperty("insertWithdraw"));
			
			stat.setString(1, bank.getAccountNo());
			stat.setString(2, bank.getAccountNo());
			stat.setString(3, bank.getTransContent());
			stat.setInt(4, bank.getWithdraw());
			stat.setString(5, bank.getAccountNo());
			stat.setInt(6, bank.getWithdraw());
			
			re = stat.executeUpdate();
			if(re <= 0) {
				rollback(conn);
				throw new BankException("");
			}
		} catch (Exception e) {
			rollback(conn);
			throw new BankException(e.getMessage());
		}
		return re;
	}
	
	
}
