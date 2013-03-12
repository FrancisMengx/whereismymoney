package whereismymoney.activities;

import whereismymoney.activities.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
	private String userName;
	private String password;
	private String accountName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.userName = this.getIntent().getStringExtra("userName");
        this.password = this.getIntent().getStringExtra("password");
        this.accountName = this.getIntent().getStringExtra("accountName");
        setContentView(R.layout.activity_main);
        Button preDataButton = (Button)findViewById(R.id.previous_data);
        Button newDataButton = (Button)findViewById(R.id.new_data);
        preDataButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent preDataIntent = new Intent(MainActivity.this, TabsFragmentActivity.class);
				Log.d("preButton","preButton is clicked");
				preDataIntent.putExtra("userName", userName);
				preDataIntent.putExtra("password", password);
				preDataIntent.putExtra("accountName", accountName);
				preDataIntent.putExtra("source", 0);
				startActivity(preDataIntent);
				
			}
		});
        newDataButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent newDataIntent = new Intent(MainActivity.this, TabsFragmentActivity.class);
				Log.d("newButton","newButton is clicked");
				newDataIntent.putExtra("userName", userName);
				newDataIntent.putExtra("password", password);
				newDataIntent.putExtra("accountName", accountName);
				startActivity(newDataIntent);
				newDataIntent.putExtra("source", 1);
			}
		});
        
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
}
