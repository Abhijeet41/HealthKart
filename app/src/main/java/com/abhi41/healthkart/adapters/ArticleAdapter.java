package com.abhi41.healthkart.adapters;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.abhi41.healthkart.R;
import com.abhi41.healthkart.databinding.SingleArticleRowBinding;
import com.abhi41.healthkart.model.Articles;
import com.abhi41.healthkart.model.ArticlesItem;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ArticleAdapter extends ListAdapter<ArticlesItem,ArticleAdapter.ViewHolder> {

    /*private List<ArticlesItem> articlesList;
    private Context context;*/
    private ArticleInterface articleInterface;



    public ArticleAdapter(ArticleInterface articleInterface) {
        super(ArticlesItem.itemCallback);
        this.articleInterface = articleInterface;
    }

    @NonNull
    @Override
    public ArticleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        SingleArticleRowBinding binding = SingleArticleRowBinding.inflate(inflater, parent, false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ArticlesItem articles = getItem(position);

        holder.binding.setArticle(articles);
        holder.binding.executePendingBindings();

        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            Date date = dateFormat.parse(articles.getPublishedAt());//You will get date object relative to server/client timezone wherever it is parsed
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); //If you need time just put specific format for time like 'HH:mm:ss'
            holder.binding.txtDate.setText(formatter.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.binding.constraintMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                articleInterface.onItemClick(getCurrentList(),position);
            }
        });




    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        SingleArticleRowBinding binding;

        public ViewHolder(@NonNull SingleArticleRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface ArticleInterface{
        void onItemClick(List<ArticlesItem> articlesItem, int position);
    }
}
