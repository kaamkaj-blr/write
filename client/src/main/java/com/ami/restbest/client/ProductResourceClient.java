package com.ami.restbest.client;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ami.api.ProductApi;
import com.ami.model.Product;

/**
 * @author: Amit Khandelwal
 * Date: 3/7/17
 */

public class ProductResourceClient implements ProductApi {

	private static final Logger log = LoggerFactory.getLogger(ProductResourceClient.class);

	private final Client client;

	private final String url;

	public ProductResourceClient(String url) {
		this(url, null);
	}

	public ProductResourceClient(String url, Client client) {
		this.url = url;
		this.client = client == null ? ClientBuilder.newClient() : client;
	}

	@Override
	public Response create(Product product) {
		Response resp = client.target(url)
				.path("/products")
				.request()
				.accept(MediaType.APPLICATION_JSON)
				.post(Entity.json(product));
		return resp;
	}

	@Override
	public Response get(String productId) {
		return client.target(url)
				.path("/products/"+productId)
				.request()
				.accept(MediaType.APPLICATION_JSON)
				.get();
	}

	@Override
	public Response getAll() {
		return client.target(url)
				.path("/products")
				.request()
				.accept(MediaType.APPLICATION_JSON)
				.get();
	}

	@Override
	public Response update(Product product) {
		return null;
	}
}
