package edu.sjsu.cmpe282.api.resources;

import javax.ws.rs.POST;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import edu.sjsu.cmpe282.dao.*;
import edu.sjsu.cmpe282.dto.*;

@Path("/users")
//@Produces(MediaType.APPLICATION_JSON)
//@Consumes(MediaType.APPLICATION_JSON)
public class UserResources {

	private UserDAO userdao = new UserDAO();
	
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
		userdao.addUser(user);
		return Response.status(201).header("Access-Control-Allow-Origin" , "origin-list-or-null").header("Access-Control-Allow-Origin" , "*").entity(user).build();
	}

	@POST
	@Path("/signin")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response signIn(User user) throws ClassNotFoundException
	{
		System.out.print("User check");
		//userdao.checkUser(user);
		return Response.status(201).header("Access-Control-Allow-Origin" , "origin-list-or-null").header("Access-Control-Allow-Origin", "*").entity(userdao.checkUser(user)).build();
		//return user;
	}

}
