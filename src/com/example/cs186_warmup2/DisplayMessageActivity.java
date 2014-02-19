package com.example.cs186_warmup2;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DisplayMessageActivity extends Activity {
    String ERR_BAD_CREDENTIALS = "-1";
    String ERR_BAD_USER_EXISTS = "-2";
    String ERR_BAD_USERNAME = "-3";
    String ERR_BAD_PASSWORD = "-4";
    String MAX_PASSWORD_LENGTH = "128";
    String MAX_USERNAME_LENGTH = "128";
    String SUCCESS = "1";
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_message);
		
		// Show the Up button in the action bar.
		setupActionBar();
		
	    // Get the message from the intent
		Intent intent = getIntent();
		String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE); //username
		String message2 = intent.getStringExtra(MainActivity.EXTRA_MESSAGE2); //password
		String count = intent.getStringExtra(MainActivity.EXTRA_MESSAGE3); //count
		String errCode = intent.getStringExtra(MainActivity.EXTRA_MESSAGE4); //count
		
	    // Create the text view
	    TextView textView = (TextView)findViewById( R.id.welcome_text );
	    textView.setTextSize(10);
    	Button btn = (Button)findViewById( R.id.logout_back_button);
	    
	    if(errCode.equals(SUCCESS)){
	    	textView.setText("Welcome, " + message + "\n" + "You have logged in " + count + "times.");
	    }
	    else if(errCode.equals(ERR_BAD_CREDENTIALS)){
	    	textView.setText("Invalid username password combination. \n Please try again.");
	    	btn.setText("Go back");
	    }
	    else if(errCode.equals(ERR_BAD_USER_EXISTS)){
	    	textView.setText("This user name already exists. \n Please try again.");
	    	btn.setText("Go back");
	    }
	    else if(errCode.equals(ERR_BAD_USERNAME)){
	    	textView.setText("The user name should be non-empty \n and at most 128 characters long. \n Please try again.");
	    	btn.setText("Go back");
	    }
	    else if(errCode.equals(ERR_BAD_PASSWORD)){
	    	textView.setText("The password should be \n at most 128 characters long. \n and non-empty \n Please try again.");
	    	btn.setText("Go back");
	    }
	    else{
	    	textView.setText("wat error code is this yo");
	    	btn.setText("Go back");

	    }
	    
	    
	    // Set the text view as the activity layout
	    //setContentView(textView);	    	   
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void logout(View view){
		//System.out.println("LOGGING OUT LOGGING OUT");
    	//Intent intent = new Intent(this, MainActivity.class);
    	//startActivity(intent);
		
		//**TODO: reset username/password
	    
		finish();

	}

}
