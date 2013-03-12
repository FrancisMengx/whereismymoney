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
import android.widget.EditText;

public class NewAdjusterService extends AsyncTask<Void, Void, Boolean> {
	private String adjusterName;
	private String noRepeats;
	private String account;
	private String date;
	private String category;
	private String amount;
	private String period;
	private String userName;
	private String password;

	public NewAdjusterService(String name, String noRepeats, String account,
			String date, String category, String amount, String period,
			String userName, String password) {
		this.adjusterName = name;
		this.noRepeats = noRepeats;
		this.account = account;
		this.date = date;
		this.category = category;
		this.amount = amount;
		this.period = period;
		this.userName = userName;
		this.password = password;
		
	}

	@Override
	protected Boolean doInBackground(Void... params) {
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(5);
        nameValuePairs.add(new BasicNameValuePair("userName",this.userName));
        nameValuePairs.add(new BasicNameValuePair("password",this.password));
        nameValuePairs.add(new BasicNameValuePair("noRepeats",this.noRepeats));
        nameValuePairs.add(new BasicNameValuePair("account",this.account));
        nameValuePairs.add(new BasicNameValuePair("date",this.date));
        nameValuePairs.add(new BasicNameValuePair("category",this.category));
        nameValuePairs.add(new BasicNameValuePair("amount",this.amount));
        nameValuePairs.add(new BasicNameValuePair("period",this.period));
        try
        {	
        	
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://66.175.218.17/newAdjuster.php");
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
