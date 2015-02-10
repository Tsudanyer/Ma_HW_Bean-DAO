package exmodel.exmodelDAO;

import java.util.List;

import exmodel.ProductBean;

public interface ProductDAO {
	public abstract ProductBean select(int id);
	public abstract List<ProductBean> select();
	public abstract ProductBean insert(int id,String name,Double price,String make,int expire);
	public abstract ProductBean update(String name, double price, java.util.Date make, int expire,int id);
	public abstract boolean delete(int id);
}