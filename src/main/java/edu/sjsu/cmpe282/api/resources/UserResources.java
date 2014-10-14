package edu.sjsu.cmpe282.api.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import edu.sjsu.cmpe282.dao.CartDAO;
import edu.sjsu.cmpe282.dao.DaoContainer;
import edu.sjsu.cmpe282.dao.ProductDAO;
import edu.sjsu.cmpe282.dao.UserDAO;
import edu.sjsu.cmpe282.dto.User;

@Path("/users")
public class UserResources {

	private UserDAO userDao = DaoContainer.userDao;
	private CartDAO cartDao = DaoContainer.cartDao;
	
	@GET
	@Path("/{param}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMsg(@PathParam("param") String msg) {
 
		String output = "Jersey says : " + msg;
 
		return Response.status(200).entity(output).build();
	}
	
	@POST
	@Path("/signup")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response signUp(User user) throws ClassNotFoundException {
		
		System.out.print("user created: "+user.getFirstName());
		userDao.addUser(user);
		return Response.status(201).header("Access-Control-Allow-Origin" , "origin-list-or-null").header("Access-Control-Allow-Origin" , "*").entity(user).build();
	}

	@POST
	@Path("/signin")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response signIn(User user) throws ClassNotFoundException
	{
		System.out.println("User check");
		//userdao.checkUser(user);
		return Response.status(201).header("Access-Control-Allow-Origin" , "origin-list-or-null").header("Access-Control-Allow-Origin", "*").entity(userDao.checkUser(user)).build();
	}
	
	@POST
	@Path("/addToCart")
	@Produces(MediaType.APPLICATION_JSON)
	public Response addToCart(@QueryParam("mailId") String mailId, @QueryParam("productId") String productId, @QueryParam("quantity") int quantity) {
		final boolean validAddCommand = userDao.isQuantityValid(mailId, productId, quantity);
		if(validAddCommand) {
			userDao.addItemToCart(mailId, productId, quantity);
		}
		return Response.status(201).entity(validAddCommand).build();
	}
	
	@GET
	@Path("/displayCart")
	@Produces(MediaType.APPLICATION_JSON)
	public Response displayCart(@QueryParam("mailId") String mailId) {
		return Response.status(201).entity(cartDao.getCartDetails(mailId)).build();
	}
	
	@POST
	@Path("/removeFromCart")	
	public Response removeFromCart(@QueryParam("mailId") String mailId, @QueryParam("productId") String productId) {
		userDao.removeItemFromCart(mailId, productId);
		return Response.status(201).build();		
	}
	
	@POST
	@Path("/placeOrder")
	public Response placeOrder(@QueryParam("mailId") String mailId, @QueryParam("ccn") String ccn) {
		if(userDao.orderIsValid(mailId, ccn))
			userDao.placeOrder(mailId);
		return Response.status(201).build();		
	}
}
