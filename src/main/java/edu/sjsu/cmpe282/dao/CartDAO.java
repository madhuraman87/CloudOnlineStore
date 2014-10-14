package edu.sjsu.cmpe282.dao;

import java.util.ArrayList;
import java.util.List;

import com.mongodb.DBObject;

import edu.sjsu.cmpe282.dto.CartItemProductDetail;
import edu.sjsu.cmpe282.dto.Product;

public class CartDAO {

	private ProductDAO productDao = new ProductDAO();
	private UserDAO userDao = new UserDAO();
	
	public List<CartItemProductDetail> getCartDetails(String mailId) {
		List<CartItemProductDetail> cipdList = new ArrayList<CartItemProductDetail>();
		ArrayList<DBObject> cartItemList = userDao.getCartItemList(mailId);
		for(DBObject cartItem : cartItemList) {
			String productId = (String) cartItem.get("productId");
			int quantity = (Integer) cartItem.get("quantity");
			Product product = productDao.getProductDetailsByProductId(productId);
			CartItemProductDetail cipd = new CartItemProductDetail(product, quantity);
			cipdList.add(cipd);
		}
		return cipdList;
	}
}
