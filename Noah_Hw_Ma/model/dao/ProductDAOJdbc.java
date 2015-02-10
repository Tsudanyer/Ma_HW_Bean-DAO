package model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.ProductBean;
import model.ProductDAO;

public class ProductDAOJdbc implements ProductDAO {
	private static final String URL = "jdbc:sqlserver://localhost:1433;database=JDBCma";
	private static final String USERNAME = "sa";
	private static final String PASSWORD = "passw0rd";
	
	private static final String SELECT_BY_ID = "select * from product where id = ?";
	@Override
	public ProductBean select(int id) {
		ProductBean result = null;

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rset = null;
		try {
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			stmt = conn.prepareStatement(SELECT_BY_ID);
			stmt.setInt(1, id);
			rset = stmt.executeQuery();
			
			if(rset.next()) {
				result = new ProductBean();
				
				result.setId(rset.getInt("id"));
				result.setName(rset.getString("name"));
				result.setPrice(rset.getDouble("price"));
				result.setMake(rset.getDate("make"));
				result.setExpire(rset.getInt("expire"));
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

	private static final String SELECT_ALL = "select * from product";
	@Override
	public List<ProductBean> select() {
		
		ProductBean result = null;
		List<ProductBean> listPB = new ArrayList<ProductBean>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rset = null;
		try {
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			stmt = conn.prepareStatement(SELECT_ALL);
			rset = stmt.executeQuery();
			
			while(rset.next()) {
				result = new ProductBean();
				result.setId(rset.getInt("id"));
				result.setName(rset.getString("name"));
				result.setPrice(rset.getDouble("price"));
				result.setMake(rset.getDate("make"));
				result.setExpire(rset.getInt("expire"));
				listPB.add(result);
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
		
		return listPB;
	}
	private static final String INSERT = 
			"insert into product (id, name, price, make, expire) values (?, ?, ?, ?, ?)";
	@Override
	public ProductBean insert(ProductBean bean) {
		
		ProductBean pb = bean;
		
		int count = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			pstmt = conn.prepareStatement(INSERT);
			
			pstmt.setInt(1, pb.getId());
			pstmt.setString(2, pb.getName());
			pstmt.setDouble(3, pb.getPrice());
			pstmt.setDate(4, new java.sql.Date(pb.getMake().getTime()));
			pstmt.setInt(5, pb.getExpire());
			
			count = pstmt.executeUpdate();
			
			System.out.println("insert count = " + count);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
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
			return pb;
		
	}
	private static final String UPDATE = 
			"update product set name=?, price=?, make=?, expire=? where id=?";
	@Override
	public ProductBean update(String name, double price, Date make, int expire,	int id) {
		ProductBean pb = new ProductBean();
		pb.setName(name);
		pb.setPrice(price);
		pb.setMake(make);
		pb.setExpire(expire);
		pb.setId(id);
		
		int count = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			pstmt = conn.prepareStatement(UPDATE);
			
			pstmt.setString(1, pb.getName());
			pstmt.setDouble(2, pb.getPrice());
			pstmt.setDate(3, new java.sql.Date(pb.getMake().getTime()));
			pstmt.setInt(4, pb.getExpire());
			pstmt.setInt(5, pb.getId());
			
			count = pstmt.executeUpdate();
			
			System.out.println("update count = " + count);
			
//			pb = new ProductBean();
//			pb.setId(id);
//			pb.setName(name);
//			pb.setPrice(price);
//			pb.setMake(make);
//			pb.setExpire(expire);

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
			return pb;
	}
	private static final String DELETE = "delete from product where id = ?";
	@Override
	public boolean delete(int id) {
		
		int count = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			pstmt = conn.prepareStatement(DELETE);
			pstmt.setInt(1, id);
			count = pstmt.executeUpdate();
			System.out.println("delete count = " + count);

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

}
