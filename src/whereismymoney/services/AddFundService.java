package whereismymoney.services;

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

public class AddFundService extends AsyncTask<Void, Void, Boolean> {
	private String accountName;
	private String fund;
	private String userName;
	private String password;
	public AddFundService(String accountName, String fund,String userName, String password) {
		this.accountName = accountName;
		this.fund = fund;
		this.userName = userName;
		this.password = password;
	}

	@Override
	protected Boolean doInBackground(Void... params) {
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(5);
		nameValuePairs.add(new BasicNameValuePair("accountName", this.accountName));
        nameValuePairs.add(new BasicNameValuePair("fund",this.fund));
        nameValuePairs.add(new BasicNameValuePair("userName",this.userName));
        nameValuePairs.add(new BasicNameValuePair("password",this.password));
        try
        {	
        	
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://66.175.218.17/addFund.php");
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity sdfa = response.getEntity();
            String result = EntityUtils.toString(response.getEntity());
            
            Log.v("response","new account: " + result+" ");
        }
        catch(Exception e)
        {
            Log.e("log_tag", "Error:  "+e.toString());
        }
        
		return false;
	}

}
