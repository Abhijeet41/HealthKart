package com.abhi41.healthkart.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Articles{

	@SerializedName("Articles")
	private List<ArticlesItem> articles;

	public List<ArticlesItem> getArticles(){
		return articles;
	}

	@Override
	public String toString() {
		return "Articles{" +
				"articles=" + articles +
				'}';
	}
}