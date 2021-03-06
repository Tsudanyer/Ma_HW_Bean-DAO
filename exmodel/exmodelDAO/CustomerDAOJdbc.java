package exmodel.exmodelDAO;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import exmodel.CustomerBean;

public class CustomerDAOJdbc implements CustomerDAO {
	private static final String URL = "jdbc:sqlserver://localhost:1433;database=JDBC";
	private static final String USERNAME = "sa";
	private static final String PASSWORD = "passw0rd";

	public static void main(String[] args) {
		CustomerDAO dao = new CustomerDAOJdbc();
		CustomerBean bean = dao.select("Carol");
		System.out.println("bean="+bean);
	}
	
	private static final String UPDATE = "update customer set password=?, email=?, birth=? where custid=?";
	@Override
	public boolean update(byte[] password, String email, java.util.Date birth, String custid)  {
		boolean result = false;

		return result;
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
