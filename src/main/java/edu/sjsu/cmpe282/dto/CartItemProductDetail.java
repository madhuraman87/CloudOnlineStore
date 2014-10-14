package edu.sjsu.cmpe282.dto;

public class CartItemProductDetail {
	
	private String productId;
	private String name;
	private String desc;
	private int price;	
	private String catalog;
	private int quantity;
	
	public CartItemProductDetail(Product product, int quantity) {
		this.productId = product.getProdId();
		this.name = product.getName();
		this.desc = product.getDesc();
		this.price = product.getPrice();
		this.catalog = product.getCatalog();
		this.quantity = quantity;
	}

	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
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

	public String getCatalog() {
		return catalog;
	}

	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}
}
