package whereismymoney.services;

import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.os.AsyncTask;
import android.util.Log;

public class NewDataService extends AsyncTask<String, String, String> {
	private String url = "http://66.175.218.17/newdata.php";

	private String itemName;
	private String price;
	private String category;
	private String date;
	private String userName;
	private String password;
	private String accountName;

	public NewDataService(String itemName, String price, String category,
			String date, String userName, String password, String accountName) {
		this.userName = userName;
		this.password = password;
		this.accountName = accountName;
		this.itemName = itemName;
		this.price = price;
		this.category = category;
		this.date = date;
	}

	@Override
	protected String doInBackground(String... params) {
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		Log.v("debug", this.userName + " " + this.password + " "
				+ this.accountName + " " + this.itemName + " " + this.price
				+ " " + this.category + " " + this.date);
		nameValuePairs.add(new BasicNameValuePair("itemName", itemName));
		nameValuePairs.add(new BasicNameValuePair("price", price));
		nameValuePairs.add(new BasicNameValuePair("category", category));
		nameValuePairs.add(new BasicNameValuePair("date", date));
		nameValuePairs.add(new BasicNameValuePair("accountName",
				this.accountName));
		nameValuePairs.add(new BasicNameValuePair("userName", this.userName));
		nameValuePairs.add(new BasicNameValuePair("password", this.password));

		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(this.url);
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			HttpResponse response = httpclient.execute(httppost);
			String result = EntityUtils.toString(response.getEntity());

			Log.v("http response", result + " ");

		} catch (Exception e) {
			Log.e("log_tag", "Error:  " + e.toString());
		}
		return null;
		// TODO Auto-generated method stub
	}
}
