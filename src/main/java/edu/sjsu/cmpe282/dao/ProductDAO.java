package edu.sjsu.cmpe282.dao;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import edu.sjsu.cmpe282.dto.Product;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;


public class ProductDAO {
	
	private DBCollection table;
	
	
	// Constructor with MongoDB connection
	public ProductDAO() {
		try{
			/**** Connect to MongoDB ****/
			MongoClient mongo = new MongoClient("localhost", 27017);
		 
			/**** Get database ****/
			DB db = mongo.getDB("cmpe282db");
		 
			table = db.getCollection("products");
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
			table.insert(document);			
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
			DBCursor cursor = table.find();
				
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
			DBCursor cursor = table.find(query);
				
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

    public List<Product> getProductDetailsByProductId(String productId) {
    	BasicDBObject productIdFilter = new BasicDBObject();
        productIdFilter.put("prodID", productId);
        
        List<Product> product_list = new ArrayList<Product>();
        Product product = null;
        try {
        	DBCursor cursor = table.find(productIdFilter);
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
                product_list.add(product);
            }
        	cursor.close();
        }
        catch(com.mongodb.MongoException me)
        {
            me.printStackTrace();
        }
        
        return product_list;
    }
}
