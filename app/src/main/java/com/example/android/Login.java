package com.example.android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.Retorfit.IPostLogin;
import com.example.android.Retorfit.Model.AuthDto;
import com.example.android.Retorfit.Model.LoginDto;
import com.example.android.Retorfit.Model.SigninResponse;
import com.example.android.Retorfit.RetrofitLoginBuilder;
import com.example.android.Retorfit.RetrofitUserBuilder;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Login extends AppCompatActivity {


GoogleSignInClient mGoogleSignInClient;
    private static int RC_SIGN_IN = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViewById(R.id.bn_login_signup).setOnClickListener(v -> {
            Intent intent=new Intent(Login.this,Signup.class);
            startActivity(intent);
            finish();

        });
        findViewById(R.id.bn_login).setOnClickListener(view -> {
            initApi(generateRequest());



        });

        // Configure sign-in to request the user's ID, email address, and basic
// profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // Set the dimensions of the sign-in button.
        Button button = findViewById(R.id.sign_in_button);
        //button.setSize(SignInButton.SIZE_STANDARD);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        button.setOnClickListener(v -> {
            signIn();
        });

    }
    //    @Override
//    public void onClick(View v) {
//         signIn();
//        }
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
            if (acct != null) {

                String personName = acct.getDisplayName();
                String personGivenName = acct.getGivenName();
                String personFamilyName = acct.getFamilyName();
                String personEmail = acct.getEmail();
                String personId = acct.getId();
                Uri personPhoto = acct.getPhotoUrl();
                Toast.makeText(this , "User Email : " + personEmail , Toast.LENGTH_SHORT).show();
            }
            initApi(generateRequest());
            startActivity(new Intent(this , HomePage.class));
            // Signed in successfully, show authenticated UI.
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.d("Message", e.toString());

        }
    }
    public LoginDto generateRequest()
    {


        SharedPreferences sharedPreferences = getSharedPreferences("com.example.android",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();


        EditText etemail= findViewById(R.id.login_email);
        EditText etpass = findViewById(R.id.login_password);

        editor.putString("em",etemail.getText().toString());

        LoginDto loginDto=new LoginDto();
        loginDto.setUserEmail(etemail.getText().toString().trim());
        loginDto.setPassword(etpass.getText().toString().trim());
        loginDto.setAppId("3");
        System.out.println(loginDto.getUserEmail());
        return loginDto;

    }
    private boolean CheckAllFields() {
        EditText etEmail = findViewById(R.id.login_email);
        EditText etPassword = findViewById(R.id.login_password);

        if (etPassword.length() == 0) {
            etPassword.setError("This field is required");
            return false;
        }

        if (etEmail.length() == 0) {
            etEmail.setError("Email is required");
            return false;
        }
        return true;
    }

    private void initApi(LoginDto loginDto)
    {
        Retrofit retrofit= RetrofitLoginBuilder.getInstance();
        IPostLogin iPostloginApi=retrofit.create(IPostLogin.class);


        Call<SigninResponse> response=iPostloginApi.loginquora(loginDto);

        response.enqueue(new Callback<SigninResponse>() {
            @Override
            public void onResponse(Call<SigninResponse> call, Response<SigninResponse> response) {
//                if(response.body()!=null)
//                {
                    Toast.makeText(Login.this,"Successfull Login",Toast.LENGTH_SHORT).show();
//                    boolean isAllFieldChecked=CheckAllFields();
//                    if(isAllFieldChecked) {

                        Intent k = new Intent(Login.this, HomePage.class);
                        startActivity(k);
                        finish();
//                    }

//                }
//                else
//                {
//                    Toast.makeText(Login.this,"Failure to login",Toast.LENGTH_SHORT).show();
//
//                }
            }

            @Override
            public void onFailure(Call<SigninResponse> call, Throwable t) {
                Toast.makeText(Login.this,"Failure",Toast.LENGTH_SHORT).show();
            }
        });
    }
}