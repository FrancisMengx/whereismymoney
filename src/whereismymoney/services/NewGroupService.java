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

public class NewGroupService extends AsyncTask<Void, Void, Boolean> {
	private String userName;
	private String password;
	private String groupName;
	private String accountName;
	public static String result;

	


	public NewGroupService(String groupName, String accountName, String userName,
			String password) {
		this.userName = userName;
		this.password = password;
		this.groupName = groupName;
		this.accountName = accountName;
	}

	@Override
	protected Boolean doInBackground(Void... params) {
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(5);
        nameValuePairs.add(new BasicNameValuePair("userName",this.userName));
        nameValuePairs.add(new BasicNameValuePair("password",this.password));
        nameValuePairs.add(new BasicNameValuePair("groupName",this.groupName));
        nameValuePairs.add(new BasicNameValuePair("accountName",this.accountName));
        System.out.println("this is user "+this.userName + " with password "+this.password);
        try
        {	
        	
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://66.175.218.17/newGroup.php");
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

