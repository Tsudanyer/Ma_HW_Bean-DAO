package model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.CustomerBean;
import model.CustomerDAO;

public class CustomerDAOJdbc implements CustomerDAO {
	private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=JDBC";
	private static final String USER_NAME = "sa";
	private static final String password = "passw0rd";

	// 查詢、新增、刪除、修改
	private static final String SELECT_BY_CUSTID = "select custid, password, email, birth from customer where custid = ?";
	private static final String INSERT_ROW = "insert into customer value (?, ?, ?, ?)";
	private static final String DELETE_ROW_BY_CUSTID = "delete from customer where custid = ?";
	private static final String Update_ROW_BY_CUSTID = "update customer set password=?, email=?, birth=? where custid = ?";

	// Tset 方法
	public static void main(String[] args) {
		//測試select
		CustomerDAO CDAOJ = new CustomerDAOJdbc();
		CustomerBean result = CDAOJ.select("Alex");
		System.out.println(result);
		//測試update更新，並在更新後用查詢查看結果
		byte[] b = {0x42};
		java.util.Date uDate = new java.util.Date();
		uDate = new java.util.Date(uDate.getTime());
		CustomerDAOJdbc cdj = new CustomerDAOJdbc();
		cdj.update(b, "alex@lab.com", uDate, "Alex");
		result = CDAOJ.select("Alex");
		System.out.println(result);
	}

	/* (non-Javadoc)
	 * @see model.dao.CustomerDAO#update(byte[], java.lang.String, java.util.Date, java.lang.String)
	 */
	@Override
	public boolean update(byte[] password, String email, java.util.Date birth,
			String custid) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		int update_number = 0;
		try {
			conn = DriverManager.getConnection(URL, USER_NAME,
					CustomerDAOJdbc.password);
			pstmt = conn.prepareStatement(Update_ROW_BY_CUSTID);
			pstmt.setBytes(1, password);
			pstmt.setString(2, email);
			java.sql.Date sDate = new java.sql.Date(birth.getTime());
			pstmt.setDate(3, sDate);
			pstmt.setString(4, custid);
			pstmt.executeUpdate();
			result = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		System.out.println("成功更新資料庫 " + update_number + " 筆");
		return result;
	}

	/* (non-Javadoc)
	 * @see model.dao.CustomerDAO#select(java.lang.String)
	 */
	@Override
	public CustomerBean select(String custid) {
		CustomerBean result = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = DriverManager.getConnection(URL, USER_NAME, password);
			pstmt = conn.prepareStatement(SELECT_BY_CUSTID);
			pstmt.setString(1, custid);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				result = new CustomerBean();
				result.setCustid(rs.getString("custid"));
				result.setPassword(rs.getBytes("password"));
				result.setEmail(rs.getString("email"));
				result.setBirth(rs.getDate("birth"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				if (pstmt != null) {
					try {
						pstmt.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				if (conn != null) {
					try {
						conn.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return result;
	}
}
