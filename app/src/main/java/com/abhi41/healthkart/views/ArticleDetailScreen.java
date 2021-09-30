package com.abhi41.healthkart.views;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.abhi41.healthkart.R;
import com.abhi41.healthkart.dagger2.SharedPreferenceStorage;
import com.abhi41.healthkart.dagger2.components.MyComponent;
import com.abhi41.healthkart.databinding.FragmentArticleDetailScreenBinding;
import com.abhi41.healthkart.model.ArticlesItem;
import com.abhi41.healthkart.model.SingleArticle;
import com.abhi41.healthkart.utils.MyApplication;
import com.abhi41.healthkart.viewmodel.ArticleViewmodel;
import com.bumptech.glide.Glide;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class ArticleDetailScreen extends Fragment {
    private static final String TAG = "ArticleDetailScreen";
    private ArticleViewmodel viewmodel;

    MyComponent myComponent;
    SharedPreferenceStorage sharedPreferenceStorage;
    FragmentArticleDetailScreenBinding binding;
    String imageUrl = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentArticleDetailScreenBinding.inflate(inflater, container, false);
        viewmodel = new ViewModelProvider(requireActivity()).get(ArticleViewmodel.class);

        myComponent = ((MyApplication) getActivity().getApplication()).getSharedPrefModule();
        sharedPreferenceStorage = myComponent.sharedPreferenceStorage();


        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);


        try {
            String id = getArguments().getString("id");
            observable(id);
            Log.d(TAG, "getArguments: " + id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                // Handle the back button event
                navController.navigate(R.id.action_articleDetailScreen_to_home2);
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(requireActivity(), callback);

    }

    private void observable(String id) {

        viewmodel.getSingleArticle(id).observe(getViewLifecycleOwner(), new Observer<List<SingleArticle>>() {
            @Override
            public void onChanged(List<SingleArticle> singleArticles) {
                if (singleArticles != null) {
                    try {
                        Log.d(TAG, "singleArticle: " + singleArticles.get(0).toString());

                        binding.txtTitle.setText(singleArticles.get(0).getTitle());
                        binding.txtDescription.setText(singleArticles.get(0).getSummary());
                        binding.txtNewsSite.setText(singleArticles.get(0).getNewsSite());
                        imageUrl = singleArticles.get(0).getImageUrl();

                        Glide.with(requireActivity())
                                .load(imageUrl)
                                .fitCenter()
                                .into(binding.ivArticle);

                        try {
                            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                            Date date = dateFormat.parse(singleArticles.get(0).getPublishedAt());//You will get date object relative to server/client timezone wherever it is parsed
                            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); //If you need time just put specific format for time like 'HH:mm:ss'
                            binding.txtDate.setText(formatter.format(date));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        viewmodel.getProgressBar().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    binding.spinKit.setVisibility(View.VISIBLE);
                } else {
                    binding.spinKit.setVisibility(View.GONE);
                }
            }
        });

    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //TODO Add your menu entries here
        inflater.inflate(R.menu.detail_article_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.logout:

                sharedPreferenceStorage.clearStorage(requireActivity());
                return true;

            case R.id.download:

                Dexter.withContext(requireActivity())
                        .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse response) {

                                downloadImage(imageUrl);
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse response) {
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                intent.setData(Uri.parse("package:" + requireContext().getPackageName()));
                                requireContext().startActivity(intent);
                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {/* ... */}
                        }).check();



                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void downloadImage(String imageUrl) {
        String fname = "Image-" + System.currentTimeMillis() + ".jpg";
        //String root = Environment.getExternalStorageDirectory().getAbsolutePath();


        DownloadManager downloadManager = null;
        downloadManager = (DownloadManager) getActivity().getSystemService(Context.DOWNLOAD_SERVICE);
        Uri downloadUri = Uri.parse(imageUrl);


        DownloadManager.Request request = new DownloadManager.Request(downloadUri);

        String folder = "/Download";

        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE |
                DownloadManager.Request.NETWORK_WIFI);
        request.setAllowedOverRoaming(false);
        request.setTitle(fname);
        request.setMimeType("image/jpeg");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        try { //V28 Below
            request.setDestinationInExternalPublicDir(folder, fname);//v 28 allow to create and it deprecated method(v28+)

        } catch (Exception e) {

            //For Android  28+
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fname);//(Environment.DIRECTORY_PICTURES,"picname.jpeg")
        }
        downloadManager.enqueue(request);
    }


}