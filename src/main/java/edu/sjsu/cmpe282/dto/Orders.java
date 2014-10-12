package edu.sjsu.cmpe282.dto;

public class Orders {
	
	private String custID;
	private String productID;
	private int noOfItems;
	private String purchaseDate;
	private String status;
	private int price;
	private String orderID;

	public Orders() {
		super();
	}
	
	public Orders(String custID,String productID,int noOfItems,String purchaseDate,String status,int price) {
		super();
		this.custID = custID;
		this.productID = productID;
		this.noOfItems = noOfItems;
		this.purchaseDate = purchaseDate;
		this.status = status;
		this.price = price;
	}

	public String getCustID() {
		return custID;
	}

	public void setCustID(String custID) {
		this.custID = custID;
	}

	public String getProductID() {
		return productID;
	}

	public void setProductID(String productID) {
		this.productID = productID;
	}

	public int getNoOfItems() {
		return noOfItems;
	}

	public void setNoOfItems(int noOfItems) {
		this.noOfItems = noOfItems;
	}

	public String getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(String purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getOrderID() {
		return orderID;
	}

	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}
	
}
