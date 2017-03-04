package com.ami.db;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.joda.time.DateTime;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import com.ami.api.Attr;
import com.ami.api.Product;

/**
 * @author: Amit Khandelwal
 * Date: 2/26/17
 */
public class ProductMapper implements ResultSetMapper<Product> {

	/**
	 * return a product for a giving id.
	 * @param index
	 * @param r
	 * @param ctx
	 * @return
	 * @throws SQLException
	 */
	@Override
	public Product map(int index, ResultSet r, StatementContext ctx) throws SQLException {
		Product product = new Product();
		Attr attr = new Attr();
		// setting the product attributes
		attr.setBrand(r.getString("brand"));
		attr.setTitle(r.getString("title"));
		attr.setPrice(r.getBigDecimal("price"));
       // setting the product
		product.setId(r.getString("id"));
		product.setAttributes(attr);
		product.setName(r.getString("name"));
		product.setCreatedBy(r.getString("created_by"));
		product.setUpdatedBy(r.getString("updated_by"));
		// default tostring is yyyy-MM-dd'T'HH:mm:ssZ
		String createdAt = new DateTime(r.getTimestamp("created_at")).toString("yyyy-MM-dd'T'HH:mm:ssZ");
		product.setCreatedAt(createdAt);
		// Default ISO format
		String updatedAt = new DateTime(r.getTimestamp("updated_at")).toString("yyyy-MM-dd'T'HH:mm:ssZ");
		product.setUpdatedAt(updatedAt);
		return product;
	}

}
