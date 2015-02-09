package model.dao;

import java.util.List;

import model.ProductBean;
import model.ProductDAO;

public class productDAODEMO {

	public static void main(String[] args) {
		ProductDAO dao=new ProductDAOJdbc();
		ProductBean pb;
		//select
		pb=dao.select(1);
		System.out.println("select: "+pb);

		//insert
		pb=new ProductBean();
		pb.setId(11);
		pb.setName("Alice");
		pb.setPrice(22.2);
		pb.setMake(new java.util.Date());
		pb.setExpire(500);
		ProductBean pb2=dao.insert(pb);
		System.out.println(pb2);
		
		//select
		pb=dao.select(11);
		System.out.println("select: "+pb);
		
		//update
		pb=dao.update("Monster", 11.1,new java.util.Date() , 100, 11);
		System.out.println(pb);
		
		//delect
		System.out.println("delect?"+dao.delete(11));
		
		//selectALL
		List<ProductBean> listpb=dao.select();
		for(int a=0;a<listpb.size();a++){
		System.out.println(listpb.get(a));
		}
		
		
	}

}
