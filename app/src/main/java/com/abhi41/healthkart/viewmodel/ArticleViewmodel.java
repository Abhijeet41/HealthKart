package com.abhi41.healthkart.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.abhi41.healthkart.model.Articles;
import com.abhi41.healthkart.model.ArticlesItem;
import com.abhi41.healthkart.model.SingleArticle;
import com.abhi41.healthkart.repository.Articlerepo;

import java.util.List;

public class ArticleViewmodel extends AndroidViewModel {

    Articlerepo articlerepo;
    MutableLiveData<ArticlesItem> itemMutableLiveData = new MutableLiveData<>();

    public ArticleViewmodel(@NonNull Application application) {
        super(application);
        articlerepo = new Articlerepo();
    }

    public LiveData<List<ArticlesItem>> getProducts()
    {
        return articlerepo.getArticles();
    }

    public LiveData<List<SingleArticle>> getSingleArticle(String id)
    {
        return articlerepo.getSingleArticle(id);
    }


    public LiveData<Boolean> getProgressBar()
    {
        return articlerepo.getProgressBar();
    }
}
