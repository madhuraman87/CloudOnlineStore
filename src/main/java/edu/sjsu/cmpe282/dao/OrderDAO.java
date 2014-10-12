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

import edu.sjsu.cmpe282.dto.Orders;

public class OrderDAO {

	private DBCollection table1;	
	
	// Constructor with MongoDB connection
	public OrderDAO() {
		try{
			/**** Connect to MongoDB ****/
			MongoClient mongo = new MongoClient("localhost", 27017);
		 
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

}
