package model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.CustomerBean;
import model.CustomerDAO;

public class CustomerDAOJdbc implements CustomerDAO {
	private static final String URL = "jdbc:sqlserver://localhost:1433;database=JDBCma";
	private static final String USERNAME = "sa";
	private static final String PASSWORD = "passw0rd";

	public static void main(String[] args) {
		CustomerDAO dao = new CustomerDAOJdbc();
		//test select
		CustomerBean bean = dao.select("Alex");
		System.out.println("bean="+bean);
		//test update
		dao.update(new byte[2], "test@lab.com", new java.sql.Date(System.currentTimeMillis()), "Alex");
	}
	
	private static final String UPDATE = "update customer set password=?, email=?, birth=? where custid=?";
	@Override
	public boolean update(byte[] password, String email, java.sql.Date birth, String custid)  {
		
		int count = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			pstmt = conn.prepareStatement(UPDATE);
			pstmt.setBytes(1, password);
			pstmt.setString(2, email);
			pstmt.setDate(3, birth);
			pstmt.setString(4, custid);
			count = pstmt.executeUpdate();
			System.out.println("update count = " + count);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(count > 0){
			return true;
		}else{
			return false;
		}
			
	}
	private static final String SELECT_BY_CUSTID =
			"select custid, password, email, birth from customer where custid=?";
	@Override
	public CustomerBean select(String custid) {
		CustomerBean result = null;

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rset = null;
		try {
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			stmt = conn.prepareStatement(SELECT_BY_CUSTID);
			stmt.setString(1, custid);
			rset = stmt.executeQuery();
			if(rset.next()) {
				result = new CustomerBean();
				
				result.setCustid(rset.getString("custid"));
				result.setPassword(rset.getBytes("password"));
				result.setEmail(rset.getString("email"));
				result.setBirth(rset.getDate("birth"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rset!=null) {
				try {
					rset.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(stmt!=null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(conn!=null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		return result;
	}
}
