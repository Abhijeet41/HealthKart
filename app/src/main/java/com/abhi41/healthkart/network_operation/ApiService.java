package com.abhi41.healthkart.network_operation;

import com.abhi41.healthkart.model.Articles;
import com.abhi41.healthkart.model.ArticlesItem;
import com.abhi41.healthkart.model.SingleArticle;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {


    @GET("api/v2/articles")
    Observable<List<ArticlesItem>> apiArticles();


    @GET("api/v2/articles")
    Observable<List<SingleArticle>> apiGetData(@Query("id")String id);


}
