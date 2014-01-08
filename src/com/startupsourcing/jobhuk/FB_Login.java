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
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.AsyncFacebookRunner.RequestListener;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;

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
		new moveOut(this).execute();
//		logoutFromFacebook();
		}

	public void logoutFromFacebook() {
		// changes ares Required in Following line
		mAsyncRunner.logout(FB_Login.this, new RequestListener() {
		
		@Override
		public void onComplete(String response, Object state) {
			// TODO Auto-generated method stub
			Log.d("Logout from Facebook", response);
		    if (Boolean.parseBoolean(response) == true) {
		        Intent intent = new Intent(getBaseContext(),JobsDescription.class);
		       startActivity(intent);
		    }
		}

		@Override
		public void onIOException(IOException e, Object state) {
			// TODO Auto-generated method stub
			}

		@Override
		public void onFileNotFoundException(FileNotFoundException e,
				Object state) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onMalformedURLException(MalformedURLException e,
				Object state) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onFacebookError(FacebookError e, Object state) {
			// TODO Auto-generated method stub
			
		}});
		}

		public void loginToFacebook() {
			
		    mPrefs = getPreferences(MODE_PRIVATE);
		    
		    String access_token = mPrefs.getString("access_token", null);
		    long expires = mPrefs.getLong("access_expires", 0);
		 
		    Log.i("AccessToken and Access_Expires",access_token+"   "+expires);
		    
		    if (access_token != null) {
		        facebook.setAccessToken(access_token);
		        Toast.makeText(getApplicationContext(), "You Already Login",
	                    Toast.LENGTH_SHORT).show();
		        SharedPreferences.Editor editor = mPrefs.edit();
                editor.putString("access_token",
                        null);
                editor.commit();
                editor.clear();
		    }
		 
		    if (expires != 0) {
		        facebook.setAccessExpires(expires);
		        Toast.makeText(getApplicationContext(), "You Already Login",
	                    Toast.LENGTH_SHORT).show();
		        SharedPreferences.Editor editor = mPrefs.edit();
                editor.putLong("access_expires",
                        0);
                editor.commit();
                editor.clear();
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
		
		public void getProfileInformation() {
		    mAsyncRunner.request("me", new RequestListener() {
		        @Override
		        public void onComplete(String response, Object state) {
		            Log.d("Profile", response);
		            String json = response;
		            try {
		                JSONObject profile = new JSONObject(json);
		                String Uid = profile.getString("id");
		                String firstName = profile.getString("first_name");
		                String lastName = profile.getString("last_name");

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
		
		public class moveOut extends AsyncTask<Void, Void, Void>
		{
			
			Context c;
			public moveOut(FB_Login fb_Login) {
				// TODO Auto-generated constructor stub
				c = fb_Login;
			}

			@Override
			protected Void doInBackground(Void... params) {
				
//				try {
//					String res = facebook.logout(FB_Login.this);
//					Log.i("Result",res);
//				} catch (MalformedURLException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
				
				mAsyncRunner.logout(FB_Login.this, new RequestListener() {
					
					@Override
					public void onComplete(String response, Object state) {
						// TODO Auto-generated method stub
						Log.d("Logout from Facebook", response);
					    if (Boolean.parseBoolean(response) == true) {
					        Intent intent = new Intent(getBaseContext(),JobsDescription.class);
					        startActivity(intent);
					    }
					}

					@Override
					public void onIOException(IOException e, Object state) {
						// TODO Auto-generated method stub
						}

					@Override
					public void onFileNotFoundException(FileNotFoundException e,
							Object state) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void onMalformedURLException(MalformedURLException e,
							Object state) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void onFacebookError(FacebookError e, Object state) {
						// TODO Auto-generated method stub
						
					}});
//				Session.getActiveSession().closeAndClearTokenInformation();
				return null;
			}
			
		}

		}


