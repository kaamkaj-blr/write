package com.ami.restbest.client;

import java.math.BigDecimal;

import javax.ws.rs.core.Response;

import org.junit.Assert;
import org.junit.Test;

import com.ami.model.Attr;
import com.ami.model.Product;

/**
 * @author: Amit Khandelwal
 * Date: 3/7/17
 */

public class ProductResourceClientTest {

	private String URL = "http://localhost:9090";

	@Test
	public void createProductTest(){
		ProductResourceClient productResourceClient = new ProductResourceClient(URL);
		Attr attr = new Attr("brand", "title", new BigDecimal("100.00"));
		Product product = new Product();
		product.setName("name");
		product.setId("1");
		product.setAttributes(attr);
		Response response = productResourceClient.create(product);
		System.out.println("response"+ response.getStatus());
		Assert.assertEquals(201,response.getStatus());
	}
}
