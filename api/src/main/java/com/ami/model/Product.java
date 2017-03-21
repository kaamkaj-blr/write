package com.ami.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Product Representation.
 * This is a immutable object, hence can be fairly used in multi-threading env.
 * @author: Amit Khandelwal
 * Date: 2/26/17
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Product {
	private String id;
	private String name;
	private Attr attributes;
	@JsonProperty("created_by")
	private String createdBy;
	@JsonProperty("updated_by")
	private String updatedBy;
	@JsonProperty("created_at")
	private String createdAt;
	@JsonProperty("updated_at")
	private String updatedAt;

	//TODO :- need to check if its really required.
	public Product(){

	}


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}


	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Attr getAttributes() {
		return attributes;
	}

	public void setAttributes(Attr attributes) {
		this.attributes = attributes;
	}

}
