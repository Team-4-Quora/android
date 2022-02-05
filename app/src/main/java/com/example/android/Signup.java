package com.example.android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Signup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        EditText fn = findViewById(R.id.et_signup_name);
        EditText em = findViewById(R.id.et_signup_email);
        EditText pwd = findViewById(R.id.et_signup_password);
        EditText cpwd = findViewById(R.id.et_signup_confirm);

        findViewById(R.id.bn_signup_submit).setOnClickListener(v -> {
            boolean isAllFieldChecked=CheckAllFields();

            if(isAllFieldChecked){
                SharedPreferences sharedPreferences = getSharedPreferences("com.example.medsavvy", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("pass", pwd.getText().toString());
                editor.putString("confirm", cpwd.getText().toString());
                editor.putString("name", fn.getText().toString());
                editor.putString("em", em.getText().toString());
                editor.apply();
//                initApi(createRequest());
                Intent k = new Intent(Signup.this, Category.class);
                startActivity(k);
                finish();
            }
        });



    }

    private boolean CheckAllFields() {
        EditText etFirstName=findViewById(R.id.et_signup_name);
        EditText etPassword=findViewById(R.id.et_signup_password);
        EditText etConfirm=findViewById(R.id.et_signup_confirm);
        EditText etEmail=findViewById(R.id.et_signup_email);

        if (etFirstName.length() == 0) {
            etFirstName.setError("This field is required");
            return false;
        }

        if (etEmail.length() == 0) {
            etEmail.setError("Email is required");
            return false;
        }

        if (etPassword.length() == 0) {
            etPassword.setError("Password is required");
            return false;
        } else if (etPassword.length() < 8) {
            etPassword.setError("Password must be minimum 8 characters");
            return false;
        }

        if(!(etConfirm.getText().toString().equals(etPassword.getText().toString())))
        {
            etConfirm.setError("Doesn't match Password");
            return false;
        }

        // after all validation return true.
        return true;
    }
}