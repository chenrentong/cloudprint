package com.dascom.cloudprint.entity.auth;

import org.springframework.data.mongodb.core.mapping.Field;

public class CollectionUsersRole {
	
	@Field("class")
	private String category;
	private String role;
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	@Override
	public String toString() {
		return "CollectionUsersRole [category=" + category + ", role=" + role
				+ "]";
	}
	
	
}
