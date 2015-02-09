package com.cylin.Lab02;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SQLLab02DEMO {

	public static void main(String[] args) {
		SQLLab02 l2=new SQLLab02();
		Roombean rb;
		SimpleDateFormat simple=new SimpleDateFormat();
		simple.applyPattern("yyyy-MM-dd HH:mm");
		Date date=null;
		try {
			date = simple.parse("2009-12-25 13:00");
			System.out.println(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		l2.insertplaylist(date, 1, "A�U");
		rb=l2.selectm_room("A�U");
		System.out.println(rb);
		
		for(int x=1;x<=rb.getSeat_row();x++){
			for(int y=1;y<=rb.getSeat_col();y++){
				String seat= String.format("%02d-%02d", x,y);
				boolean b=l2.insertSeat(date, '1', seat);
				System.out.println("seat:"+seat+" ,creat:"+b);
			}
		}
		
		
	}

}
