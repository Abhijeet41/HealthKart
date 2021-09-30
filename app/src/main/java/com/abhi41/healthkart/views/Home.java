package com.abhi41.healthkart.views;


import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;


import com.abhi41.healthkart.R;
import com.abhi41.healthkart.adapters.ArticleAdapter;
import com.abhi41.healthkart.dagger2.SharedPreferenceStorage;
import com.abhi41.healthkart.dagger2.components.MyComponent;
import com.abhi41.healthkart.databinding.FragmentHomeBinding;

import com.abhi41.healthkart.model.ArticlesItem;
import com.abhi41.healthkart.utils.MyApplication;
import com.abhi41.healthkart.viewmodel.ArticleViewmodel;


import java.util.ArrayList;
import java.util.List;


public class Home extends Fragment implements ArticleAdapter.ArticleInterface {

    private ArticleViewmodel viewmodel;
    NavController navController;
    private static final String TAG = "Home";
    private ArticleAdapter articleAdapter;
    FragmentHomeBinding binding;
    private List<ArticlesItem> articlesItemList = new ArrayList<>();

    MyComponent myComponent;
    SharedPreferenceStorage sharedPreferenceStorage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewmodel = new ViewModelProvider(requireActivity()).get(ArticleViewmodel.class);
        navController = Navigation.findNavController(view);

        observable();

        initialization();

        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                // Handle the back button event
                requireActivity().finishAffinity();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(requireActivity(), callback);

    }


    private void observable() {

        viewmodel.getProducts().observe(getViewLifecycleOwner(), new Observer<List<ArticlesItem>>() {
            @Override
            public void onChanged(List<ArticlesItem> articles) {
                articlesItemList.clear();

                articlesItemList.addAll(articles);
                articleAdapter.notifyDataSetChanged();
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

    private void initialization() {

        myComponent = ((MyApplication) getActivity().getApplication()).getSharedPrefModule();
        sharedPreferenceStorage = myComponent.sharedPreferenceStorage();

        articleAdapter = new ArticleAdapter(articlesItemList, getActivity(),this);
        binding.rvArticles.setAdapter(articleAdapter);

    }



    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //TODO Add your menu entries here
        inflater.inflate(R.menu.main_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.logout:

                sharedPreferenceStorage.clearStorage(requireActivity());
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onItemClick(List<ArticlesItem> articlesItem, int position) {

        final Bundle bundle = new Bundle();
        bundle.putString("id", articlesItem.get(position).getId());
        Log.d(TAG, "onItemClick: "+articlesItem.get(position).getId());
        navController.navigate(R.id.action_home2_to_articleDetailScreen,bundle);
    }
}