package com.abhi41.healthkart.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.abhi41.healthkart.R;
import com.abhi41.healthkart.dagger2.SharedPreferenceStorage;
import com.abhi41.healthkart.dagger2.components.MyComponent;
import com.abhi41.healthkart.databinding.ActivityLoginScreenBinding;
import com.abhi41.healthkart.utils.MyApplication;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;

public class LoginScreen extends AppCompatActivity {
    ActivityLoginScreenBinding binding;
    MyComponent myComponent;
    SharedPreferenceStorage sharedPreferenceStorage;
    private AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_login_screen);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login_screen);

        initialiZation();

        binding.idBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                submitLogin();

            }
        });

        binding.idBtnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginScreen.this, SignUpScreen.class));
            }
        });

    }

    private void initialiZation() {
        //prefs = getSharedPreferences(Const.MY_PREFS_NAME, MODE_PRIVATE);
        myComponent = ((MyApplication) getApplication()).getSharedPrefModule();
        sharedPreferenceStorage = myComponent.sharedPreferenceStorage();

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        awesomeValidation.addValidation(this, R.id.id_edit_email,
                RegexTemplate.NOT_EMPTY, R.string.not_empty);

        awesomeValidation.addValidation(this, R.id.id_edit_email,
                Patterns.PHONE, R.string.invalid_phone);

        awesomeValidation.addValidation(this, R.id.id_edit_password,
                RegexTemplate.NOT_EMPTY, R.string.not_empty);

    }

    private void submitLogin() {

        String email = sharedPreferenceStorage.getInformation(sharedPreferenceStorage.email);
        String password = sharedPreferenceStorage.getInformation(sharedPreferenceStorage.password);


        if (binding.idEditEmail.getText().toString().equals(email)
                && binding.idEditPassword.getText().toString().equals(password))
        {
            sharedPreferenceStorage.setIsLogin(sharedPreferenceStorage.isLogin,true);
            startActivity(new Intent(LoginScreen.this, DashBoardScreen.class));
            finish();
        }else {
            Toast.makeText(LoginScreen.this, "Invalid Email Or Password", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}