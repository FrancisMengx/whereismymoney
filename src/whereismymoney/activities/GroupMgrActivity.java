package whereismymoney.activities;

import java.util.ArrayList;
import java.util.Arrays;

import whereismymoney.services.GetGroupService;
import whereismymoney.services.GroupIDService;
import whereismymoney.services.NewGroupService;
import whereismymoney.services.WishInfoService;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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

public class GroupMgrActivity extends Activity {
	private String userName;
	private String password;
	public ArrayList<String> groups = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.group_layout);
		this.userName = this.getIntent().getStringExtra("userName");
		this.password = this.getIntent().getStringExtra("password");
		// System.out.println("this is user "+this.userName +
		// " with password "+this.password);
		Button createAccount = (Button) findViewById(R.id.newGroup);
		createAccount.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				inputNewAccountInfo();

			}

		});
		
		Button joinGroup = (Button) findViewById(R.id.joinGroup);
		joinGroup.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				
				
			}
			
		});
		ListView listView = (ListView) findViewById(R.id.groupslistview);
		listView.setTextFilterEnabled(true);
		GetGroupService accountTask = new GetGroupService(this.userName,
				this.password);
		accountTask.execute((Void) null);
		try {
			Thread.sleep(3500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (GetGroupService.result != null) {
			String[] tempArray = new String[10];
			String groupstemp = GetGroupService.result.replace('"', ' ');
			System.out.println(groupstemp);
			if (groupstemp.contains("  ")) {
				tempArray = groupstemp.split("  ");
				groups.addAll(Arrays.asList(tempArray));
				groups.set(0, groups.get(0).replace(" ", ""));
			} else {
				groups.add(groupstemp.replace(" ", ""));
			}

			if (groups.get(0).contains("\n")) {
				groups.set(0, groups.get(0).replace("\n", ""));
			} else {
				groups.set(groups.size() - 1,
						groups.get(groups.size() - 1).replace(" \n", ""));
			}
		}
		Log.i("groups", groups + " ");
		for(int i = 0; i< groups.size(); i++){
			if(TextUtils.isEmpty(groups.get(i))){
				groups.remove(i);
			}
		}
		// Create ArrayAdapter using the planet list.
		ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(this,
				R.layout.list_row, groups);

		listView.setAdapter(listAdapter);
		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				// When clicked, show a toast with the TextView text
				final Dialog dialog = new Dialog(GroupMgrActivity.this);
				dialog.setContentView(R.layout.group_id);
				dialog.setTitle("GroupID");
				// String fundAmount = getFund();
				// set the custom dialog components - text, image and button
				
				String itemName2 = groups.get(position);
				System.out.println("hahahaha"+itemName2);
				TextView gid = (TextView) dialog.findViewById(R.id.gid);
				
				GroupIDService addfund = new GroupIDService(itemName2);
				addfund.execute((Void)null);
		        try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

		        String accountstemp = GroupIDService.result.replace('"', ' ');
				gid.setText(accountstemp);
				
				Button dialogButton1 = (Button) dialog
						.findViewById(R.id.groupDismiss);
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
		final Dialog dialog = new Dialog(GroupMgrActivity.this);
		dialog.setContentView(R.layout.new_group);
		dialog.setTitle("New Group");

		Button dialogButton1 = (Button) dialog
				.findViewById(R.id.submitNewGroup);

		final EditText groupName = (EditText) dialog
				.findViewById(R.id.groupName);
		final EditText accountName = (EditText) dialog
				.findViewById(R.id.groupAccountName);
		dialogButton1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String name = null;
				name = groupName.getText().toString();
				String groupAccountName = accountName.getText().toString(); 
				Log.v("input name ", name);
				
				NewGroupService accountTask = new NewGroupService(name ,groupAccountName,userName, password);
				accountTask.execute((Void) null);
				dialog.dismiss();
				Intent selfIntent = new Intent(GroupMgrActivity.this,
						GroupMgrActivity.class);
				selfIntent.putExtra("userName", userName);
				selfIntent.putExtra("password", password);
				selfIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(selfIntent);
			}
		});

		dialog.show();
	}
}
