package com.ami.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import org.joda.time.DateTime;
import org.slf4j.LoggerFactory;

import com.ami.api.Attr;
import com.ami.api.Product;
import com.ami.db.ProductDao;
import com.ami.exceptions.EntityNotPresentException;

/**
 * @author: Amit Khandelwal
 * Date: 2/27/17
 */

public class ProductService {
	private final org.slf4j.Logger log = LoggerFactory.getLogger(ProductService.class);

	private ProductDao productDao;

	@Inject
	public ProductService(ProductDao productDao) {
		this.productDao = productDao;
	}

	/**
	 * Add a product and returns newly created product.
	 * @param product product
	 * @return newly created product
	 */
	public Product add(Product product) {
		String id = UUID.randomUUID().toString();
		Attr attr = product.getAttributes();
		String brand = null,title =null;
		BigDecimal price =null;
		if(attr != null){
			brand = attr.getBrand();
			title = attr.getTitle();
			price = attr.getPrice();
		}
		DateTime dateTime = new DateTime(product.getCreatedAt()).toDateTime();
		// Demonstrate the powerfulness of Joda time
		// get the month of created date
		String month = dateTime.monthOfYear().getAsText();
		if(log.isDebugEnabled()){
			log.debug("printing the month using joda time "+month);
		}
		productDao.insert(id,product.getName(),brand,title,price,product.getCreatedBy(),dateTime);
		return productDao.findById(id);

	}

	/**
	 * get a product using the unique id.
	 * @param id id
	 * @return product.
	 */
	public Product get(String id) throws EntityNotPresentException{
		Product product = productDao.findById(id);
		if(product == null){
			throw new EntityNotPresentException("product with id: "+id+ "not present");
		}
		return product;
	}

	/**
	 * get all product.
	 * @return product.
	 */
	public List<Product> get(){
		List<Product> products = productDao.findAll();
		return products;
	}
}
