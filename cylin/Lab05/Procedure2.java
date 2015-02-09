package com.cylin.Lab05;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Procedure2 {
	public static final String URL="jdbc:sqlserver://localhost:1433;database=SQL01";
	public static final String USERNAME="sa";
	public static final String PASSWORD="passw0rd";
	
	public static final String CALL="{call gen_seats(?,?,?)}";
	public boolean Call(String ptime,int movie,String roomid){
		boolean over=false;
		try(Connection conn=DriverManager.getConnection(URL, USERNAME, PASSWORD);
			CallableStatement cstmt=conn.prepareCall(CALL)){
			cstmt.setString(1, ptime);
			cstmt.setInt(2, movie);
			cstmt.setString(3, roomid);
			if(cstmt.execute()){
				conn.rollback();
			}else{
				over=true;
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return over;
	}
	
}
