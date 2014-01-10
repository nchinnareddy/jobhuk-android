package com.startupsourcing.jobhuk;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Twitter_Login extends Activity {

	static String TWITTER_CONSUMER_KEY = "QcgQa9I0Sr2ZTK2wUIeSXA";
    static String TWITTER_CONSUMER_SECRET = "X51KozroJ0G20s9YlislierZeyUYfIupdjYRDUalNec";
 
    // Preference Constants
    static String PREFERENCE_NAME = "twitter_oauth";
    static final String PREF_KEY_OAUTH_TOKEN = "2282000941-8pqbdiqB3ppHilyHz1NezQbqUSa1fGfuQpj7eEl";
    static final String PREF_KEY_OAUTH_SECRET = "thMCMVq2M04ErSu0mccmOvUoNlGhiFEaanuc1UTN6cQHe";
    static final String PREF_KEY_TWITTER_LOGIN = "isTwitterLogedIn";
 
    static final String TWITTER_CALLBACK_URL = "www.jobhuk.com";
 
    // Twitter oauth urls
    static final String URL_TWITTER_AUTH = "auth_url";
    static final String URL_TWITTER_OAUTH_VERIFIER = "oauth_verifier";
    static final String URL_TWITTER_OAUTH_TOKEN = "oauth_token";
    
    // Twitter
    private static Twitter twitter;
    private static RequestToken requestToken;
     
    // Shared Preferences
    private static SharedPreferences mSharedPreferences;
    
    // Login button
    Button btnLoginTwitter;

    // Progress dialog
    ProgressDialog pDialog;
 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_twitter__login);
		
		Boolean network_check = isNetAvailable(getApplicationContext());
		
		if (android.os.Build.VERSION.SDK_INT > 9) {
	        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	        StrictMode.setThreadPolicy(policy);
	        }
		
        // All UI elements
        btnLoginTwitter = (Button) findViewById(R.id.btnLoginTwitter);

        // Shared Preferences
        mSharedPreferences = getPreferences(MODE_PRIVATE);
        
        btnLoginTwitter.setOnClickListener(new View.OnClickListener() {
        	 
            @Override
            public void onClick(View arg0) {
                // Call login twitter function
                loginToTwitter();
            }
        });
     
		
	}

	public Boolean isNetAvailable(Context con)  {

        try{
            ConnectivityManager connectivityManager = (ConnectivityManager)      
                                                                      con.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo wifiInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            NetworkInfo mobileInfo =
                                 connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (wifiInfo.isConnected() || mobileInfo.isConnected()) {
                return true;
            }
        }
        catch(Exception e){
           e.printStackTrace();
        }
        return false;
    }
	
	/**
     * Function to login twitter
     * */
    private void loginToTwitter() {
        // Check if already logged in
        if (!isTwitterLoggedInAlready()) {
            ConfigurationBuilder builder = new ConfigurationBuilder();
            builder.setOAuthConsumerKey(TWITTER_CONSUMER_KEY);
            builder.setOAuthConsumerSecret(TWITTER_CONSUMER_SECRET);
            Configuration configuration = builder.build();
             
            TwitterFactory factory = new TwitterFactory(configuration);
            twitter = factory.getInstance();
                      
            SharedPreferences.Editor editor = mSharedPreferences.edit();
//          editor.putBoolean(PREF_KEY_TWITTER_LOGIN,true);
            editor.commit();
            editor.clear();
 
            try {
                requestToken = twitter.getOAuthRequestToken(TWITTER_CALLBACK_URL);
                Log.i("TwitterToken",""+requestToken);

                this.startActivity(new Intent(Intent.ACTION_VIEW, Uri
                        .parse(requestToken.getAuthenticationURL())));
                
                onNewIntent(getIntent());
                
//                Status status = twitter.updateStatus("hello");
//                Log.e("Successfully updated the status to [", ""  + status.getText() + "].");
                
            } catch (TwitterException e) {
                e.printStackTrace();
            }
        } else {
            // user already logged into twitter
            Toast.makeText(getApplicationContext(),
                    "Already Logged into twitter", Toast.LENGTH_LONG).show();
        }
    }
    
    protected void onPause()
    {
    	super.onPause();
    	Log.i("Hello","onPause()");
    }
    
    protected void onResume()
    {
    	super.onResume();
    	Log.i("Hello","onResume()");
    }
    
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.i("Hello_TWitter",""+intent.getData());
        setIntent(intent);
    }
 
    /**
     * Check user already logged in your application using twitter Login flag is
     * fetched from Shared Preferences
     * */
    private boolean isTwitterLoggedInAlready() {
        // return twitter login status from Shared Preferences
        return mSharedPreferences.getBoolean(PREF_KEY_TWITTER_LOGIN, false);
    }

}
