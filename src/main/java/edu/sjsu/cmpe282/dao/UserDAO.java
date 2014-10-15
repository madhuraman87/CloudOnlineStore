package edu.sjsu.cmpe282.dao;

import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;

import edu.sjsu.cmpe282.dto.CartItemProductDetail;
import edu.sjsu.cmpe282.dto.Product;
import edu.sjsu.cmpe282.dto.User;

/**
 * @author madhuajeeth
 *
 */
public class UserDAO 
{
	private Connection connect;
	private Statement stmt;
	private ResultSet rs;
	
	private DBCollection usersCollection;

	// Constructor with JDBC connection
	public UserDAO() 
	{
		try
		{
			try
			{
				Class.forName("com.mysql.jdbc.Driver");
			}
			catch (ClassNotFoundException e)
			{
				System.out.println("Unable to find MySQL JDBC Driver");
				e.printStackTrace();
			}
		connect = DriverManager.getConnection("jdbc:mysql://rdsmysqlinstance.cucnzttohznf.us-west-1.rds.amazonaws.com:3306/cmpe282","rootuser","madhuajeeth");
		}
		catch (SQLException se)
		{
			se.printStackTrace();
		}
		
		try{			
			MongoClient mongo = new MongoClient("54.183.198.222",27017);
			DB db = mongo.getDB("cmpe282db");		 
			usersCollection = db.getCollection("users");
		}
		catch (UnknownHostException e) {
			e.printStackTrace();
	    } 
		catch (MongoException e) {
		e.printStackTrace();
	    }
	}
	
	public boolean addUser(User user) 
	{
		try
		{
			stmt = connect.createStatement();
			String lastname = user.getLastName();
			if(lastname.equals("admin"))
			{
				 final String accountType = "admin";
				 user.setaccType(accountType);
				 String query = "INSERT INTO `cmpe282`.`users` (`acctype`,`firstname`, `lastname`, `email`, `passwd`) VALUES ('" + accountType + "', '" + user.getFirstName() + "', '" + user.getLastName() + "', '" + user.getMailId() + "', '" + user.getPasswd() + "');";
				 stmt.executeUpdate(query);
			}else
			{
				 final String accountType = "user";
				 user.setaccType(accountType);
				 String query = "INSERT INTO `cmpe282`.`users` (`acctype`,`firstname`, `lastname`, `email`, `passwd`) VALUES ('" + accountType + "', '" + user.getFirstName() + "', '" + user.getLastName() + "', '" + user.getMailId() + "', '" + user.getPasswd() + "');";
				 stmt.executeUpdate(query);
				 BasicDBObject document = new BasicDBObject();
					document.put("mailId", user.getMailId());
					document.put("cart", new ArrayList());
					document.put("orderHistory", new ArrayList());
				 usersCollection.insert(document);
			}
		}	
		catch (MongoException me) 
		{
			me.printStackTrace();
		}
		catch (SQLException se)
		{
			se.printStackTrace();
		}
		finally{
			try {
				stmt.close();				
			} 
			catch (SQLException se) {
				se.printStackTrace();
			}
		}
		
		return true;
	}
	
