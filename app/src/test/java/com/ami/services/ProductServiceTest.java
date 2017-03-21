package com.ami.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.ami.model.Attr;
import com.ami.model.Product;
import com.ami.db.ProductDao;
import com.ami.exceptions.EntityNotPresentException;

/**
 * @author: Amit Khandelwal
 * Date: 3/4/17
 */

public class ProductServiceTest {
	private ProductDao productDao;
	private ProductService productService;
	private Product product;
	private Product product2;

	private Attr attr;
	private List<Product> products;

	@Before
	public void setup() {
		productDao = mock(ProductDao.class);
		productService = new ProductService(productDao);
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

		// given
		doNothing().when(productDao).insert("1","name","brand","title", new BigDecimal(100.25),null,
				null);
		// given , used when method doesn't return any value.
		doNothing().when(productDao).insert("2","name2","brand","title", new BigDecimal(100.25),
				null,
				null);
		when(productDao.findById("1")).thenReturn(product);
		when(productDao.findAll()).thenReturn(products);

	}

	@Test
	public void get() {
		assertThat(productService.get("1").getName()).isEqualTo(product.getName());
		assertThat(productService.get("1").getAttributes().getBrand()).isEqualTo(product.getAttributes()
				.getBrand());
		assertThat(productService.get("1").getAttributes().getTitle()).isEqualTo(product.getAttributes()
				.getTitle());
		assertThat(productService.get("1").getAttributes().getPrice()).isEqualTo(product.getAttributes()
				.getPrice());
	}

	@Test(expected = EntityNotPresentException.class)
	public void entityNotPresent() {
		productService.get("2");
	}

	@Test
	public void add() {
		assertThat(productService.add(product)).isNotEqualTo(product);

	}

	@Test
	public void getAll() {
		assertThat(productService.get().size()).isEqualTo(2);
	}
}