package com.example.android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.Retorfit.IPostLogin;
import com.example.android.Retorfit.Model.SignupResponse;
import com.example.android.Retorfit.Model.UserRegister;
import com.example.android.Retorfit.RetrofitLoginBuilder;
import com.example.android.Retorfit.RetrofitUserBuilder;

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
            initApi();

        });

//        initApi();



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


    private void initApi(){
        Retrofit retrofit= RetrofitLoginBuilder.getInstance();
//        IPostLogin iPostLoginApi=retrofit.create(IPostLogin.class);


        UserRegister userRegister=new UserRegister();

        EditText etFirstName=findViewById(R.id.et_signup_name);
        EditText etPassword=findViewById(R.id.et_signup_password);
        EditText etEmail=findViewById(R.id.et_signup_email);
        userRegister.setName(etFirstName.getText().toString());
        userRegister.setPassword(etPassword.getText().toString());
//        userDto.setPoints(new Long(0));
        // userDto.setUsername(etEmail.getText().toString());
        userRegister.setUserEmail(etEmail.getText().toString());
        userRegister.setAppId("3");


        System.out.println("userregister here:::::"+userRegister.getUserEmail());

        Call<SignupResponse> userregister=retrofit.create(IPostLogin.class).signupquora(userRegister);

       userregister.enqueue(new Callback<SignupResponse>() {
           @Override
           public void onResponse(Call<SignupResponse> call, Response<SignupResponse> response) {
            if(!response.body().getStatus().equals("failure")){

                    Intent k = new Intent(Signup.this, Category.class);
                    startActivity(k);


                Toast.makeText(Signup.this,"Success",Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(Signup.this,"Unable to Register",Toast.LENGTH_SHORT).show();
            }
           }

           @Override
           public void onFailure(Call<SignupResponse> call, Throwable t) {
               Toast.makeText(Signup.this,"Failure",Toast.LENGTH_SHORT).show();
           }
       });


    }
}