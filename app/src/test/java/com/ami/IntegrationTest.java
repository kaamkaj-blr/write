package com.ami;


import static org.assertj.core.api.Java6Assertions.assertThat;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.JerseyClientBuilder;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;

import com.ami.model.Attr;
import com.ami.model.BaseResponse;
import com.ami.model.Product;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.dropwizard.jackson.Jackson;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.testing.ConfigOverride;
import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit.DropwizardAppRule;
import liquibase.Liquibase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;

/**
 * @author: Amit Khandelwal
 * Date: 3/5/17
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class IntegrationTest {

    private static final String TMP_FILE = createTempFile();
	private static DBI dbi;
	private static Handle handle;
	private static Liquibase liquibase;
	private static Product product;
	private static Attr attr;
	private static Client client;
	private static ObjectMapper objectMapper = Jackson.newObjectMapper();
	private static String productId;

	@ClassRule
	public static final DropwizardAppRule<RestBestConfiguration> RULE =
			new DropwizardAppRule<RestBestConfiguration>(RestBestApplication.class, ResourceHelpers.resourceFilePath
					("integration-test.yml"), ConfigOverride.config("database.url","jdbc:h2:" + TMP_FILE));

	@BeforeClass
	public static void migrateDb() throws Exception {
		dbi = new DBIFactory().build( RULE.getEnvironment(), RULE.getConfiguration().getDataSourceFactory(), "test" );
		handle = dbi.open();
		migrateDatabase();
		//RULE.getApplication().run("db", "migrate", CONFIG_PATH);
		client = new JerseyClientBuilder().build();
	}

	private static void migrateDatabase() throws LiquibaseException {
		liquibase = new Liquibase("migrations.xml", new ClassLoaderResourceAccessor(), new JdbcConnection(handle.getConnection()));
		liquibase.update("");
	}

	private static String createTempFile() {
		try {
			return File.createTempFile("integration-test", null).getAbsolutePath();
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}

	@Test
	public void testaPostProduct() throws Exception {
		attr = new Attr("brand", "title", new BigDecimal("100.00"));
		product = new Product();
		product.setName("name");
		product.setId("1");
		product.setAttributes(attr);

		Response response = client.target("http://localhost:" + RULE.getLocalPort() +
				"/products")
				.request()
				.post(Entity.json(product));
		// getting the baseresponse object which i am serializing using jackson in my resource.
		BaseResponse baseResponse = response.readEntity(new GenericType<BaseResponse>(){});

		/** finally after spending hour got the solution of how to get the product pojo from my jersey
		 response object. jackson for weird reason converts the object to linkedhashmap read more at
		 http://stackoverflow.com/questions/28821715/java-lang-classcastexception-java-util-linkedhashmap
		 -cannot-be-cast-to-com-test. as in my case i just told jackson to serialize baseresponse object
		 not the output object.
		 Now main problem was to get the product object from BaseResponse which has,
		 metadata,output object as the LinkedHashMap. not the error as its type is defined clearly.
		 So i can't cast Output object to directly in product object and below method from jackson databind
		 comes handly to convert it to product object with the help of http://stackoverflow
		 .com/questions/15430715/casting-linkedhashmap-to-complex-object SO post. pweeee.
		 */
		Product retProduct = objectMapper.convertValue(baseResponse.getOutput(), Product.class);
		assertThat(retProduct.getId()).isNotNull();
		assertThat(retProduct.getName()).isEqualTo(product.getName());
		Assert.assertEquals(201,response.getStatus());
		productId = retProduct.getId();
	}

	@Test()
	public void testbGetProduct() throws Exception {
		Response response = client.target("http://localhost:" + RULE.getLocalPort()
				+ "/products/"+productId).request().get();

		// getting the baseresponse object which i am serializing using jackson in my resource.
		BaseResponse baseResponse = response.readEntity(new GenericType<BaseResponse>(){});
		Product retProduct = objectMapper.convertValue(baseResponse.getOutput(), Product.class);
		assertThat(retProduct.getId()).isNotNull();
		assertThat(retProduct.getName()).isEqualTo(product.getName());
		Assert.assertEquals(200, response.getStatus());
	}

	@Test()
	public void testcGetProducts() throws Exception {
		Response response = client.target("http://localhost:" + RULE.getLocalPort()
				+ "/products/")
				.request()
				.get();
		// getting the baseresponse object which i am serializing using jackson in my resource.
		BaseResponse baseResponse = response.readEntity(new GenericType<BaseResponse>(){});
		List<Product> retProducts = objectMapper.convertValue(baseResponse.getOutput(), new
				TypeReference<List<Product>>() { });
		assertThat(retProducts.get(0).getId()).isNotNull();
		assertThat(retProducts.get(0).getName()).isEqualTo(product.getName());
		Assert.assertEquals(200,response.getStatus());
		Assert.assertEquals(retProducts.size(),1);
	}

}
