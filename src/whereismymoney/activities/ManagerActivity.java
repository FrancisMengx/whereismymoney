package whereismymoney.activities;

import whereismymoney.services.NewAdjusterService;
import whereismymoney.services.NewReminderService;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

public class ManagerActivity extends Activity {
	private String userName;
	private String password;
	private String accountName;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.userName = this.getIntent().getStringExtra("userName");
        this.password = this.getIntent().getStringExtra("password");
        this.accountName = this.getIntent().getStringExtra("accountName");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.manager_layout);
		
		Button account_mgr = (Button)findViewById(R.id.account_mgr);
		Button wishlist_mgr = (Button)findViewById(R.id.wishlist_mgr);
		Button reminder_mgr = (Button)findViewById(R.id.reminder_mgr);
		Button group_mgr = (Button)findViewById(R.id.group_mgr);
		Button create_adjuster = (Button) findViewById(R.id.createAdjuster);
		
		account_mgr.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent accIntent = new Intent(ManagerActivity.this, ManageAccountActivity.class);
				accIntent.putExtra("userName", userName);
				accIntent.putExtra("password", password);
				startActivity(accIntent);
			}
			
		});
		wishlist_mgr.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				
				Intent wishIntent = new Intent(ManagerActivity.this, WishListMgrActivity.class);
				wishIntent.putExtra("userName", userName);
				wishIntent.putExtra("password", password);
				startActivity(wishIntent);
				
			}
			
		});
		
		reminder_mgr.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent remIntent = new Intent(ManagerActivity.this, ReminderMgrActivity.class);
				remIntent.putExtra("userName", userName);
				remIntent.putExtra("password", password);
				startActivity(remIntent);
				
			}
			
		});
		
		group_mgr.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent groupIntent = new Intent(ManagerActivity.this, GroupMgrActivity.class);
				groupIntent.putExtra("userName", userName);
				groupIntent.putExtra("password", password);
				startActivity(groupIntent);
				
			}
			
		});
		create_adjuster.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// When clicked, show a toast with the TextView text
				final Dialog dialog = new Dialog(ManagerActivity.this);
				dialog.setContentView(R.layout.new_adjuster);
				dialog.setTitle("New Adjuster");
				// String fundAmount = getFund();
				
				DatePicker pickedDate = (DatePicker) dialog.findViewById(R.id.adjustDate);
				int day = pickedDate.getDayOfMonth();
				int month = pickedDate.getMonth() + 1;
				int year = pickedDate.getYear();
				String date = year + "-"+month + "-" + day;
				Button dialogButton1 = (Button) dialog
						.findViewById(R.id.submitAdjuster);
				final String Date = date;
				// if button is clicked, close the custom dialog
				final EditText reminderName = (EditText) dialog
						.findViewById(R.id.adjusterName);
				final EditText repeats = (EditText) dialog
						.findViewById(R.id.no_repeats);
				final EditText category = (EditText) dialog
						.findViewById(R.id.category);
				final EditText account = (EditText) dialog
						.findViewById(R.id.account);
				final EditText am = (EditText) dialog
						.findViewById(R.id.Amount);
				final EditText per = (EditText) dialog
						.findViewById(R.id.period);
				dialogButton1.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						String name = null;
						name = reminderName.getText().toString();
						Log.v("input name ", name);
						String noRepeats = null;
						noRepeats = repeats.getText().toString();
						String accountName = account.getText().toString();
						String amount = am.getText().toString();
						String period = per.getText().toString();
						Log.v("input fund ", noRepeats);
						String categoryName = category.getText().toString();
						NewAdjusterService accountTask = new NewAdjusterService(name,
								noRepeats, accountName, Date, categoryName, amount,period, userName, password);
						accountTask.execute((Void) null);
						dialog.dismiss();
						Intent selfIntent = new Intent(ManagerActivity.this,
								ReminderMgrActivity.class);
						selfIntent.putExtra("userName", userName);
						selfIntent.putExtra("password", password);
						selfIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(selfIntent);
					}
				});

				dialog.show();
				
			}
			
		});
	} 
		

}
