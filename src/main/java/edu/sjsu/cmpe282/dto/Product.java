package edu.sjsu.cmpe282.dto;

public class Product {
	
	private String prodId;
	private String name;
	private String desc;
	private int price;
	private int inventory;
	private String catalog;
	
	public Product() {
		super(); // TODO Auto-generated constructor stub
	}
	public String getProdId() {
		return prodId;
	}
	public void setProdId(String prodId) {
		this.prodId = prodId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getInventory() {
		return inventory;
	}
	public void setInventory(int inventory) {
		this.inventory = inventory;
	}	
	public String getCatalog() {
		return catalog;
	}
	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}
	public String toString(){
		 String prod = new StringBuffer("Product Id:").append(this.prodId).
				 append(" name:").append(this.name).append(" desc:").
				 append(this.desc).append(" Price:").append(this.price).append("  Inventory:").append(this.inventory).toString();
		 
		 return prod;
	}
}
