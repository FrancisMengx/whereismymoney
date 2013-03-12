package whereismymoney.activities;

import whereismymoney.activities.R;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

/**
 * 
 * @author 
 * 
 * 
 */
public class TabsFragmentActivity extends TabActivity {
	
	public static TabHost tabHost;
	private String userName;
	private String password;
	private String accountName;

	public void onCreate(Bundle savedInstanceState) {
        this.userName = this.getIntent().getStringExtra("userName");
        this.password = this.getIntent().getStringExtra("password");
        this.accountName = this.getIntent().getStringExtra("accountName");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tabs_layout);
 
		Resources ressources = getResources(); 
		this.tabHost = getTabHost(); 
 
		// list tab
		Intent intentList= new Intent().setClass(this,ListFragment.class);
		intentList.putExtra("userName", this.userName);
		intentList.putExtra("password", this.password);
		intentList.putExtra("accountName", this.accountName);
		TabSpec tabSpecAndroid = tabHost
		  .newTabSpec("List")
		  .setIndicator("List")
		  .setContent(intentList);
 
		// Pie graph tab
		Intent intentPie= new Intent().setClass(this, PieChartFragment.class);
		intentPie.putExtra("userName", this.userName);
		intentPie.putExtra("password", this.password);
		intentPie.putExtra("accountName", this.accountName);
		TabSpec tabSpecApple = tabHost
		  .newTabSpec("PieChart")
		  .setIndicator("PieChart")
		  .setContent(intentPie);
 
		// line graph tab
		Intent intentLine = new Intent().setClass(this, CurveFragment.class);
		intentLine.putExtra("userName", this.userName);
		intentLine.putExtra("password", this.password);
		intentLine.putExtra("accountName", this.accountName);
		TabSpec tabSpecWindows = tabHost
		  .newTabSpec("Line Graph")
		  .setIndicator("Line Graph")
		  .setContent(intentLine);
 
		// new data tab
		Intent intentNewData = new Intent().setClass(this, NewDataFragment.class);
		intentNewData.putExtra("userName", this.userName);
		intentNewData.putExtra("password", this.password);
		intentNewData.putExtra("accountName", this.accountName);
		TabSpec tabSpecBerry = tabHost
		  .newTabSpec("New Data")
		  .setIndicator("New Data")
		  .setContent(intentNewData);
 
		// add all tabs 
		tabHost.addTab(tabSpecAndroid);
		tabHost.addTab(tabSpecApple);
		tabHost.addTab(tabSpecWindows);
		tabHost.addTab(tabSpecBerry);
		
 
		//set Windows tab as default (zero based)
		Intent source = this.getIntent();
		if(source.getIntExtra("source", 1) == 1){
			tabHost.setCurrentTab(3);
		}else{
			tabHost.setCurrentTab(0);
		}
		
	}
 
}

