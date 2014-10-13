package edu.sjsu.cmpe282.api.resources;
import edu.sjsu.cmpe282.dao.ProductDAO;
import edu.sjsu.cmpe282.dto.Product;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/product")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductResources {
	
	private ProductDAO productdao = new ProductDAO();
	
	@POST
	@Path("/addproduct")
	public Response addProduct(Product product) throws ClassNotFoundException {
		
		return Response.status(201).entity(productdao.addProduct(product)).build();
	}
	
	@GET
	@Path("/listallproducts")
	@Produces(MediaType.APPLICATION_JSON)
	public Response listProducts() throws ClassNotFoundException {
		
		return Response.status(201).header("Access-Control-Allow-Origin", "*").entity(productdao.listAllProducts()).build();
	}
	
	@GET
	@Path("/listproducts")
	@Produces(MediaType.APPLICATION_JSON)
	public Response listCatalogProducts(@QueryParam("catalog") String catalog) throws ClassNotFoundException {
		
		return Response.status(201).entity(productdao.listByCatalog(catalog)).build();
	}

    @GET
    @Path("/productInfo/{productId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProductDetails(@QueryParam("catalog") String productId) {
        return Response.status(201).build();
    }
}
