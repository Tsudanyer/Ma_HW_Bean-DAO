package exmodel.exmodelDAO;

import java.util.*;
import java.util.Date;
import java.sql.*;

import exmodel.ProductBean;

public class ProductDAOJdbc implements ProductDAO {

	private static final String url = "jdbc:sqlserver://localhost:1433;databaseName=jdbc";
	private static final String username = "sa";
	private static final String password = "passw0rd";

	public static void main(String[] args) {
		ProductDAO pdo = new ProductDAOJdbc();
		ProductBean bean = pdo.select(3);
		System.out.println("bean=" + bean);
		System.out.println();
		List<ProductBean> lean = pdo.select();
		System.out.println();
//		ProductBean iean = pdo.insert(11 , "Micro" , 20.0 , "2007-09-01" , 25);
//		ProductBean sean1 = pdo.select(11);
//		System.out.println("iean=" + sean1);
		System.out.println();
//		Date udate=ProductBean.convertDate("2008-09-05");
//		ProductBean uean = pdo.update("NIco",20.0,udate,50,11);
//		ProductBean sean2 = pdo.select(11);
//		System.out.println("uean=" + sean2);
		System.out.println();
//		boolean dean = pdo.delete(11);
//		System.out.println("dean="+dean);
	}

	private static final String select_by_id = "select * from product where id = ?";

	@Override
	public ProductBean select(int id) {
		ProductBean result = null;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet set = null;
		try {
			conn = DriverManager.getConnection(url, username, password);
			stmt = conn.prepareStatement(select_by_id);
			stmt.setInt(1, id);
			set = stmt.executeQuery();
			if (set.next()) {
				result = new ProductBean();
				result.setId(set.getInt("id"));
				result.setName(set.getString("name"));
				result.setPrice(set.getDouble("price"));
				result.setMake(set.getDate("make"));
				result.setExpire(set.getInt("expire"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (set != null) {
					set.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	private static final String SELECT_ALL = "select * from product";

	@Override
	public List<ProductBean> select() {
		List<ProductBean> result = null;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rset = null;
		try {
			conn = DriverManager.getConnection(url, username, password);
			stmt = conn.prepareStatement(SELECT_ALL);
			rset = stmt.executeQuery();

			while (rset.next()) {
				System.out.println("[" + rset.getInt("id") + " , "
						+ rset.getString("name") + " , "
						+ rset.getDouble("price") + " , "
						+ rset.getDate("make") + " , " + rset.getInt("expire")+"]");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rset != null) {
					rset.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	private static final String INSERT 
	= "insert into product (id, name, price, make, expire) values (?, ?, ?, ?, ?)";

	@Override
	public ProductBean insert(int id,String name,Double price,String make,int expire) {
		ProductBean result = null;
		try {
			Connection conn = DriverManager.getConnection(url, username,
					password);
			PreparedStatement stmt = conn.prepareStatement(INSERT);

			stmt.setInt(1,id);
			stmt.setString(2,name);
			stmt.setDouble(3,price);
			stmt.setDate(4,java.sql.Date.valueOf(make));
			stmt.setInt(5,expire);
			
			int set = stmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	private static final String UPDATE = "update product set name=?, price=?, make=?, expire=? where id=?";

	@Override
	public ProductBean update(String name, double price, Date make,
			int expire,int id) {
		ProductBean result=null;
		
		try{
			Connection conn=DriverManager.getConnection(url,username,password);
			PreparedStatement stmt=conn.prepareStatement(UPDATE);

			stmt.setString(1,name);
			stmt.setDouble(2,price);
			stmt.setDate(3,new java.sql.Date(make.getTime()));
			stmt.setInt(4,expire);
			stmt.setInt(5,id);
			
			int set = stmt.executeUpdate();
			
		}catch(SQLException e){
			e.printStackTrace();
		}
		
		return result;
	}

	private static final String DELETE = "delete from product where id = ?";

	@Override
	public boolean delete(int id) {
		boolean result=false;
		try{
			Connection conn =DriverManager.getConnection(url,username,password);
			PreparedStatement stmt=conn.prepareStatement(DELETE);
			
			stmt.setLong(1, id);
			int set=stmt.executeUpdate();
			result=true;
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}

}
