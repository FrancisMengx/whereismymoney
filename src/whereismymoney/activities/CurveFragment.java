package whereismymoney.activities;

import java.util.ArrayList;
import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import whereismymoney.activities.PieChartFragment.NewPieChartService;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActionBar.LayoutParams;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

public class CurveFragment extends Activity {

    private String userName;
	private String password;
	private String accountName;
	private String result;
	
	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.userName = this.getIntent().getStringExtra("userName");
		this.password = this.getIntent().getStringExtra("password");
		this.accountName = this.getIntent().getStringExtra("accountName");
		
		setContentView(R.layout.curve_layout);
		
		
		NewLineChartService pieTask = new NewLineChartService();
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
		double[] code = new double[tempList.length/2];
		double[] totals = new double[tempList.length/2];
		for(int i = 1; i< tempList.length; i+=2){
			code[i/2] = Double.parseDouble((tempList[i-1]));;
			totals[i/2] = Double.parseDouble((tempList[i]));
		}
		
        // Pie Chart Section Value
		
		
		String[] titles = new String[] {"Daily Spending"};
		List<double[]> x = new ArrayList<double[]>();
		
		x.add(code);
		
		List<double[]> values = new ArrayList<double[]>();
		double max = 0;
		double min = 1000000;
		values.add(totals);
		for(int i = 0; i < values.get(0).length; i ++){
			if(values.get(0)[i]>max){
				max = values.get(0)[i];
			}
			if(values.get(0)[i]<min){
				min = values.get(0)[i];
			}
		}
	       Log.i("minmax",max+" "+min);
		int[] colors = new int[] { Color.GREEN };
		PointStyle[] styles = new PointStyle[] { PointStyle.CIRCLE,
				PointStyle.DIAMOND, PointStyle.TRIANGLE, PointStyle.SQUARE };
		XYMultipleSeriesRenderer renderer = buildRenderer(colors, styles);
		int length = renderer.getSeriesRendererCount();
		for (int i = 0; i < length; i++) {
			((XYSeriesRenderer) renderer.getSeriesRendererAt(i))
					.setFillPoints(true);
		}
		
		setChartSettings(renderer, "Spending($)", "Day",
				"Spending", 0.5, 15, min-min/10, max+max/10, Color.LTGRAY, Color.LTGRAY);
		renderer.setXLabels(10);
		renderer.setYLabels(20);
		renderer.setShowGrid(true);
		renderer.setXLabelsAlign(Align.RIGHT);
		renderer.setYLabelsAlign(Align.RIGHT);

		renderer.setZoomButtonsVisible(true);
		renderer.setZoomLimits(new double[] { -10, 20, -10, 40 });
		
		LinearLayout layout = (LinearLayout) findViewById(R.id.linechart);
		
		View view = ChartFactory.getLineChartView(this,
				buildDataset(titles, x, values), renderer);
		view.setBackgroundColor(Color.BLACK);
		layout.addView(view, new LayoutParams
        		(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
	}

	private XYMultipleSeriesRenderer buildRenderer(int[] colors,
			PointStyle[] styles) {
		XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
		setRenderer(renderer, colors, styles);
		return renderer;
	}

	private void setRenderer(XYMultipleSeriesRenderer renderer, int[] colors,
			PointStyle[] styles) {
		renderer.setAxisTitleTextSize(16);
		renderer.setChartTitleTextSize(20);
		renderer.setLabelsTextSize(15);
		renderer.setLegendTextSize(15);
		renderer.setPointSize(5f);
		renderer.setMargins(new int[] { 20, 30, 15, 20 });
		int length = colors.length;
		for (int i = 0; i < length; i++) {
			XYSeriesRenderer r = new XYSeriesRenderer();
			r.setColor(colors[i]);
			r.setPointStyle(styles[i]);
			renderer.addSeriesRenderer(r);
		}
	}

	private void setChartSettings(XYMultipleSeriesRenderer renderer,
			String title, String xTitle, String yTitle, double xMin,
			double xMax, double yMin, double yMax, int axesColor,
			int labelsColor) {
		renderer.setChartTitle(title);
		renderer.setXTitle(xTitle);
		renderer.setYTitle(yTitle);
		renderer.setXAxisMin(xMin);
		renderer.setXAxisMax(xMax);
		renderer.setYAxisMin(yMin);
		renderer.setYAxisMax(yMax);
		renderer.setAxesColor(axesColor);
		renderer.setLabelsColor(labelsColor);
	}

	private XYMultipleSeriesDataset buildDataset(String[] titles,
			List<double[]> xValues, List<double[]> yValues) {
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		addXYSeries(dataset, titles, xValues, yValues, 0);
		return dataset;
	}

	private void addXYSeries(XYMultipleSeriesDataset dataset, String[] titles,
			List<double[]> xValues, List<double[]> yValues, int scale) {
		int length = titles.length;
		for (int i = 0; i < length; i++) {
			XYSeries series = new XYSeries(titles[i], scale);
			double[] xV = xValues.get(i);
			double[] yV = yValues.get(i);
			int seriesLength = xV.length;
			for (int k = 0; k < seriesLength; k++) {
				series.add(xV[k], yV[k]);
			}
			dataset.addSeries(series);
		}
	}
	   public class NewLineChartService extends AsyncTask<String, String, String>{
	    	private String url = "http://66.175.218.17/lineChartRequest.php";
	    	
	    	
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
