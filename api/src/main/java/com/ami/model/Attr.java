package com.ami.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonRootName;

/**
 * @author: Amit Khandelwal
 * Date: 3/2/17
 */

@JsonRootName("attributes")
public class Attr {
	private String brand;
	private String title;
	private BigDecimal price;


	public Attr() {

	}

	public Attr(String brand, String title, BigDecimal price) {
		this.brand = brand;
		this.title = title;
		this.price = price;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

}
