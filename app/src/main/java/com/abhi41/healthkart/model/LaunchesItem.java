package com.abhi41.healthkart.model;

import com.google.gson.annotations.SerializedName;

public class LaunchesItem{

	@SerializedName("provider")
	private String provider;

	@SerializedName("id")
	private String id;

	public String getProvider(){
		return provider;
	}

	public String getId(){
		return id;
	}
}