	public boolean checkUser(User user)
	{
		String origPasswd = null;
		try{
			stmt = connect.createStatement();
			String query = "Select passwd from cmpe282.users where email = '"+ user.getMailId() +"';";
			rs = stmt.executeQuery(query);
			rs.next();
			origPasswd = rs.getString("passwd");
		}
		catch (SQLException se){
			se.printStackTrace();			
		}
		finally{
			
			try {
				stmt.close();				
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
		return user.getPasswd().equals(origPasswd);
	}
	
	public void addItemToCart(String mailId, String productId, int quantity) {				
		//find user document
		final DBObject queryOptions = new BasicDBObject("mailId", mailId);
		try {
		DBCursor cursor = usersCollection.find(queryOptions);		
		//get carts attribute
		if(cursor.hasNext()) {
			BasicDBObject userDocument = (BasicDBObject) cursor.next();
			ArrayList<DBObject> cartlist = (ArrayList<DBObject>) userDocument.get("cart");
			boolean productFound = false;
			for(DBObject cartItem : cartlist) {
				if (cartItem.get("productId").equals(productId)) {
				productFound = true;
				Integer temp_quantity = (Integer) cartItem.get("quantity");
				int newquantity = temp_quantity + quantity;
				cartItem.put("quantity", newquantity);
				break;
				}				
			}
			if(!productFound) {
				DBObject newCartItem = new BasicDBObject();
				newCartItem.put("productId", productId);
			    newCartItem.put("quantity", quantity);
			    cartlist.add(newCartItem);			    
			}
				
			usersCollection.update(queryOptions, new BasicDBObject("$set", new BasicDBObject("cart", cartlist)));
			//db.users.update({mailId:"sandeep@cat.com"}, {$set:{cart: [{productId:"3", quantity: 2}]}})
		}
		cursor.close();
		}
		catch (MongoException me) 
		{
			me.printStackTrace();
		}
	}
	
	public ArrayList<DBObject> getCartItemList(String mailId) {
		ArrayList<DBObject> cartlist = new ArrayList<DBObject>();
		//find user document
		final DBObject queryOptions = new BasicDBObject("mailId", mailId);
		try {
			DBCursor cursor = usersCollection.find(queryOptions);		
			//get carts attribute
			if(cursor.hasNext()) {
				BasicDBObject userDocument = (BasicDBObject) cursor.next();
				cartlist = (ArrayList<DBObject>) userDocument.get("cart");	
			}
		cursor.close();
		}	
		catch (MongoException me) 
		{
			me.printStackTrace();
		}
		
		return cartlist;
	}
	
	public ArrayList<ArrayList<DBObject>> getOrderHistoryList(String mailId) {
		ArrayList<ArrayList<DBObject>> orderlist = new ArrayList<ArrayList<DBObject>>();
		//find user document
		final DBObject queryOptions = new BasicDBObject("mailId", mailId);
		try {
			DBCursor cursor = usersCollection.find(queryOptions);		
			//get carts attribute
			if(cursor.hasNext()) {
				BasicDBObject userDocument = (BasicDBObject) cursor.next();
				orderlist = (ArrayList<ArrayList<DBObject>>) userDocument.get("orderHistory");	
			}
		cursor.close();
		}	
		catch (MongoException me) 
		{
			me.printStackTrace();
		}
		
		return orderlist;
	}
	
	public void removeItemFromCart(String mailId, String productId) {
		//find user document
		final DBObject queryOptions = new BasicDBObject("mailId", mailId);
		try {
		DBCursor cursor = usersCollection.find(queryOptions);		
		//get carts attribute
		if(cursor.hasNext()) {
			BasicDBObject userDocument = (BasicDBObject) cursor.next();
			ArrayList<DBObject> cartlist = (ArrayList<DBObject>) userDocument.get("cart");
			Iterator<DBObject> cartListIterator = cartlist.iterator();
			for(; cartListIterator.hasNext();) {
				DBObject cartItem = cartListIterator.next();
				if (cartItem.get("productId").equals(productId)) {
					cartListIterator.remove();
					break;
				}
			}
		usersCollection.update(queryOptions, new BasicDBObject("$set", new BasicDBObject("cart", cartlist)));
		}
		cursor.close();
		}
		catch (MongoException me) 
		{
			me.printStackTrace();
		}
	}
	
	public boolean orderIsValid(String userEmailId, String ccn) {
		return isCreditCardNumberValid(ccn);
	}
	
	private static boolean isCreditCardNumberValid(String ccn) {
		if (ccn.length() == 16){
			return true;
		}
		return false;	
	}
	
	public void placeOrder(final String userEmailId) {		
		final List<CartItemProductDetail> cipdList = DaoContainer.cartDao.getCartDetails(userEmailId);
		for(CartItemProductDetail cipd : cipdList ) {
			DaoContainer.productDao.reduceProductInventory(cipd.getProductId(), cipd.getQuantity());
		}
		saveOrderHistory(userEmailId);
		deleteCart(userEmailId);
	}
	
	private void saveOrderHistory(String userEmailId) {
		DBObject userDocument = getUserDocument(userEmailId);
		
		ArrayList<DBObject> cartList = (ArrayList<DBObject>) userDocument.get("cart");
		ArrayList<ArrayList<DBObject>> orderHistory = (ArrayList<ArrayList<DBObject>>) userDocument.get("orderHistory");
		orderHistory.add(cartList);
		
		final DBObject queryOptions = new BasicDBObject("mailId", userEmailId);
		final BasicDBObject updatedInfo = new BasicDBObject("$set", new BasicDBObject("orderHistory", orderHistory));
		usersCollection.update(queryOptions, updatedInfo);
	}

	private void deleteCart(String userEmailId) {
		final DBObject queryOptions = new BasicDBObject("mailId", userEmailId);		
		final BasicDBObject updatedInfo = new BasicDBObject("$set", new BasicDBObject("cart", new ArrayList<BasicDBObject>()));
		usersCollection.update(queryOptions, updatedInfo);
	}

	private DBObject getUserDocument(String userEmailId) {
		final DBObject queryOptions = new BasicDBObject("mailId", userEmailId);
		final DBCursor cursor = usersCollection.find(queryOptions);
		if(cursor.hasNext()) {
			return cursor.next();
		}
		return null;
	}
	
	public boolean isQuantityValid(final String userEmailId, final String productId, final int addQuantity) {
		final int cartQuantity = getProductQuantityFromUserCart(userEmailId, productId);
		final int inventory = DaoContainer.productDao.getProductDetailsByProductId(productId).getInventory();
		if(cartQuantity + addQuantity <= inventory)
			return true;
		return false;
	}

	private int getProductQuantityFromUserCart(String userEmailId, String productId) {
		List<CartItemProductDetail> cipdList = DaoContainer.cartDao.getCartDetails(userEmailId);
		for(CartItemProductDetail cipd : cipdList) {
			if(productId.equals(cipd.getProductId())) {
				return cipd.getQuantity();
			}
		}
		return 0;
	}
	
	public List<List<CartItemProductDetail>> getOrderDetails(String mailId) {
		List<List<CartItemProductDetail>> finalorderList = new ArrayList<List<CartItemProductDetail>>();
		ArrayList<ArrayList<DBObject>> orderHistoryList = DaoContainer.userDao.getOrderHistoryList(mailId);
		for(ArrayList<DBObject> orderItem : orderHistoryList) {
			List<CartItemProductDetail> orderList = new ArrayList<CartItemProductDetail>();
			for(DBObject item : orderItem){
				String productId = (String) item.get("productId");
				int quantity = (Integer) item.get("quantity");
				Product product = DaoContainer.productDao.getProductDetailsByProductId(productId);
				CartItemProductDetail oipd = new CartItemProductDetail(product, quantity);
				orderList.add(oipd);				
			}	
			finalorderList.add(orderList);
		}
		return finalorderList;
	}
	
}
