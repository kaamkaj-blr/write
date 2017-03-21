package com.ami.model;

/**
 * @author: Amit Khandelwal
 * Date: 3/7/17
 */

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.assertj.core.api.Assertions.assertThat;
import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;

import java.math.BigDecimal;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author: Amit Khandelwal
 * Date: 3/4/17
 */

public class ProductRepresentationTest {
	private static final ObjectMapper MAPPER = new ObjectMapper();


	/**
	 * serialize test (will be used when sending response back from resource).
	 * @throws Exception
	 */
	@Test
	public void serializesToJson() throws Exception {
		final Attr attr = new Attr("brand","title", new BigDecimal(1000000));
		Product product = new Product();
		product.setAttributes(attr);
		product.setName("product");
		product.setId("1");
		product.setCreatedBy("Amit");
		product.setUpdatedBy("Khandelwal");
		product.setUpdatedAt("2017-03-03T00:18:49+0530");
		product.setCreatedAt("2017-03-03T00:18:49+0530");

		final String expected = MAPPER.writeValueAsString(
				MAPPER.readValue(fixture("fixtures/product.json"), Product.class));

		assertThat(MAPPER.writeValueAsString(product)).isEqualTo(expected);
	}

	// Using assertReflectionEquals from http://www.unitils.org/tutorial-reflectionassert.html.
	@Test
	public void deserializeFromJson() throws Exception {
		final Attr attr = new Attr("brand","title", new BigDecimal(1000000 ));
		final Product product = new Product();
		product.setAttributes(attr);
		product.setName("product");
		product.setId("1");
		product.setCreatedBy("Amit");
		product.setUpdatedBy("Khandelwal");
		product.setUpdatedAt("2017-03-03T00:18:49+0530");
		product.setCreatedAt("2017-03-03T00:18:49+0530");
		assertReflectionEquals(product,MAPPER.readValue(fixture("fixtures/product.json"), Product.class));
	}
}
