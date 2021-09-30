package com.abhi41.healthkart.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.abhi41.healthkart.model.Articles;
import com.abhi41.healthkart.model.ArticlesItem;
import com.abhi41.healthkart.model.SingleArticle;
import com.abhi41.healthkart.network_operation.ApiClient;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class Articlerepo {


    private MutableLiveData<List<ArticlesItem>> mutableArticles;
    private MutableLiveData<List<SingleArticle>> mutableSingleArticle = new MutableLiveData<>();
    private MutableLiveData<Boolean> progressbar = new MutableLiveData<>();

    public LiveData<List<ArticlesItem>> getArticles() {
        if (mutableArticles == null) {
            mutableArticles = new MutableLiveData<>();
            loadArticles();
        }
        return mutableArticles;
    }

    public LiveData<List<SingleArticle>> getSingleArticle(String id) {
        loadDetailArticles(id);
        return mutableSingleArticle;
    }

    public LiveData<Boolean> getProgressBar() {

        return progressbar;
    }

    private void loadArticles() {

        progressbar.setValue(true);

        CompositeDisposable compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(ApiClient.getRetrofitCLient().apiArticles()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<ArticlesItem>>() {


                    @Override
                    public void onNext(@NonNull List<ArticlesItem> articlesItems) {
                        try {
                            mutableArticles.setValue(articlesItems);
                            progressbar.setValue(false);


                        } catch (Exception e) {
                            e.printStackTrace();
                            progressbar.setValue(false);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        e.printStackTrace();
                        mutableArticles.setValue(null);
                        progressbar.setValue(false);
                    }

                    @Override
                    public void onComplete() {

                    }
                }));

    }

    private void loadDetailArticles(String id) {

        progressbar.setValue(true);


        CompositeDisposable compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(ApiClient.getRetrofitCLient().apiGetData(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<SingleArticle>>() {

                    @Override
                    public void onNext(@NonNull List<SingleArticle> singleArticles) {
                        mutableSingleArticle.setValue(singleArticles);
                        progressbar.setValue(false);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mutableSingleArticle.setValue(null);
                        progressbar.setValue(false);
                    }

                    @Override
                    public void onComplete() {

                    }
                }));

    }

}
