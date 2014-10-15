package edu.sjsu.cmpe282.dao;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;

import edu.sjsu.cmpe282.dto.Product;


public class ProductDAO {
	
	private DBCollection productCollection;
	
	
	// Constructor with MongoDB connection
	public ProductDAO() {
		try{
			/**** Connect to MongoDB ****/
			MongoClient mongo = new MongoClient("54.183.154.120",27017);
		 
			/**** Get database ****/
			DB db = mongo.getDB("cmpe282db");
		 
			productCollection = db.getCollection("products");
		}
		catch (UnknownHostException e) {
			e.printStackTrace();
	    } 
		catch (MongoException e) {
		e.printStackTrace();
	    }
	}
	
	public boolean addProduct(Product product)
	{
		try
		{
			BasicDBObject document = new BasicDBObject();
			document.put("prodID", product.getProdId());
			document.put("name", product.getName());
			document.put("desc", product.getDesc());
			document.put("price", product.getPrice());
			document.put("inventory", product.getInventory());
			document.put("Catalog",product.getCatalog());
			productCollection.insert(document);			
		}
		catch (MongoException me) 
		{
			me.printStackTrace();
		}
		return true;
	}
	
	public List<Product> listAllProducts()
	{
		List<Product> product_list = new ArrayList<Product>();
		try
		{
			DBCursor cursor = productCollection.find();
				
			while(cursor.hasNext()){
				BasicDBObject obj = (BasicDBObject)cursor.next();
				String prodId = obj.getString("prodID");
				String name = obj.getString("name");
				String desc = obj.getString("desc");
				int price = obj.getInt("price");
				int inventory = obj.getInt("inventory");
				String catalog = obj.getString("Catalog");
				Product product = new Product();
				product.setProdId(prodId);
				product.setName(name);
				product.setDesc(desc);
				product.setPrice(price);
				product.setInventory(inventory);
				product.setCatalog(catalog);
				product_list.add(product);
			}
			cursor.close();
		}
		catch (MongoException me) 
		{
			me.printStackTrace();
		}
		return product_list;
	}
	
	public List<Product> listByCatalog(String catalog)
	{
		BasicDBObject query = new BasicDBObject();
		query.put("Catalog",catalog);
		List<Product> product_list = new ArrayList<Product>();
		try
		{
			DBCursor cursor = productCollection.find(query);
				
			while(cursor.hasNext()){
				BasicDBObject obj = (BasicDBObject)cursor.next();
				String prodId = obj.getString("prodID");
				String name = obj.getString("name");
				String desc = obj.getString("desc");
				int price = obj.getInt("price");
				int inventory = obj.getInt("inventory");
				String product_catalog = obj.getString("Catalog");
				Product product = new Product();
				product.setProdId(prodId);
				product.setName(name);
				product.setDesc(desc);
				product.setPrice(price);
				product.setInventory(inventory);
				product.setCatalog(product_catalog);
				product_list.add(product);
			}
			cursor.close();
		}
		catch (MongoException me) 
		{
			me.printStackTrace();
		}
		return product_list;
	}

    public Product getProductDetailsByProductId(String productId) {
    	BasicDBObject productIdFilter = new BasicDBObject();
        productIdFilter.put("prodID", productId);
                
        Product product = null;
        try {
        	DBCursor cursor = productCollection.find(productIdFilter);
        	while(cursor.hasNext())
            {
                BasicDBObject obj = (BasicDBObject)cursor.next();
                String prodId = obj.getString("prodID");
                String name = obj.getString("name");
                String desc = obj.getString("desc");
                int price = obj.getInt("price");
                int inventory = obj.getInt("inventory");
                String catalog = obj.getString("Catalog");
                product = new Product();
                product.setProdId(prodId);
                product.setName(name);
                product.setDesc(desc);
                product.setPrice(price);
                product.setInventory(inventory);
                product.setCatalog(catalog);
            }
        	cursor.close();
        }
        catch(com.mongodb.MongoException me)
        {
            me.printStackTrace();
        }
        
        return product;
    }

	public void reduceProductInventory(String productId, int quantity) {
		BasicDBObject documentFindFilter = new BasicDBObject("prodID", productId);
		
		DBCursor cursor = productCollection.find(documentFindFilter);
		if(cursor.hasNext()) {
			DBObject productDocument = cursor.next();
			int inventory = (Integer) productDocument.get("inventory");
			inventory -= quantity;
			BasicDBObject updatedInfo = new BasicDBObject("$set", new BasicDBObject("inventory", inventory));
			productCollection.update(documentFindFilter, updatedInfo);
		}
	}
}
