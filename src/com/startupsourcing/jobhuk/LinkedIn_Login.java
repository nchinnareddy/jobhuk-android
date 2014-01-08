package com.startupsourcing.jobhuk;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.code.linkedinapi.client.LinkedInApiClient;
import com.google.code.linkedinapi.client.LinkedInApiClientFactory;
import com.google.code.linkedinapi.client.oauth.LinkedInAccessToken;
import com.google.code.linkedinapi.client.oauth.LinkedInOAuthService;
import com.google.code.linkedinapi.client.oauth.LinkedInOAuthServiceFactory;
import com.google.code.linkedinapi.client.oauth.LinkedInRequestToken;
import com.google.code.linkedinapi.schema.Companies;
import com.google.code.linkedinapi.schema.Company;
import com.google.code.linkedinapi.schema.Connections;
import com.google.code.linkedinapi.schema.Group;
import com.google.code.linkedinapi.schema.Groups;
import com.google.code.linkedinapi.schema.Person;

public class LinkedIn_Login extends Activity implements OnClickListener{
	
	public static final String CONSUMER_KEY = "755j7sdwb80286";
	public static final String CONSUMER_SECRET = "6SM8WNo3VMVcxOCX";

	public static final String APP_NAME = "firstlinked";
	public static final String OAUTH_CALLBACK_SCHEME = "https";
	public static final String OAUTH_CALLBACK_HOST = "";
	public static final String OAUTH_CALLBACK_URL = OAUTH_CALLBACK_SCHEME
	+ "://www.jobhuk.com" + OAUTH_CALLBACK_HOST;
	
	LinkedInOAuthService oAuthService = LinkedInOAuthServiceFactory
			.getInstance().createLinkedInOAuthService(CONSUMER_KEY,
			CONSUMER_SECRET);
	LinkedInApiClientFactory factory = LinkedInApiClientFactory
			.newInstance(CONSUMER_KEY, CONSUMER_SECRET);
			LinkedInRequestToken linkedIn_Token;
			LinkedInApiClient client;
			
			TextView tv = null;
			Button signIn, btn_status, post;
			EditText currentStatus;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_linkedin__login);
		
		if (android.os.Build.VERSION.SDK_INT > 9) {
	        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	        StrictMode.setThreadPolicy(policy);
	        }
		
		tv = (TextView) findViewById(R.id.tv);
		signIn = (Button) findViewById(R.id.btn_sign);
		btn_status = (Button) findViewById(R.id.btn_post);
		post = (Button) findViewById(R.id.btn_send);
		currentStatus = (EditText) findViewById(R.id.edt_post);

		signIn.setOnClickListener(this);
		btn_status.setOnClickListener(this);
		post.setOnClickListener(this);
		
		linkedIn_Token = oAuthService.getOAuthRequestToken(OAUTH_CALLBACK_URL);
		Log.i("Token",""+linkedIn_Token);
		Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(linkedIn_Token
		.getAuthorizationUrl()));
		startActivity(i);
		
//		onNewIntent(getIntent());
	}
	
//	@Override
//	protected void onNewIntent(Intent intent) 
//	{
//	  super.onNewIntent(intent);
//	  Log.i("Hello","How are you");
//	  //code
//	}
	
	@Override
	public void onDestroy() {
	    Log.i("Startup", "onDestroy()");
	    super.onDestroy();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_sign:
//			new request().execute();
		linkedIn_Token = oAuthService.getOAuthRequestToken(OAUTH_CALLBACK_URL);
		Log.i("Token",""+linkedIn_Token);
		Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(linkedIn_Token
		.getAuthorizationUrl()));
		startActivity(i);
//		onNewIntent(i);
		break;
		case R.id.btn_post:
		post.setVisibility(View.VISIBLE);
		currentStatus.setVisibility(View.VISIBLE);
		break;
		}
	}
/*	
	
	
	public class request extends AsyncTask<Void, Void, Void>
	{

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			linkedIn_Token = oAuthService.getOAuthRequestToken(OAUTH_CALLBACK_URL);
			Log.i("URL",linkedIn_Token.getAuthorizationUrl());
			Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(linkedIn_Token
			.getAuthorizationUrl()));
			startActivity(i);
			return null;
		}
		
	}*/
	

@Override
protected void onNewIntent(Intent intent) {
super.onNewIntent(intent);
setIntent(intent);
btn_status.setVisibility(View.VISIBLE);
Log.i("Hello","onNewIntent()");
String verifier = intent.getData().getQueryParameter("oauth_verifier");
Log.i("Hello",""+verifier);
LinkedInAccessToken accessToken = oAuthService.getOAuthAccessToken(linkedIn_Token, verifier);
client = factory.createLinkedInApiClient(accessToken);
client.postNetworkUpdate("LinkedIn Android app test");
Person profile = client.getProfileForCurrentUser();

post.setOnClickListener(new OnClickListener() {

@Override
public void onClick(View v) {
// TODO Auto-generated method stub
String status = currentStatus.getText().toString();
client.updateCurrentStatus(status);
currentStatus.setText("");
}
});

// for update status......
//

// Person profile = client.getProfileById(id);
Log.e("Name:",
"" + profile.getFirstName() + " " + profile.getLastName());
Log.e("BirthDate:", "" + profile.getDateOfBirth());
Log.e("Headline:", "" + profile.getHeadline());
Log.e("Summary:", "" + profile.getSummary());
Log.e("Industry:", "" + profile.getIndustry());
Log.e("Picture:", "" + profile.getPictureUrl());
Log.e("Picture:", "" + profile.getApiStandardProfileRequest() + "\n"
+ profile.getSiteStandardProfileRequest().getUrl());

tv.setText(profile.getLastName() + " " + profile.getFirstName());

Groups categoryImpl = client.getSuggestedGroups();
for (Group p : categoryImpl.getGroupList()) {
Log.e("Name", "" + p.getName());
Log.e("      ", "" + "*****************");
}


Companies companies = client.getFollowedCompanies();
for (Company p : companies.getCompanyList()) {
Log.e("Comny Name", "" + p.getName());
Log.e("      ", "" + "*****************");
}

Connections connections = client.getConnectionsForCurrentUser();
for (Person p : connections.getPersonList()) {
Log.e("Name", "" + p.getLastName() + " " + p.getFirstName());
Log.e("Industry      ", "" + p.getIndustry());
Log.e("      ", "" + "*****************");
}

}

}
