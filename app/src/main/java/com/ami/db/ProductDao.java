package com.ami.db;

import java.math.BigDecimal;
import java.util.List;

import org.joda.time.DateTime;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import com.ami.model.Product;

/**
 * @author: Amit Khandelwal
 * Date: 2/26/17
 */

@RegisterMapper(ProductMapper.class)
public interface ProductDao {
	@SqlUpdate("insert into product (id,name,brand,title,price,created_by,created_at) values (:id,:name,"
			+ ":brand,:title,:price,:createdBy,:createdAt)")
	void insert(@Bind("id") String id, @Bind("name") String name, @Bind("brand") String brand, @Bind
			("title") String title , @Bind("price") BigDecimal price, @Bind("createdBy") String
			createdBy, @Bind("createdAt") DateTime createdAt);

	@SqlQuery("select * from product where id = :id")
	Product findById(@Bind("id") String id);

	@SqlQuery("select * from product")
	List<Product> findAll();
}
