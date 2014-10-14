package edu.sjsu.cmpe282.dao;

import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;

//import org.bson.BasicBSONObject;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;

//import edu.sjsu.cmpe282.dto.CartItem;
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
	
	private DBCollection mongoDbUsersCollection;

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
			MongoClient mongo = new MongoClient("localhost", 27017);
			DB db = mongo.getDB("cmpe282db");		 
			mongoDbUsersCollection = db.getCollection("users");
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
				 mongoDbUsersCollection.insert(document);
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
				connect.close();
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
				connect.close();
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
		DBCursor cursor = mongoDbUsersCollection.find(queryOptions);		
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
				
			mongoDbUsersCollection.update(queryOptions, new BasicDBObject("$set", new BasicDBObject("cart", cartlist)));
			//db.users.update({mailId:"sandeep@cat.com"}, {$set:{cart: [{productId:"3", quantity: 2}]}})
		}
		cursor.close();
		}
		catch (MongoException me) 
		{
			me.printStackTrace();
		}
	}
	
	public ArrayList<DBObject> displayItemsFromCart(String mailId){
		ArrayList<DBObject> cartlist = new ArrayList<DBObject>();
		//find user document
		final DBObject queryOptions = new BasicDBObject("mailId", mailId);
		try {
			DBCursor cursor = mongoDbUsersCollection.find(queryOptions);		
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
	
	public void removeItemFromCart(String mailId, String productId) {
		//find user document
		final DBObject queryOptions = new BasicDBObject("mailId", mailId);
		try {
		DBCursor cursor = mongoDbUsersCollection.find(queryOptions);		
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
		mongoDbUsersCollection.update(queryOptions, new BasicDBObject("$set", new BasicDBObject("cart", cartlist)));
		}
		cursor.close();
		}
		catch (MongoException me) 
		{
			me.printStackTrace();
		}
	}
	
}
