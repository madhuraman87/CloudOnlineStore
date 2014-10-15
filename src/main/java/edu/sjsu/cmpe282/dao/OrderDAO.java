package edu.sjsu.cmpe282.dao;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;

import edu.sjsu.cmpe282.dto.*;

public class OrderDAO {

	private DBCollection table1;	
	
	// Constructor with MongoDB connection
	public OrderDAO() {
		try{
			/**** Connect to MongoDB ****/
			MongoClient mongo = new MongoClient("54.183.154.120",27017);
		 
			/**** Get database ****/
			DB db = mongo.getDB("cmpe282db");
		 
			table1 = db.getCollection("orders");
		}
		catch (UnknownHostException e) {
			e.printStackTrace();
	    } 
		catch (MongoException e) {
		e.printStackTrace();
	    }
	}
	
	public boolean creatOrder(Orders order)
	{
		try
		{
			BasicDBObject document = new BasicDBObject();
			document.put("productID", order.getProductID());
			document.put("custID", order.getCustID());
			document.put("noofitems", order.getNoOfItems());
			document.put("date", order.getPurchaseDate());
			document.put("status", order.getStatus());
			document.put("price",order.getPrice());
			document.put("orderID", order.getOrderID());
			table1.insert(document);			
		}
		catch (MongoException me) 
		{
			me.printStackTrace();
		}
		return true;
	}
	
	/**
	 * Deletes the entry containing the order id
	 * @param orderId
	 */
	public void removeFromCart(String orderID){
		
	}
	
	/**
	 * This method returns all the orders
	 * @return
	 */
	public List<Orders> listAllOrders(){
		
		List<Orders> order_list = new ArrayList<Orders>();
		try
		{
			DBCursor cursor = table1.find();
				
			while(cursor.hasNext()){
				BasicDBObject obj = (BasicDBObject)cursor.next();
				String orderID = obj.getString("orderID");
				String custID = obj.getString("custID");
				String productID = obj.getString("productID");
				int noOfItems = obj.getInt("noOfItems");
				int price = obj.getInt("price");
				String purchaseDate = obj.getString("purchaseDate");
				String status = obj.getString("status");
				Orders order = new Orders();
				order.setOrderID(orderID);
				order.setCustID(custID);
				order.setProductID(productID);
				order.setNoOfItems(noOfItems);
				order.setPrice(price);
				order.setPurchaseDate(purchaseDate);
				order.setStatus(status);
				order_list.add(order);
			}
			cursor.close();
		}
		catch (MongoException me) 
		{
			me.printStackTrace();
		}
		return order_list;
	}
	
	/**
	 * This method returns orders by customer id
	 * @return
	 * @param userId
	 */
	public List<Orders> getOrderById(int userId)
	{
		BasicDBObject query = new BasicDBObject();
		query.put("custID",userId);
		List<Orders> orders_list = new ArrayList<Orders>();
		try
		{
			DBCursor cursor = table1.find(query);
				
			while(cursor.hasNext()){
				BasicDBObject obj = (BasicDBObject)cursor.next();
				String orderID = obj.getString("orderID");
				String custID = obj.getString("custID");
				String productID = obj.getString("productID");
				int noOfItems = obj.getInt("noOfItems");
				int price = obj.getInt("price");
				String purchaseDate = obj.getString("purchaseDate");
				String status = obj.getString("status");
				Orders order = new Orders();
				order.setOrderID(orderID);
				order.setCustID(custID);
				order.setProductID(productID);
				order.setNoOfItems(noOfItems);
				order.setPrice(price);
				order.setPurchaseDate(purchaseDate);
				order.setStatus(status);
				orders_list.add(order);
			}
			cursor.close();
		}
		catch (MongoException me) 
		{
			me.printStackTrace();
		}
		return orders_list;
	}

}
