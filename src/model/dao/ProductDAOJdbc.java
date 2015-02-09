package model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.ProductBean;
import model.ProductDAO;

public class ProductDAOJdbc implements ProductDAO {
	private static final String SELECT_BY_ID = "select * from product where id = ?";

	private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=JDBC";
	private static final String USER_NAME = "sa";
	private static final String password = "passw0rd";

	public static void main(String[] args) {
		// select測試，搜尋條件 id = 1
		ProductDAOJdbc pbdj = new ProductDAOJdbc();
		ProductBean pb = pbdj.select(1);
		System.out.println(pb);
		// select all測試
		List<ProductBean> lpb = pbdj.select();
		for (ProductBean p : lpb) {
			System.out.println(p);
		}
		// insert 測試
//		 pb = new ProductBean();
//		 pb.setExpire(365);
//		 pb.setId(13);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		 try {
//		 pb.setMake(sdf.parse("2014-09-05"));
//		 pb.setName("Product1");
//		 pb.setPrice(3.14);
//		 pb = pbdj.insert(pb);
//		 } catch (ParseException e) {
//		 e.printStackTrace();
//		 }
//		 System.out.println(pb);
		
		// Update 測試
//		java.util.Date uDate = null;
//		try {
//			uDate = sdf.parse("2015-02-06");
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		pb = pbdj.update("Oranger Coke", 1, uDate, 1, 13);
//		System.out.println(pb);
		
		//Delete測試
		boolean deleteresult;
		deleteresult = pbdj.delete(12);
		if (deleteresult){
			System.out.println("刪除成功");
		}else{
			System.out.println("刪除失敗");
		}
	}

	@Override
	public ProductBean select(int id) {
		ProductBean pb = new ProductBean();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DriverManager.getConnection(URL, USER_NAME, password);
			pstmt = conn.prepareStatement(SELECT_BY_ID);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				pb.setId(rs.getInt("id"));
				pb.setName(rs.getString("name"));
				pb.setPrice(rs.getDouble("price"));
				java.sql.Date sDate = new java.sql.Date(rs.getDate("make")
						.getTime());
				pb.setMake(sDate);
				pb.setExpire(rs.getInt("expire"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
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

	private static final String SELECT_ALL = "select * from product";

	@Override
	public List<ProductBean> select() {
		ProductBean pb = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		List<ProductBean> lpb = null;
		ResultSet rs = null;
		try {
			conn = DriverManager.getConnection(URL, USER_NAME, password);
			pstmt = conn.prepareStatement(SELECT_ALL);
			rs = pstmt.executeQuery();
			lpb = new ArrayList<ProductBean>();
			while (rs.next()) {
				pb = new ProductBean();
				pb.setExpire(rs.getInt("expire"));
				pb.setId(rs.getInt("id"));
				pb.setMake(rs.getDate("make"));
				pb.setName(rs.getString("name"));
				pb.setPrice(rs.getDouble("price"));
				lpb.add(pb);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
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
		return lpb;
	}

	private static final String INSERT = "insert into product (id, name, price, make, expire) values (?, ?, ?, ?, ?)";

	@Override
	public ProductBean insert(ProductBean bean) {
		ProductBean pb = bean;
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DriverManager.getConnection(URL, USER_NAME, password);
			pstmt = conn.prepareStatement(INSERT);
			pstmt.setInt(1, pb.getId());
			pstmt.setString(2, pb.getName());
			pstmt.setDouble(3, pb.getPrice());
			java.sql.Date sDate = new java.sql.Date(pb.getMake().getTime());
			pstmt.setDate(4, sDate);
			pstmt.setInt(5, pb.getExpire());
			pstmt.executeUpdate();
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

	private static final String UPDATE = "update product set name=?, price=?, make=?, expire=? where id=?";

	@Override
	public ProductBean update(String name, double price, Date make, int expire,
			int id) {
		ProductBean pb = new ProductBean();
		pb.setName(name);
		pb.setPrice(price);
		pb.setMake(make);
		pb.setExpire(expire);
		pb.setId(id);
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DriverManager.getConnection(URL, USER_NAME, password);
			pstmt = conn.prepareStatement(UPDATE);
			pstmt.setString(1, pb.getName());
			pstmt.setDouble(2, pb.getPrice());
			java.sql.Date sDate = new java.sql.Date(pb.getMake().getTime());
			pstmt.setDate(3, sDate);
			pstmt.setInt(4, pb.getExpire());
			pstmt.setInt(5, pb.getId());
			pstmt.executeUpdate();
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
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DriverManager.getConnection(URL, USER_NAME, password);
			pstmt = conn.prepareStatement(DELETE);
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
			result = true;
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

		return result;
	}

}
