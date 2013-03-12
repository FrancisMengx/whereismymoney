package whereismymoney.activities;

import java.util.ArrayList;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

public class PieChartFragment extends Activity{

	
	private GraphicalView mChartView;

    private String userName;
	private String password;
	private String accountName;
	private String result;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		this.userName = this.getIntent().getStringExtra("userName");
		this.password = this.getIntent().getStringExtra("password");
		this.accountName = this.getIntent().getStringExtra("accountName");
        setContentView(R.layout.pie_chart_layout);
        // Getting reference to the button btn_chart
        openChart();
	}
 
    @SuppressLint("NewApi")
	private void openChart(){
 
        // Pie Chart Section Names
        
        
        NewPieChartService pieTask = new NewPieChartService();
        pieTask.execute();
        try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        result = result.replace("[", "");
        result = result.replace("]", "");
        result = result.replace(",", "");
        Log.i("new",result);
        String accountstemp = result.replace('"', ' ');
		String[] tempList = accountstemp.split("  ");
		tempList[0] = tempList[0].replace(" ", "");
		tempList[tempList.length - 1] = tempList[tempList.length - 1]
				.replace(" \n", "");
		Log.i("new", tempList+"");
		String[] code = new String[tempList.length/2];
		double[] totals = new double[tempList.length/2];
		double total = 0;
		for(int i = 1; i< tempList.length; i+=2){
			code[i/2] = tempList[i-1];
			totals[i/2] = Double.parseDouble((tempList[i]));
			total += totals[i/2];
		}
		
        // Pie Chart Section Value
        double[] distribution = new double[code.length];
        for(int i = 0; i < distribution.length; i++){
        	distribution[i] = totals[i]/total;
        }
        
 
        // Color of each Pie Chart Sections
        int[] colors = { Color.BLUE, Color.MAGENTA, Color.GREEN, Color.CYAN, Color.RED,
                        Color.YELLOW, Color.DKGRAY,Color.LTGRAY };
 
        // Instantiating CategorySeries to plot Pie Chart
        CategorySeries distributionSeries = new CategorySeries("Category Distribution");
        for(int i=0 ;i < distribution.length;i++){
            // Adding a slice with its values and name to the Pie Chart
            distributionSeries.add(code[i], distribution[i]);
        }
        
        // Instantiating a renderer for the Pie Chart
        DefaultRenderer defaultRenderer  = new DefaultRenderer();
        for(int i = 0 ;i<distribution.length;i++){
            SimpleSeriesRenderer seriesRenderer = new SimpleSeriesRenderer();
            seriesRenderer.setColor(colors[i]);
            seriesRenderer.setDisplayChartValues(true);
            // Adding a renderer for a slice
            defaultRenderer.addSeriesRenderer(seriesRenderer);
        }
 
        defaultRenderer.setChartTitle("Spending Distribution");
        defaultRenderer.setChartTitleTextSize(30);
        defaultRenderer.setLabelsTextSize(30);
        defaultRenderer.setLegendTextSize(30);
        defaultRenderer.setZoomButtonsVisible(true);
        LinearLayout layout = (LinearLayout) findViewById(R.id.chart);
        mChartView = ChartFactory.getPieChartView(this, distributionSeries, defaultRenderer);
        layout.addView(mChartView, new LayoutParams
        		(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        // Creating an intent to plot bar chart using dataset and multipleRenderer
//        Intent intent = ChartFactory.getPieChartIntent(getBaseContext(), distributionSeries , defaultRenderer, "AChartEnginePieChartDemo");
//        // Start Activity
//        
//        startActivity(intent);
 
    }
 
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
       return true;
    }
    
    public class NewPieChartService extends AsyncTask<String, String, String>{
    	private String url = "http://66.175.218.17/pieChartRequest.php";
    	
    	
    	@Override
    	protected String doInBackground(String... params) {
    		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
    		
            nameValuePairs.add(new BasicNameValuePair("userName",userName));
            nameValuePairs.add(new BasicNameValuePair("password",password));
            
            try
            {	
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(this.url);
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                
                HttpResponse response = httpclient.execute(httppost);
                result = EntityUtils.toString(response.getEntity());
                
                Log.i("http response", result+" ");
                
            }
            catch(Exception e)
            {
                Log.e("log_tag", "Error:  "+e.toString());
            }
    		return null;
    		// TODO Auto-generated method stub
    	}
    }

}
