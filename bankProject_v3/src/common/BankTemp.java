package common;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

import bank.exception.BankException;

public class BankTemp {
	public static Connection getConnection() throws BankException{
		//Connection to DB
		Properties pr = new Properties();
		Connection conn = null;
		try {
			pr.load(new FileReader("properties/bankDriver.properties"));
			Class.forName(pr.getProperty("driver"));
			conn = DriverManager.getConnection
					(pr.getProperty("url"), pr.getProperty("user"), pr.getProperty("pin"));
			conn.setAutoCommit(false);
		} catch (Exception e) {
			throw new BankException(e.getMessage());
		}
		return conn;
	}
	
	public static void close(Connection conn) throws BankException{
		//close conn
		try {
			if(conn != null && !conn.isClosed())
				conn.close();
		} catch (Exception e) {
			throw new BankException(e.getMessage());
		}
	}
	
	public static void close(Statement statement) throws BankException{
		try {
			if(statement != null && !statement.isClosed())
				statement.isClosed();
		} catch (Exception e) {
			throw new BankException(e.getMessage());
		}
	}
	
	public static void close(ResultSet reSet) throws BankException{
		try {
			if(reSet != null && !reSet.isClosed())
				reSet.isClosed();
		} catch (Exception e) {
			throw new BankException(e.getMessage());
		}
	}
	
	public static void commit(Connection conn) throws BankException{
		try {
			if(conn != null && conn.isClosed())
				conn.commit();
		} catch (Exception e) {
			throw new BankException(e.getMessage());
		}
	}
	
	public static void rollback(Connection conn) throws BankException{
		try {
			if(conn != null && !conn.isClosed()) {
				conn.rollback();
			}
		} catch (Exception e) {
			throw new BankException(e.getMessage());
		}
	}

}
