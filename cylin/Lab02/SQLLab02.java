package com.cylin.Lab02;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;



public class SQLLab02 {
	private static final String URL = "jdbc:sqlserver://localhost:1433;database=SQL01";
	private static final String USERNAME="sa";
	private static final String PASSWORD="passw0rd";
	
	private static String INSERT="INSERT INTO playlist VALUES(?,?,?)";
	public boolean insertplaylist(java.util.Date ptime,int movie,String roomid){
		boolean c=false;
		try(Connection conn=DriverManager.getConnection(URL, USERNAME, PASSWORD);
				PreparedStatement pstmt=conn.prepareStatement(INSERT)){
			Timestamp date=new Timestamp(ptime.getTime());
			pstmt.setTimestamp(1, date);
			pstmt.setInt(2, movie);
			pstmt.setString(3, roomid);
			int check=pstmt.executeUpdate();
			if(check==0){
				c=false;
			}else{c=true;}		
		}catch(SQLException e){
			e.printStackTrace();
		}
		return c;
	}
	
	private static final String m_roomselect="SELECT * FROM m_room";
	public Roombean selectm_room(String roomid){
		Roombean rb=new Roombean();
		try(Connection conn=DriverManager.getConnection(URL, USERNAME, PASSWORD);
				PreparedStatement pstmt=conn.prepareStatement(m_roomselect);
				ResultSet rs=pstmt.executeQuery();){
			if(rs.next()){
				rb.setRoomid(rs.getString("roomid"));
				rb.setSeat_col(rs.getInt("seat_col"));
				rb.setSeat_row(rs.getInt("seat_row"));
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return rb;
	}
	private static final String SEATINSERT="INSERT INTO seats VALUES(?,?,?,0,null)";	
	public boolean insertSeat(java.util.Date ptime,int movie ,String seat_num ){
		boolean check=false;
		try(Connection conn=DriverManager.getConnection(URL, USERNAME, PASSWORD);
			PreparedStatement pstmt=conn.prepareStatement(SEATINSERT)){
			pstmt.setTimestamp(1, new Timestamp(ptime.getTime()));
			pstmt.setInt(2, movie);
			pstmt.setString(3, seat_num);
			if(pstmt.executeUpdate()==0){
				check=false;
			}else{check=true;}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return check;
	}
	
	public ArrayList<SelectBean> selectseats(){
		ArrayList<SelectBean> selectlist=new ArrayList();
		
		return selectlist;
	}
	
	
}
