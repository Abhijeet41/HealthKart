package com.abhi41.healthkart.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.abhi41.healthkart.R;
import com.abhi41.healthkart.dagger2.SharedPreferenceStorage;
import com.abhi41.healthkart.dagger2.components.MyComponent;
import com.abhi41.healthkart.databinding.ActivitySignUpScreenBinding;
import com.abhi41.healthkart.utils.MyApplication;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;

public class SignUpScreen extends AppCompatActivity {

    private static final String TAG = "SignUpScreen";
    private AwesomeValidation awesomeValidation;
    ActivitySignUpScreenBinding binding;

    MyComponent myComponent;                            //dagger 2 intinalization
    SharedPreferenceStorage sharedPreferenceStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up_screen);

        myComponent = ((MyApplication) getApplication()).getSharedPrefModule();
        sharedPreferenceStorage = myComponent.sharedPreferenceStorage();

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        addValidationToViews();

        binding.idBtnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitForm();
            }
        });
    }

    private void addValidationToViews() {
        awesomeValidation.addValidation(this, R.id.id_edit_email,
                RegexTemplate.NOT_EMPTY, R.string.not_empty);

        awesomeValidation.addValidation(this, R.id.id_edit_email,
                Patterns.PHONE, R.string.invalid_phone);

        awesomeValidation.addValidation(this, R.id.id_edit_name,
                RegexTemplate.NOT_EMPTY, R.string.not_empty);

        awesomeValidation.addValidation(this, R.id.id_edit_Lname,
                RegexTemplate.NOT_EMPTY, R.string.not_empty);

            awesomeValidation.addValidation(this, R.id.id_edit_password,
                    RegexTemplate.NOT_EMPTY, R.string.not_empty);

    }

    private void submitForm() {
        // Validate the form...
        if (awesomeValidation.validate()) {

            String email = sharedPreferenceStorage.getInformation(sharedPreferenceStorage.email);

            if (email.equals(binding.idEditEmail.getText().toString()))
            {
                Toast.makeText(SignUpScreen.this,
                        "Phone Number  Is Already Exist",
                        Toast.LENGTH_SHORT).show();
                return;
            }


            Toast.makeText(this, "Register Successfully", Toast.LENGTH_LONG).show();

            sharedPreferenceStorage.addInformation(sharedPreferenceStorage.name,
                    binding.idEditName.getText().toString());

            sharedPreferenceStorage.addInformation(sharedPreferenceStorage.last_name,
                    binding.idEditLname.getText().toString());

            sharedPreferenceStorage.addInformation(sharedPreferenceStorage.email,
                    binding.idEditEmail.getText().toString());

            sharedPreferenceStorage.addInformation(sharedPreferenceStorage.password,
                    binding.idEditPassword.getText().toString());


            startActivity(new Intent(SignUpScreen.this, LoginScreen.class));
            finish();
        }
    }

}