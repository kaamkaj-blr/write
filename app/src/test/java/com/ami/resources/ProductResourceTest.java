package com.ami.resources;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.ami.model.Attr;
import com.ami.model.Product;
import com.ami.exceptions.EntityNotPresentException;
import com.ami.services.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.dropwizard.jackson.Jackson;
import io.dropwizard.testing.junit.ResourceTestRule;

/** Ref :- http://stackoverflow.com/questions/31512245/calling-mockito-when-multiple-times-on-same-object
 *  need to use this.
 * @author: Amit Khandelwal
 * Date: 3/4/17
 */
public class ProductResourceTest {
	private static ProductService productService = mock(ProductService.class);
	private Product product;
	private Product product2;
	private Attr attr;
	private List<Product> products;
	private static final ObjectMapper obj = Jackson.newObjectMapper();

	// instead of class leval, making it test leval.
	@Rule
	public ResourceTestRule resources = ResourceTestRule.builder()
			.addResource(new ProductResource(productService))
			.build();

	@Before
	public void setup() {
		attr = new Attr("brand", "title", new BigDecimal("100.00"));
		product = new Product();
		product.setName("name");
		product.setId("1");
		product.setAttributes(attr);
		product2 = new Product();
		product2.setName("name2");
		product2.setId("2");
		product2.setAttributes(attr);
		products = new ArrayList<>();

		products.add(product);
		products.add(product2);
	}

	@Test
	public void testAddProduct() throws JsonProcessingException {
		// when
		when(productService.add(product)).thenReturn(product);

		// then
		Response response =resources.client().target("/products").request().post(Entity.entity(product, MediaType
				.APPLICATION_JSON_TYPE));

		// verify
		Assert.assertEquals(201,response.getStatus());
		// Below line will give the error as passed product will be the different de-serialized object
		// created by Jackson. so no need to verify it.
		//verify(productService).add(product);
		//	Product myResponse = response.getEntity(Product.class);
		//	Assert.assertEquals("created product doesn't match with requested product",response.getEntity
		//(Product.class).getClass());
	}

	@Test
	public void testGetProduct() {
		// when
		when(productService.get("1")).thenReturn(product);
		// then
		Response response = resources.client().target("/products/1").request().get();
		// hate to do this , i wanted to check product fields values but couldn't find easy way to get them
		// from the response object.
		Assert.assertEquals(200,response.getStatus());
		// verify
		verify(productService).get("1");
	}

	@Test
	public void testGetProducts() {
		doReturn(products).when(productService).get();
		when(productService.get()).thenReturn(products);

		Response response = resources.client().target("/products").request().get();
		// hate to do this , i wanted to check product fields values but couldn't find easy way to get them
		// from the response object.
		Assert.assertEquals(200,response.getStatus());
	}

	@Test
	public void invalidGetProduct() {
		when(productService.get("2")).thenThrow(EntityNotPresentException.class);
		Response response = resources.client().target("/products/2").request().get();
		Assert.assertEquals(500,response.getStatus());
	}

	// TODO :- need to figure out , how to mock behaviour as passed object will always be changed.
	// because of jackson mapper.
	@Test
	public void invalidGetProducts() {
		//when(productService.get()).thenThrow(Exception.class);
		doThrow(Exception.class).when(productService).get();
		Response response = resources.client().target("/products").request().get();
		Assert.assertEquals(500,response.getStatus());
	}
}