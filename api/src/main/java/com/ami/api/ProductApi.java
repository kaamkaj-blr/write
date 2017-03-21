package com.ami.api;

import javax.ws.rs.core.Response;

import com.ami.model.Product;

/**
 * @author: Amit Khandelwal
 * Date: 3/7/17
 */
public interface ProductApi {
	Response create(Product product);
	Response get(String productId);
	Response getAll();
	Response update(Product product);
}
