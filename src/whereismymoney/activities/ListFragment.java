package whereismymoney.activities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

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

import whereismymoney.services.GetAccountService;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class ListFragment extends Activity {

	private String userName;
	private String password;
	private String accountName;
	private ArrayList<String> tranList = new ArrayList<String>();
	private static String result;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_layout);
		this.userName = this.getIntent().getStringExtra("userName");
		this.password = this.getIntent().getStringExtra("password");
		this.accountName = this.getIntent().getStringExtra("accountName");
		// System.out.println("this is user "+this.userName +
		// " with password "+this.password);
		ListTask accountTask = new ListTask();
		accountTask.execute((Void) null);
		ArrayList<String> tempList = new ArrayList<String>();
		ArrayList<String> temp = new ArrayList<String>();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (!TextUtils.isEmpty(result)){
			String accountstemp = result.replace('"', ' ');
			System.out.println("work");
			System.out.println(accountstemp+"");
			if(accountstemp.contains("  ")){
				String[] tempArray = new String[10];
				tempArray = accountstemp.split("  ");
				tempList.addAll(Arrays.asList(tempArray));
				tempList.set(0, tempList.get(0).replace(" ", ""));
			}
			else tempList.add( accountstemp.replace(" ", ""));
			if(tempList.get(tempList.size() - 1).contains(" \n")){
				tempList.set(tempList.size()-1, tempList.get(tempList.size() - 1)
						.replace(" \n", ""));
			}
			else {
				tempList.set(tempList.size()-1, tempList.get(tempList.size() - 1).replace("\n", ""));
			}
					

		}
		if (tempList.size() > 10) {
			for (int i = 0; i < 10; i++) {
				temp.add(tempList.get(tempList.size()- 1 - i));
			}
		} else {
			for (int i = 0; i < tempList.size(); i++) {
				temp.add(tempList.get(tempList.size()- 1 - i));
			}
		}
		tranList = temp;
		Button createAccount = (Button) findViewById(R.id.listRefresh);
		createAccount.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				Intent preDataIntent = new Intent(ListFragment.this,
						TabsFragmentActivity.class);
				Log.d("preButton", "preButton is clicked");
				preDataIntent.putExtra("userName", userName);
				preDataIntent.putExtra("password", password);
				preDataIntent.putExtra("accountName", accountName);
				preDataIntent.putExtra("source", 0);
				preDataIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(preDataIntent);
			}

		});
		ListView listView = (ListView) findViewById(R.id.transactionList);
		// Create ArrayAdapter using the planet list.
		ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(this,
				R.layout.list_row, tranList);

		listView.setAdapter(listAdapter);
		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// request for transaction info
			}
		});
	}

	public class ListTask extends AsyncTask<Void, Void, Boolean> {
		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO: attempt authentication against a network service.

			try {
				// Simulate network access.
				ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(
						5);
				nameValuePairs
						.add(new BasicNameValuePair("userName", userName));
				nameValuePairs
						.add(new BasicNameValuePair("password", password));
				nameValuePairs.add(new BasicNameValuePair("accountName",
						accountName));
				HttpClient httpclient = new DefaultHttpClient();
				HttpPost httppost = new HttpPost(
						"http://66.175.218.17/listRequest.php");
				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

				HttpResponse response = httpclient.execute(httppost);
				HttpEntity sdfa = response.getEntity();
				result = EntityUtils.toString(response.getEntity());
				Log.v("response", result);
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
