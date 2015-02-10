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
	private static final String URL="jdbc:sqlserver://localhost:1433;database=JDBC2";
	private static final String USERNAME="sa";
	private static final String PASSWORD="passw0rd";
	
	private static final String SELECT_BY_ID = "select * from product where id = ?";
	@Override
	public ProductBean select(int id) {
		ProductBean pdb = null;
		try(Connection conn=DriverManager.getConnection(URL,USERNAME,PASSWORD);
				PreparedStatement pmst=conn.prepareStatement(SELECT_BY_ID);){
			pmst.setInt(1, id);
			ResultSet rs=pmst.executeQuery();
			if(rs.next()){
				pdb=new ProductBean();
				pdb.setId(rs.getInt("id"));
				pdb.setMake(rs.getDate("make"));
				pdb.setName(rs.getString("name"));
				pdb.setPrice(rs.getDouble("price"));
				pdb.setExpire(rs.getInt("expire"));
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return pdb;
	}

	private static final String SELECT_ALL = "select * from product";
	@Override
	public List<ProductBean> select() {
		List<ProductBean> listpb=new ArrayList<ProductBean>();
		ProductBean pdb = null;
		try(Connection conn=DriverManager.getConnection(URL, USERNAME, PASSWORD);
				PreparedStatement psmt=conn.prepareStatement(SELECT_ALL);
				ResultSet rs=psmt.executeQuery();){
			while(rs.next()){
				pdb=new ProductBean();
				pdb.setId(rs.getInt("id"));
				pdb.setMake(rs.getDate("make"));
				pdb.setName(rs.getString("name"));
				pdb.setPrice(rs.getDouble("price"));
				pdb.setExpire(rs.getInt("expire"));
				listpb.add(pdb);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		
		return listpb;
	}
	private static final String INSERT = 
			"insert into product (id, name, price, make, expire) values (?, ?, ?, ?, ?)";
	@Override
	public ProductBean insert(ProductBean bean) {
		ProductBean pb=null;
		try(Connection conn=DriverManager.getConnection(URL, USERNAME, PASSWORD);
				PreparedStatement psmt=conn.prepareStatement(INSERT);){
			psmt.setInt(1, bean.getId());
			psmt.setString(2, bean.getName());
			psmt.setDouble(3, bean.getPrice());
			psmt.setDate(4, new java.sql.Date(bean.getMake().getTime()));
			psmt.setInt(5, bean.getExpire());
			psmt.executeUpdate();
			pb=select(bean.getId());
		}catch(SQLException e){
			e.printStackTrace();
		}
		return pb;
	}
	private static final String UPDATE = 
			"update product set name=?, price=?, make=?, expire=? where id=?";
	@Override
	public ProductBean update(String name, double price, Date make, int expire,	int id) {
		ProductBean pdb=new ProductBean();
		try(Connection conn=DriverManager.getConnection(URL, USERNAME, PASSWORD);
				PreparedStatement pstmt =conn.prepareStatement(UPDATE);){
			pdb.setId(id);
			pdb.setMake(make);
			pdb.setName(name);
			pdb.setPrice(price);
			pdb.setExpire(expire);
			pstmt.setString(1, pdb.getName());
			pstmt.setDouble(2, pdb.getPrice());
			pstmt.setDate(3, new java.sql.Date(pdb.getMake().getTime()));
			pstmt.setInt(4, pdb.getExpire());
			pstmt.setInt(5, pdb.getId());
			pstmt.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
		}
		
		return pdb;
	}
	private static final String DELETE = "delete from product where id = ?";
	@Override
	public boolean delete(int id) {
		boolean b=false;
		try (Connection conn=DriverManager.getConnection(URL, USERNAME, PASSWORD);
			PreparedStatement pstmt=conn.prepareStatement(DELETE);){
			pstmt.setInt(1, id);
			if(pstmt.executeUpdate()==0){
				b=false;
			}else{
				b=true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return b;
	}

}
