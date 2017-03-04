package com.ami.db;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;

import com.ami.api.Product;

import liquibase.exception.LiquibaseException;

/**
 * @author: Amit Khandelwal
 * Date: 3/4/17
 */

@FixMethodOrder(

)
public class ProductDaoTest extends DaoTest {

	ProductDao productDao;

	@Before
	public void setupTests() throws IOException, LiquibaseException {
		super.setup();
		productDao = dbi.onDemand(ProductDao.class);
		productDao.insert("1","name","brand","title",new BigDecimal(110.23),"amit",null);
	}

	@Test
	public void createProduct() {
		Product product = productDao.findById("1");
		assertThat(product.getName()=="name").isEqualTo(true);
	}

	@Test
	public void getProducts() {
		List<Product> products = productDao.findAll();
		assertThat(products.size() == 1).isEqualTo(true);
	}


}

