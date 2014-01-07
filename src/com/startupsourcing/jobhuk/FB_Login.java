package com.startupsourcing.jobhuk;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.AsyncFacebookRunner.RequestListener;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;
import com.facebook.internal.Utility;

@SuppressWarnings("deprecation")
public class FB_Login extends Activity implements OnClickListener{

	private static String APP_ID = "1404488943131729";
	
	private Facebook facebook;
    private AsyncFacebookRunner mAsyncRunner;
    String FILENAME = "AndroidSSO_data";
    private SharedPreferences mPrefs;
    
    TextView email, password;
    EditText email_edit, password_edit;
    Button submit;
    
    Context c;
 
	
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
		getProfileInformation();
		logoutFromFacebook();
		}

		public void loginToFacebook() {
			
		    mPrefs = getPreferences(MODE_PRIVATE);
		    
		    String access_token = mPrefs.getString("access_token", null);
		    long expires = mPrefs.getLong("access_expires", 0);
		 
		    Log.i("AccessToken and Access_Expires",access_token+"   "+expires);
		    
		    if (access_token != null) {
		        facebook.setAccessToken(access_token);
		    }
		 
		    if (expires != 0) {
		        facebook.setAccessExpires(expires);
		    }
		 
		    if (!facebook.isSessionValid()) {
		    	
		        facebook.authorize(this,new String[] { "email", "publish_stream" },Facebook.FORCE_DIALOG_AUTH,new DialogListener() {
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
		                        editor.clear();
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
		
		@Override
		 public void onActivityResult(int requestCode, int resultCode, Intent data) {
		  super.onActivityResult(requestCode, resultCode, data);
		  facebook.authorizeCallback(requestCode, resultCode, data);
		 }
		
		public void getProfileInformation() {
		    mAsyncRunner.request("me", new RequestListener() {
		        @Override
		        public void onComplete(String response, Object state) {
		            Log.d("Profile", response);
		            String json = response;
		            try {
		                JSONObject profile = new JSONObject(json);
		                // getting name of the user
		                String Uid = profile.getString("id");
		                String name = profile.getString("name");
		                String firstName = profile.getString("first_name");
		                String lastName = profile.getString("last_name");
		                // getting email of the user
		                String email = profile.getString("email");
		                String socialPicture = profile.getString("link");
		                
		                String hometown = profile.getString("hometown");
		                JSONObject Hometown = new JSONObject(hometown);
		                
		                String str = Hometown.getString("name");
		                String[] arr = str.split(",");    
		                String city = arr[0].replaceAll(",","");
		                String State = arr[1];
		                
		                Log.i("Uid and AccessToken",Uid+"     "+mPrefs.getString("access_token", null));
		                Log.i("firstname and lastname",firstName+"  "+lastName);
		                Log.i("email and socialpicture",email+"   "+socialPicture);
		                Log.i("city and state",city+"   "+State);
		                
//		                logoutFromFacebook();
		                try {
							facebook.logout(c);
						} catch (MalformedURLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		                Utility.clearCaches(c);
		                Utility.clearFacebookCookies(c);

		                } catch (JSONException e) {
		                e.printStackTrace();
		            }
		        }
		 
		        @Override
		        public void onIOException(IOException e, Object state) {
		        }
		 
		        @Override
		        public void onFileNotFoundException(FileNotFoundException e,
		                Object state) {
		        }
		 
		        @Override
		        public void onMalformedURLException(MalformedURLException e,
		                Object state) {
		        }
		 
		        @Override
		        public void onFacebookError(FacebookError e, Object state) {
		        }
		    });
		}
		
		@SuppressWarnings("deprecation")
		 public void logoutFromFacebook() {
		
		  mAsyncRunner.logout(this, new RequestListener() {
		   @Override
		   public void onComplete(String response, Object state) {
		    Log.i("Logout from Facebook", response);
		    
		    	facebook.setAccessToken(null);
	            facebook.setAccessExpires(0);
			    
		   }
		 
		   @Override
		   public void onIOException(IOException e, Object state) {
		   }
		 
		   @Override
		   public void onFileNotFoundException(FileNotFoundException e,
		     Object state) {
		   }
		 
		   @Override
		   public void onMalformedURLException(MalformedURLException e,
		     Object state) {
		   }
		 
		   @Override
		   public void onFacebookError(FacebookError e, Object state) {
		   }
		  });
		 }
		 
		}


