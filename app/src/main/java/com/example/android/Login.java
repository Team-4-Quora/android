package com.example.android;

import androidx.annotation.NonNull;
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
import com.example.android.Retorfit.Model.LoginDto;
import com.example.android.Retorfit.Model.SigninResponse;
import com.example.android.Retorfit.RetrofitLoginBuilder;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import kotlin.Metadata;
import java.util.HashMap;
import kotlin.jvm.internal.Intrinsics;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

@Metadata(
        mv = {1, 1, 18},
        bv = {1, 0, 3},
        k = 1,
        d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\bH\u0014R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082D¢\u0006\u0002\n\u0000¨\u0006\t"},
        d2 = {"Lcom/example/android/Login;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "TAG", "", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "app_debug"}
)

public class Login extends AppCompatActivity {
    boolean isAllFieldsChecked = false;
    private final String TAG = "LoginActivity";
    private final String Token_Name = "";
    private HashMap _$_findViewCache;
    public String st = "";

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

            FirebaseInstanceId var10000 = FirebaseInstanceId.getInstance();
            Intrinsics.checkExpressionValueIsNotNull(var10000, "FirebaseInstanceId.getInstance()");
            var10000.getInstanceId().addOnSuccessListener((OnSuccessListener)(new OnSuccessListener() {
                // $FF: synthetic method
                // $FF: bridge method
                public void onSuccess(Object var1) {
                    this.onSuccess((InstanceIdResult)var1);
                }

                public final void onSuccess(InstanceIdResult it) {
//                    TextView var10000 = findViewById(R.id.textfield);
                    Intrinsics.checkExpressionValueIsNotNull(it, "it");
//                    var10000.append((CharSequence)it.getToken());
                    st = it.getToken();
                    //ReturnTopic(st);
                    initApi(generateRequest(it.getToken()));

                    SharedPreferences sharedPreferences=getSharedPreferences("com.example.android",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("tokenid",it.getToken());
                    editor.apply();
                    Log.d("tokenid", String.valueOf(it.getToken()));
                }
            }));
            FirebaseMessaging.getInstance().subscribeToTopic("testnotificationjava123").addOnCompleteListener((OnCompleteListener)(new OnCompleteListener() {
                public final void onComplete(@NonNull Task task) {
                    Intrinsics.checkParameterIsNotNull(task, "task");
                    if (task.isSuccessful()) {
                        Log.d(Login.this.TAG, "Global topic subscription successful");
                    } else {
                        String var10000 = Login.this.TAG;
                        StringBuilder var10001 = (new StringBuilder()).append("Global topic subscription failed. Error: ");
                        Exception var10002 = task.getException();
                        Log.e(var10000, var10001.append(var10002 != null ? var10002.getLocalizedMessage() : null).toString());
                    }

                }
            }));



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
            initApi(generateRequest(""));
            startActivity(new Intent(this , HomePage.class));
            // Signed in successfully, show authenticated UI.
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.d("Message", e.toString());

        }
    }
    public LoginDto generateRequest(String token)
    {


        SharedPreferences sharedPreferences = getSharedPreferences("com.example.android",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();


        EditText etemail= findViewById(R.id.login_email);
        EditText etpass = findViewById(R.id.login_password);

        editor.putString("em",etemail.getText().toString());
        editor.apply();

        LoginDto loginDto=new LoginDto();
        loginDto.setUserEmail(etemail.getText().toString().trim());
        loginDto.setPassword(etpass.getText().toString().trim());
        loginDto.setAppId("3");

        loginDto.setDeviceId(token);
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
    public View _$_findCachedViewById(int var1) {
        if (this._$_findViewCache == null) {
            this._$_findViewCache = new HashMap();
        }

        View var2 = (View)this._$_findViewCache.get(var1);
        if (var2 == null) {
            var2 = this.findViewById(var1);
            this._$_findViewCache.put(var1, var2);
        }

        return var2;
    }

    public void _$_clearFindViewByIdCache() {
        if (this._$_findViewCache != null) {
            this._$_findViewCache.clear();
        }

    }
}