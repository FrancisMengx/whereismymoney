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


public class NewPieChartService extends AsyncTask<String, String, String>{
	private String url = "http://66.175.218.17/newCat.php";
	
	private String userName;
	private String password;
	private String catName;
	private String balance;
	
	public NewPieChartService(String catName, String balance, String userName, String password){
		this.userName = userName;
		this.balance = balance;
		this.catName = catName;
		this.password = password;
	}
	
	@Override
	protected String doInBackground(String... params) {
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		
		nameValuePairs.add(new BasicNameValuePair("catName",this.catName));
		nameValuePairs.add(new BasicNameValuePair("balance",this.balance));
        nameValuePairs.add(new BasicNameValuePair("userName",this.userName));
        nameValuePairs.add(new BasicNameValuePair("password",this.password));
        
        try
        {	
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(this.url);
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            
            HttpResponse response = httpclient.execute(httppost);
            String result = EntityUtils.toString(response.getEntity());
            
            Log.v("http response", result+" ");
            
        }
        catch(Exception e)
        {
            Log.e("log_tag", "Error:  "+e.toString());
        }
		return null;
		// TODO Auto-generated method stub
	}
}
