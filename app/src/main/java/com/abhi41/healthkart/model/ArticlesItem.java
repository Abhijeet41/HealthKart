package com.abhi41.healthkart.model;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import java.util.List;

import com.bumptech.glide.Glide;
import com.google.gson.annotations.SerializedName;

public class ArticlesItem{

	@SerializedName("summary")
	private String summary;

	@SerializedName("featured")
	private boolean featured;

	@SerializedName("publishedAt")
	private String publishedAt;

	@SerializedName("imageUrl")
	private String imageUrl;

	@SerializedName("newsSite")
	private String newsSite;

	@SerializedName("id")
	private String id;

	@SerializedName("title")
	private String title;

	@SerializedName("url")
	private String url;

	@SerializedName("launches")
	private List<LaunchesItem> launches;

	@SerializedName("events")
	private List<Object> events;

	@SerializedName("updatedAt")
	private String updatedAt;

	public String getSummary(){
		return summary;
	}

	public boolean isFeatured(){
		return featured;
	}

	public String getPublishedAt(){
		return publishedAt;
	}

	public String getImageUrl(){
		return imageUrl;
	}

	public String getNewsSite(){
		return newsSite;
	}

	public String getId(){
		return id;
	}

	public String getTitle(){
		return title;
	}

	public String getUrl(){
		return url;
	}

	public List<LaunchesItem> getLaunches(){
		return launches;
	}

	public List<Object> getEvents(){
		return events;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	@Override
	public String toString() {
		return "ArticlesItem{" +
				"summary='" + summary + '\'' +
				", featured=" + featured +
				", publishedAt='" + publishedAt + '\'' +
				", imageUrl='" + imageUrl + '\'' +
				", newsSite='" + newsSite + '\'' +
				", id='" + id + '\'' +
				", title='" + title + '\'' +
				", url='" + url + '\'' +
				", launches=" + launches +
				", events=" + events +
				", updatedAt='" + updatedAt + '\'' +
				'}';
	}

	@BindingAdapter("android:loadImage")
	public static void loadImage(ImageView imageView, String url){
		Glide.with(imageView)
				.load(url)
				.fitCenter()
				.into(imageView);
	}
}