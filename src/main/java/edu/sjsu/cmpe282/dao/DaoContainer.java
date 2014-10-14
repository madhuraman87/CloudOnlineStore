package edu.sjsu.cmpe282.dao;

public class DaoContainer {
	public final static ProductDAO productDao = new ProductDAO();
	public final static UserDAO userDao = new UserDAO();
	//public final static OrderDAO orderDao = new OrderDAO();
	public final static CartDAO cartDao = new CartDAO();
}
