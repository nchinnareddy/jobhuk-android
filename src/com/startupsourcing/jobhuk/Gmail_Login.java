package com.startupsourcing.jobhuk;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.plus.PlusClient;

public class Gmail_Login extends Activity implements ConnectionCallbacks, OnConnectionFailedListener,OnClickListener{

	private static final String TAG = "ExampleActivity";
    private static final int REQUEST_CODE_RESOLVE_ERR = 9000;
    
    private ProgressDialog mConnectionProgressDialog;
    private PlusClient mPlusClient;
    private ConnectionResult mConnectionResult;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gmail_login);
		
		Button signin = (Button) findViewById(R.id.signin);
				
		mPlusClient = new PlusClient.Builder(this, this, this)
        .setActions("http://schemas.google.com/AddActivity", "http://schemas.google.com/BuyActivity")
        .build();
		// Progress bar to be displayed if the connection failure is not resolved.
		mConnectionProgressDialog = new ProgressDialog(this);
		mConnectionProgressDialog.setMessage("Signing in...");
		
		signin.setOnClickListener(this);
	}

    @Override
    protected void onStart() {
        super.onStart();
        mPlusClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPlusClient.disconnect();
    }

    @Override
    protected void onActivityResult(int requestCode, int responseCode, Intent intent) {
        if (requestCode == REQUEST_CODE_RESOLVE_ERR && responseCode == RESULT_OK) {
            mConnectionResult = null;
            mPlusClient.connect();
        }
    }

    @Override
    public void onDisconnected() {
        Log.d(TAG, "disconnected");
    }
    
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.submit && !mPlusClient.isConnected()) {
            if (mConnectionResult == null) {
                mConnectionProgressDialog.show();
            } else {
                try {
                    mConnectionResult.startResolutionForResult(this, REQUEST_CODE_RESOLVE_ERR);
                } catch (SendIntentException e) {
                    // Try connecting again.
                    mConnectionResult = null;
                    mPlusClient.connect();
                }
            }
        }
    }
	@Override
    public void onConnectionFailed(ConnectionResult result) {
      if (mConnectionProgressDialog.isShowing()) {
        // The user clicked the sign-in button already. Start to resolve
        // connection errors. Wait until onConnected() to dismiss the
        // connection dialog.
        if (result.hasResolution()) {
          try {
                   result.startResolutionForResult(this, REQUEST_CODE_RESOLVE_ERR);
           } catch (SendIntentException e) {
                   mPlusClient.connect();
           }
        }
      }
      // Save the result and resolve the connection failure upon a user click.
      mConnectionResult = result;
    }

	@Override
	public void onConnected(Bundle connectionHint) {
		// TODO Auto-generated method stub
		String accountName = mPlusClient.getAccountName();
	    mConnectionProgressDialog.dismiss();
	    Toast.makeText(this, "User is connected!", Toast.LENGTH_LONG).show();
        Toast.makeText(this, accountName + " is connected.", Toast.LENGTH_LONG).show();
	}

}
