package whereismymoney.activities;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import whereismymoney.services.NewCatService;
import whereismymoney.services.NewDataService;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.Spinner;

public class NewDataFragment extends Activity {

	public View inflate;
	private DatePicker dpResult;
	private Gallery gallery;
	private int selectedItem;
	private Spinner spinner;
	private String userName;
	private String password;
	private String accountName;
	private String catName;
	private String[] categorys;
	private String result;
	EditText editTextItem;
	EditText editTextPrice;
	String itemName;
	String price;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.userName = this.getIntent().getStringExtra("userName");
		this.password = this.getIntent().getStringExtra("password");
		this.accountName = this.getIntent().getStringExtra("accountName");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_data_layout);
		chooseCategory();
		Button createCat = (Button) findViewById(R.id.createCat);
		createCat.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				final Dialog dialog = new Dialog(NewDataFragment.this);
				dialog.setContentView(R.layout.new_category_layout);
				dialog.setTitle("Create Category");
				// String fundAmount = getFund();
				// set the custom dialog components - text, image and button
				final EditText editText1 = (EditText) dialog
						.findViewById(R.id.addCatName);
				final EditText editText2 = (EditText) dialog
						.findViewById(R.id.addBalance);
				Button dialogButton = (Button) dialog
						.findViewById(R.id.submitNewCat);
				// if button is clicked, close the custom dialog
				dialogButton.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						catName = editText1.getText().toString();
						String catBalance = editText2.getText().toString();
						NewCatService newCat = new NewCatService(catName,
								catBalance, userName, password);
						newCat.execute();
						Intent newDataIntent = new Intent(NewDataFragment.this,
								TabsFragmentActivity.class);
						newDataIntent.putExtra("userName", userName);
						newDataIntent.putExtra("password", password);
						newDataIntent.putExtra("accountName", accountName);
						startActivity(newDataIntent);
						newDataIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						newDataIntent.putExtra("source", 1);
					}
				});

				dialog.show();

			}
		});

			
		Button submit = (Button) findViewById(R.id.submit);
		submit.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				boolean cancel = false;
				View focusView = null;
				
				itemName = getItemName();
				price = getPrice();
				if(TextUtils.isEmpty(itemName)){
					editTextItem.setError("Please Enter The Item Name");
					cancel = true;
				}
				if(TextUtils.isEmpty(price)){
					editTextPrice.setError("Please Enter The Price");
					cancel = true;
				}
				if(!cancel){
				String date = pickDate();
				String category = spinner.getSelectedItem().toString();
				NewDataService dataTask =  new NewDataService(itemName, price, category,  date, userName, password, accountName);
				dataTask.execute();
				TabsFragmentActivity.tabHost.setCurrentTab(0);
			}
			}
		});
		}
	

	private String pickDate() {
		dpResult = (DatePicker) findViewById(R.id.dpResult);
		int day = this.dpResult.getDayOfMonth();
		int month = this.dpResult.getMonth() + 1;
		int year = this.dpResult.getYear();
		String date = year + "-"+month + "-" + day;
		return date;
	}

	private void chooseCategory() {
		// add query to get all the categories so far
		this.spinner = (Spinner) findViewById(R.id.spinner1);
		CatListTask catTask = new CatListTask();
		catTask.execute();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (result == null)
			this.categorys = new String[0];
		else {
			String cattemp = result.replace('"', ' ');
			System.out.println(cattemp);
			this.categorys = cattemp.split("  ");
			this.categorys[0] = this.categorys[0].replace(" ", "");
			categorys[categorys.length-1] = categorys[categorys.length-1].replace("\n", "");
		}
		
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, categorys);
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(dataAdapter);
	}

	private String getItemName() {
		editTextItem = (EditText) findViewById(R.id.edit_message);
		System.out.println("haha");
		String message = editTextItem.getText().toString();
		
		return message;
	}

	private String getPrice() {
		editTextPrice = (EditText) findViewById(R.id.price);
		System.out.println("haha");
		String price = editTextPrice.getText().toString();
		return price;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_data_layout, menu);
		return true;
	}
	
	public class CatListTask extends AsyncTask<Void, Void, Boolean> {
		@Override
		
		protected Boolean doInBackground(Void... params) {
			// TODO: attempt authentication against a network service.

			try {
				// Simulate network access.
				ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(5);
				nameValuePairs.add(new BasicNameValuePair("userName", userName));
		        nameValuePairs.add(new BasicNameValuePair("password",password));
		        HttpClient httpclient = new DefaultHttpClient();
	            HttpPost httppost = new HttpPost("http://66.175.218.17/getCat.php");
	            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	            
	            HttpResponse response = httpclient.execute(httppost);
	            HttpEntity sdfa = response.getEntity();
	            result = EntityUtils.toString(response.getEntity());
	            Log.v("response",result);
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// TODO: register the new account here.
			
			return false;
		}
	}

}
