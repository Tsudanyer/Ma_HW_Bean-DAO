package model;

import java.text.ParseException;
import java.util.Iterator;
import java.util.List;

import model.dao.ProductDAOJdbc;

public class DAODemo {

	public static void main(String[] args) throws ParseException{
		ProductDAOJdbc pdj = new ProductDAOJdbc();
		
	//ProductDAOJdbc
		//test SELECT_BY_ID
        ProductBean pbean1 = pdj.select(1);
		System.out.println(pbean1);
		
		//test SELECT_ALL
		List<ProductBean> pbean2 = pdj.select();
		Iterator<ProductBean> iterator = pbean2.iterator();
		while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
		
		//test INSERT
//		ProductBean pbean3 = new ProductBean();
//		pbean3.setId(11);
//		pbean3.setName("Ice Cream");
//		pbean3.setPrice(30);
//		Date d = pbean3.convertDate("2009-12-25");
//		pbean3.setMake(d);
//		pbean3.setExpire(2);
//		
//		pdj.insert(pbean3);
//		
//		//test UPDATE
//		pbean1 = pdj.update("Coca Cola", 20, pbean1.convertDate("2009-12-24"), 365, 1);
//		System.out.println(pbean1);
		
		//test DELETE
//		boolean a = false;
//		a = pdj.delete(11);
		System.out.println("是否刪除成功："+ pdj.delete(11));
	}
}
