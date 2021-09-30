package com.abhi41.healthkart.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.abhi41.healthkart.R;
import com.abhi41.healthkart.databinding.ActivityDashBoardScreenBinding;

public class DashBoardScreen extends AppCompatActivity {

    ActivityDashBoardScreenBinding binding;

    NavController navController;
    private static final String TAG = "DashBoardScreen";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_dash_board_screen);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dash_board_screen);

        navController = Navigation.findNavController(DashBoardScreen.this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(DashBoardScreen.this, navController);

    }



    @Override
    public boolean onSupportNavigateUp() {
        navController.navigateUp();
        return super.onSupportNavigateUp();
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        return NavigationUI.onNavDestinationSelected(item, navController) || super.onOptionsItemSelected(item);
    }
}