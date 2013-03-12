package whereismymoney.activities;

import java.util.ArrayList;
import java.util.Arrays;

import whereismymoney.services.GetWishservice;
import whereismymoney.services.NewWishService;
import whereismymoney.services.WishInfoService;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class WishListMgrActivity extends Activity {
	private String userName;
	private String password;
	public int itemPosition;
	public ArrayList<String> wishes = new ArrayList<String>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wishlist_layout);
		this.userName = this.getIntent().getStringExtra("userName");
		this.password = this.getIntent().getStringExtra("password");
		// System.out.println("this is user "+this.userName +
		// " with password "+this.password);
		Button createAccount = (Button) findViewById(R.id.newWish);
		createAccount.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				inputNewAccountInfo();

			}

		});
		ListView listView = (ListView) findViewById(R.id.wisheslistview);
		listView.setTextFilterEnabled(true);
		GetWishservice accountTask = new GetWishservice(this.userName,
				this.password);
		accountTask.execute((Void) null);
		try {
			Thread.sleep(3500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (GetWishservice.result != null){
			String[] tempArray = new String[10];
			String wishestemp = GetWishservice.result.replace('"', ' ');
			System.out.println(wishestemp);
			if(wishestemp.contains("  ")){
				tempArray= wishestemp.split("  ");
				wishes.addAll(Arrays.asList(tempArray));
				wishes.set(0, wishes.get(0).replace(" ", ""));
			}else{
				wishes.add(wishestemp.replace(" ", ""));
			}
			
			if(wishes.get(0).contains("\n")){
				wishes.set(0, wishes.get(0).replace("\n", ""));
			}else{
				wishes.set(wishes.size()-1, wishes.get(wishes.size()-1).replace(" \n", ""));
			}
		}
		Log.i("wishes", wishes+" ");

		// Create ArrayAdapter using the planet list.
		ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(this,
				R.layout.list_row, wishes);

		listView.setAdapter(listAdapter);
		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				itemPosition = position;
				// When clicked, show a toast with the TextView text
				final Dialog dialog = new Dialog(WishListMgrActivity.this);
				dialog.setContentView(R.layout.wish_info);
				dialog.setTitle("Wish Info");
				// String fundAmount = getFund();
				// set the custom dialog components - text, image and button
				TextView itemName = (TextView) dialog.findViewById(R.id.wishItemName);
				TextView cost = (TextView) dialog.findViewById(R.id.itemCost);
				TextView funds = (TextView) dialog.findViewById(R.id.funds);
				TextView afterfunds = (TextView) dialog.findViewById(R.id.fundsafterpurchase);
				
				String itemName2 = wishes.get(itemPosition);
				System.out.println("hahahaha"+itemName2);
				
				WishInfoService addfund = new WishInfoService(itemName2, userName,password);
				addfund.execute((Void)null);
		        try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        WishInfoService.result = WishInfoService.result.replace("[", "");
		        WishInfoService.result = WishInfoService.result.replace("]", "");
		        WishInfoService.result = WishInfoService.result.replace(",", "");
		        Log.i("new",WishInfoService.result);
		        String accountstemp = WishInfoService.result.replace('"', ' ');
				String[] tempList = accountstemp.split("  ");
				tempList[0] = tempList[0].replace(" ", "");
				tempList[tempList.length - 1] = tempList[tempList.length - 1]
						.replace(" \n", "");
				Log.i("list",tempList+"");
				itemName.setText(itemName2);
				cost.setText(tempList[0]);
				funds.setText(tempList[1]);
				afterfunds.setText(tempList[2]);
				
				
				Button dialogButton1 = (Button) dialog
						.findViewById(R.id.dismiss);
				// if button is clicked, close the custom dialog
				dialogButton1.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {

						dialog.dismiss();
					}
				});
				dialog.show();
			}
		});
	}
	private void inputNewAccountInfo() {
		// When clicked, show a toast with the TextView text
		final Dialog dialog = new Dialog(WishListMgrActivity.this);
		dialog.setContentView(R.layout.new_wish);
		dialog.setTitle("New Wish");
		// String fundAmount = getFund();
		// set the custom dialog components - text, image and button

		Button dialogButton1 = (Button) dialog
				.findViewById(R.id.submitWish);
		// if button is clicked, close the custom dialog
		final EditText editText1 = (EditText) dialog
				.findViewById(R.id.addItemName);
		final EditText editText2 = (EditText) dialog
				.findViewById(R.id.addCost);
		dialogButton1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String itemName = null;
				itemName = editText1.getText().toString();
				Log.v("input name ", itemName);
				String cost = null;
				cost = editText2.getText().toString();
				Log.v("input fund ", cost);
				NewWishService accountTask = new NewWishService(
						itemName, cost, userName, password);
				accountTask.execute((Void) null);
				dialog.dismiss();
				Intent selfIntent = new Intent(WishListMgrActivity.this,
						WishListMgrActivity.class);
				selfIntent.putExtra("userName", userName);
				selfIntent.putExtra("password", password);
				selfIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(selfIntent);
			}
		});

		dialog.show();
	}

}
