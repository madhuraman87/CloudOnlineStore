package edu.sjsu.cmpe282.api.resources;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
//import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import edu.sjsu.cmpe282.dao.DaoContainer;
import edu.sjsu.cmpe282.dao.OrderDAO;

@Path("/order")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderResources {

	private OrderDAO orderdao = DaoContainer.orderDao;
	
	@GET
	@Path("/orderhistory/{userId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getOrderById(@PathParam("userId") int userId) throws ClassNotFoundException {
		
		return Response.status(201).entity(orderdao.getOrderById(userId)).build();
	}

}
