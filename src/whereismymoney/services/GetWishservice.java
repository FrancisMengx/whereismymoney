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

public class GetWishservice extends AsyncTask<Void, Void, Boolean> {
	private String userName;
	private String password;
	public static String result;
	public GetWishservice(String userName, String password) {
		this.userName = userName;
		this.password = password;
	}

	@Override
	protected Boolean doInBackground(Void... params) {
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(5);
        nameValuePairs.add(new BasicNameValuePair("userName",this.userName));
        nameValuePairs.add(new BasicNameValuePair("password",this.password));
        System.out.println("this is user "+this.userName + " with password "+this.password);
        try
        {	
        	
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://66.175.218.17/getWishList.php");
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity sdfa = response.getEntity();
            result = EntityUtils.toString(response.getEntity());
            
            Log.v("response","new account: " + result+" ");
        }
        catch(Exception e)
        {
            Log.e("log_tag", "Error:  "+e.toString());
        }
        
		return false;
	}

}
