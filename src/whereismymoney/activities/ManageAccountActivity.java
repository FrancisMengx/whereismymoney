package whereismymoney.activities;

import java.util.ArrayList;
import java.util.Arrays;

import whereismymoney.services.AddFundService;
import whereismymoney.services.GetAccountService;
import whereismymoney.services.NewAccountService;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class ManageAccountActivity extends Activity {

	private View mLoginFormView;
	private View mLoginStatusView;
	private String userName;
	private String password;
	public ArrayList<String> accounts = new ArrayList<String>();
	public int itemPosition;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.userName = this.getIntent().getStringExtra("userName");
		this.password = this.getIntent().getStringExtra("password");
		// System.out.println("this is user "+this.userName +
		// " with password "+this.password);

		setContentView(R.layout.activity_manage_account);
		Button createAccount = (Button) findViewById(R.id.newAccount);
		createAccount.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				inputNewAccountInfo();

			}

		});
		ListView listView = (ListView) findViewById(R.id.accounts);
		listView.setTextFilterEnabled(true);
		GetAccountService accountTask = new GetAccountService(this.userName,
				this.password);
		accountTask.execute((Void) null);
		try {
			Thread.sleep(3500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (GetAccountService.result != null){
			String[] tempArray = new String[10];
			String accountstemp = GetAccountService.result.replace('"', ' ');
			System.out.println(accountstemp);
			if(accountstemp.contains("  ")){
				tempArray= accountstemp.split("  ");
				accounts.addAll(Arrays.asList(tempArray));
				accounts.set(0, accounts.get(0).replace(" ", ""));
			}else{
				accounts.add(accountstemp.replace(" ", ""));
			}
			
			if(accounts.get(0).contains("\n")){
				accounts.set(0, accounts.get(0).replace("\n", ""));
			}else{
				accounts.set(accounts.size()-1, accounts.get(accounts.size()-1).replace(" \n", ""));
			}
		}
		Log.i("Accounts", accounts+" ");

		// Create ArrayAdapter using the planet list.
		ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(this,
				R.layout.list_row, accounts);

		listView.setAdapter(listAdapter);
		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				itemPosition = position;
				// When clicked, show a toast with the TextView text
				final Dialog dialog = new Dialog(ManageAccountActivity.this);
				dialog.setContentView(R.layout.account_choice);
				dialog.setTitle("Manage Account");
				// String fundAmount = getFund();
				// set the custom dialog components - text, image and button
				final EditText editText = (EditText) dialog.findViewById(R.id.addFund);
				Button dialogButton1 = (Button) dialog
						.findViewById(R.id.dialogButtonAddFund);
				// if button is clicked, close the custom dialog
				dialogButton1.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						String fund = editText.getText().toString();
						String accountName = accounts.get(itemPosition);
						AddFundService addfund = new AddFundService(accountName, fund, userName,password);
						addfund.execute((Void)null);
						dialog.dismiss();
					}
				});

				Button dialogButton2 = (Button) dialog
						.findViewById(R.id.dialogButtonManageData);
				// if button is clicked, close the custom dialog
				dialogButton2.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						String accountName = accounts.get(itemPosition);
						Intent mainIntent = new Intent(
								ManageAccountActivity.this, MainActivity.class);
						mainIntent.putExtra("userName", userName);
						mainIntent.putExtra("password", password);
						mainIntent.putExtra("accountName", accountName);
						startActivity(mainIntent);
					}
				});

				dialog.show();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_manage_account, menu);
		return true;
	}

	private void inputNewAccountInfo() {
		// When clicked, show a toast with the TextView text
		final Dialog dialog = new Dialog(ManageAccountActivity.this);
		dialog.setContentView(R.layout.new_account);
		dialog.setTitle("Create Account");
		// String fundAmount = getFund();
		// set the custom dialog components - text, image and button

		Button dialogButton1 = (Button) dialog
				.findViewById(R.id.submitNewAccount);
		// if button is clicked, close the custom dialog
		final EditText editText1 = (EditText) dialog
				.findViewById(R.id.addAccountName);
		final EditText editText2 = (EditText) dialog
				.findViewById(R.id.addStartingAmount);
		dialogButton1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String accountName = null;
				accountName = editText1.getText().toString();
				Log.v("input name ", accountName);
				String startingFund = null;
				startingFund = editText2.getText().toString();
				Log.v("input fund ", startingFund);
				NewAccountService accountTask = new NewAccountService(
						accountName, startingFund, userName, password);
				accountTask.execute((Void) null);
				dialog.dismiss();
				Intent selfIntent = new Intent(ManageAccountActivity.this,
						ManageAccountActivity.class);
				selfIntent.putExtra("userName", userName);
				selfIntent.putExtra("password", password);
				selfIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(selfIntent);
			}
		});

		dialog.show();
	}

	private String getFund() {
		EditText editText = (EditText) findViewById(R.id.addFund);
		String fundAmount = editText.getText().toString();
		return fundAmount;
	}
	
}
