package edu.ufcg.es.es_front.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import edu.ufcg.es.es_front.R;


public class LoginActivity extends AppCompatActivity{

    private TextView mStatusTextView;

    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;

    private CallbackManager mCallbackManager;

    private ProgressBar mProgressBar;

    private SignInButton btnGoogleSignIn;
    private LoginButton fbLoginButton;
    private Button logOutButton;


    @Override
    protected void onStart() {
        super.onStart();

        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        // GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.init();

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();


        // Build a GoogleSignInClient with the options specified by gso.
        this.mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        this.mAuth = FirebaseAuth.getInstance();
    }

    private void init() {
        this.mStatusTextView = findViewById(R.id.tvStatus);
        this.mProgressBar = findViewById(R.id.progressBar);
        this.btnGoogleSignIn = findViewById(R.id.btnGoogleSignIn);
        this.fbLoginButton = findViewById(R.id.btn_fb_login);
        this.logOutButton = findViewById(R.id.btnSignOut);


        this.btnGoogleSignIn.setOnClickListener(googleSingIn());
        this.logOutButton.setOnClickListener(googleSingOut());

        mCallbackManager = CallbackManager.Factory.create();
        LoginButton fbLoginButton = findViewById(R.id.btn_fb_login);
        fbLoginButton.setReadPermissions("email", "public_profile");
        fbLoginButton.registerCallback(mCallbackManager, facebookCallback());
    }

    private void showProgressBar(boolean exibir) {
        mProgressBar.setVisibility(exibir ? View.VISIBLE : View.GONE);
    }

    private void updateUI(FirebaseUser user){

        //ninguém logado, carregar tela de login
        if(user == null) {
            this.mStatusTextView.setText("not logged");
            this.btnGoogleSignIn.setVisibility(View.VISIBLE);
            this.fbLoginButton.setVisibility(View.VISIBLE);
            this.logOutButton.setVisibility(View.GONE);
        } else { //alguém já logado, redirecionar para tela de home
            this.mStatusTextView.setText("logged in");
            this.btnGoogleSignIn.setVisibility(View.GONE);
            this.fbLoginButton.setVisibility(View.GONE);
            this.logOutButton.setVisibility(View.VISIBLE);
        }

        showProgressBar(false);


    }

    private OnClickListener googleSingIn(){
        return new OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgressBar(true);

                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, 9001);
            }
        };
    }

    private OnClickListener googleSingOut(){
        return new OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgressBar(true);
                signOut();
            }
        };
    }

    private FacebookCallback<LoginResult> facebookCallback(){
        return new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("FacebookActivity", "facebook:onSuccess" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d("FacebookActivity", "facebook:onCancel");
                updateUI(null);
            }

            @Override
            public void onError(FacebookException error) {
                Log.d("FacebookActivity", "facebook:onError", error);
                updateUI(null);
            }
        };
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 9001) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                Log.w("GoogleActivity", "Google sign in failed", e);
                updateUI(null);
            }
        } else {
            mCallbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        Log.d("GoogleActivity", "firebaseAuthWithGoogle:" + account.getId());
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("GoogleActivity", "signInWithCredential: success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            Log.w("GoogleActivity", "signInWithCredentials: failure", task.getException());
                            updateUI(null);
                        }
                    }
                });
    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d("FacebookActivity", "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("FacebookActivity", "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            Log.d("FacebookActivity", "signInWithCredential:failure", task.getException());
                            updateUI(null);
                        }
                    }
                });
    }

    private void signOut() {
        mAuth.signOut();
        mGoogleSignInClient.signOut().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        updateUI(null);
                    }
                }
        );
        LoginManager.getInstance().logOut();
        updateUI(null);
    }
}
