package com.startupsourcing.jobhuk;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;

public class FB_Login extends Activity implements OnClickListener{

	private static String APP_ID = "608969769152874";
	
	private Facebook facebook;
    private AsyncFacebookRunner mAsyncRunner;
    String FILENAME = "AndroidSSO_data";
    private SharedPreferences mPrefs;
    
    TextView email, password;
    EditText email_edit, password_edit;
    Button submit;
 
	
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fb__login);
		
		facebook = new Facebook(APP_ID);
        mAsyncRunner = new AsyncFacebookRunner(facebook);
        
        submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(this);
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		loginToFacebook();
		}

		@SuppressWarnings("deprecation")
		public void loginToFacebook() {
		    mPrefs = getPreferences(MODE_PRIVATE);
		    String access_token = mPrefs.getString("access_token", null);
		    long expires = mPrefs.getLong("access_expires", 0);
		 
		    if (access_token != null) {
		        facebook.setAccessToken(access_token);
		    }
		 
		    if (expires != 0) {
		        facebook.setAccessExpires(expires);
		    }
		 
		    if (!facebook.isSessionValid()) {
		        facebook.authorize(this,
		                new String[] { "email", "publish_stream" },
		                new DialogListener() {
		 
		                    @Override
		                    public void onCancel() {
		                        // Function to handle cancel event
		                    }
		 
		                    @Override
		                    public void onComplete(Bundle values) {
		                        // Function to handle complete event
		                        // Edit Preferences and update facebook acess_token
		                        SharedPreferences.Editor editor = mPrefs.edit();
		                        editor.putString("access_token",
		                                facebook.getAccessToken());
		                        editor.putLong("access_expires",
		                                facebook.getAccessExpires());
		                        editor.commit();
		                    }
		 
		                    @Override
		                    public void onError(DialogError error) {
		                        // Function to handle error
		 
		                    }
		 
		                    @Override
		                    public void onFacebookError(FacebookError fberror) {
		                        // Function to handle Facebook errors
		 
		                    }
		 
		                });
		    }
		
	}

}
