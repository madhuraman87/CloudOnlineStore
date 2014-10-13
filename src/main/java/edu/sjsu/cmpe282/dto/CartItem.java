package edu.sjsu.cmpe282.dto;

public class CartItem {
	
	private String prodId;
	private int quantity;
	
	public CartItem(String prodId, int quantity) {
		this.prodId = prodId;
		this.quantity = quantity;
	}
		
	public String getProdId() {
		return prodId;
	}
	public void setProdId(String prodId) {
		this.prodId = prodId;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}
