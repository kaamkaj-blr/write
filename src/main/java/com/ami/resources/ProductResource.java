package com.ami.resources;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ami.api.AppError;
import com.ami.api.BaseResponse;
import com.ami.api.Product;
import com.ami.exceptions.EntityNotPresentException;
import com.ami.services.ProductService;
import com.codahale.metrics.annotation.Timed;

/**
 * @author: Amit Khandelwal.
 * Date: 2/26/17
 */

@Path("/products")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductResource {
	private static final Logger log = LoggerFactory.getLogger(ProductResource.class);

	private final ProductService productService;

	@Inject
	public ProductResource(ProductService productService){
		this.productService = productService;
	}

	/**
	 * Add product.
	 * @param product product.
	 * @return newly created product.
	 */
	@POST
	@Timed
	public Response add(Product product) {
		Response response = null;
		BaseResponse addResponse = new BaseResponse();
		try {
			Product dbProduct = productService.add(product);
			addResponse.setOutput(dbProduct);
			response = Response.status(201).entity(addResponse).build();
		} catch (Exception e) {
			AppError error = new AppError(50002, e.getMessage());
			addResponse.setError(error);
			response = Response.status(500).entity(error).build();
		}
		return response;
	}

	@GET
	@Path("{id}")
	@Timed
	public Response get(@PathParam("id") String id) {
		Response response = null;
		BaseResponse productResponse = new BaseResponse();
		try {
			Product product = productService.get(id);
				productResponse.setOutput(product);
				response = response.status(200).entity(productResponse).build();
		} catch (EntityNotPresentException e) {
			AppError appError = new AppError();
			appError.setCode(50001);
			appError.setMessage(e.getMessage());
			productResponse.setError(appError);
			response = response.status(500).entity(productResponse).build();
		}

		return response;
	}

	@GET
	@Timed
	public Response get() {
		Response response = null;
		BaseResponse productResponse = new BaseResponse();
		try {
			List<Product> products = productService.get();
			productResponse.setOutput(products);
			response = response.status(200).entity(productResponse).build();
		} catch (Exception e) {
			AppError appError = new AppError();
			appError.setCode(50001);
			appError.setMessage(e.getMessage());
			productResponse.setError(appError);
			response = response.status(500).entity(productResponse).build();
		}

		return response;
	}

}
