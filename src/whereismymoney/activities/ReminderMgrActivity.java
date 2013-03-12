package whereismymoney.activities;

import java.util.ArrayList;
import java.util.Arrays;

import whereismymoney.services.GetReminderService;
import whereismymoney.services.GetWishservice;
import whereismymoney.services.NewReminderService;
import whereismymoney.services.NewWishService;
import whereismymoney.services.WishInfoService;
import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class ReminderMgrActivity extends Activity {
	private String userName;
	private String password;
	public ArrayList<String> reminders = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reminder_layout);
		this.userName = this.getIntent().getStringExtra("userName");
		this.password = this.getIntent().getStringExtra("password");
		// System.out.println("this is user "+this.userName +
		// " with password "+this.password);
		Button createAccount = (Button) findViewById(R.id.newReminder);
		createAccount.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				inputNewAccountInfo();

			}

		});
		ListView listView = (ListView) findViewById(R.id.reminderslistview);
		listView.setTextFilterEnabled(true);
		GetReminderService accountTask = new GetReminderService(this.userName,
				this.password);
		accountTask.execute((Void) null);
		try {
			Thread.sleep(3500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (GetReminderService.result != null) {
			String[] tempArray = new String[10];
			String reminderstemp = GetReminderService.result.replace('"', ' ');
			System.out.println(reminderstemp);
			if (reminderstemp.contains("  ")) {
				tempArray = reminderstemp.split("  ");
				reminders.addAll(Arrays.asList(tempArray));
				reminders.set(0, reminders.get(0).replace(" ", ""));
			} else {
				reminders.add(reminderstemp.replace(" ", ""));
			}

			if (reminders.get(0).contains("\n")) {
				reminders.set(0, reminders.get(0).replace("\n", ""));
			} else {
				reminders.set(reminders.size() - 1,
						reminders.get(reminders.size() - 1).replace(" \n", ""));
			}
		}
		Log.i("reminders", reminders + " ");

		// Create ArrayAdapter using the planet list.
		ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(this,
				R.layout.list_row, reminders);

		listView.setAdapter(listAdapter);
		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
			}
		});
	}

	private void inputNewAccountInfo() {
		// When clicked, show a toast with the TextView text
		final Dialog dialog = new Dialog(ReminderMgrActivity.this);
		dialog.setContentView(R.layout.new_reminder);
		dialog.setTitle("New Reminder");
		// String fundAmount = getFund();
		// set the custom dialog components - text, image and button
		String[] unitData = { "Daily", "Weekly", "Biweekly", "Monthly",
				"Yearly", "Quarterly", "SemiYearly" };
		Spinner units = (Spinner) dialog.findViewById(R.id.unit);
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, unitData);
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		units.setAdapter(dataAdapter);
		
		DatePicker pickedDate = (DatePicker) dialog.findViewById(R.id.remindDate);
		int day = pickedDate.getDayOfMonth();
		int month = pickedDate.getMonth() + 1;
		int year = pickedDate.getYear();
		String date = year + "-"+month + "-" + day;
		Button dialogButton1 = (Button) dialog
				.findViewById(R.id.submitReminder);
		final String unit = units.getSelectedItem().toString();
		final String Date = date;
		// if button is clicked, close the custom dialog
		final EditText reminderName = (EditText) dialog
				.findViewById(R.id.reminderName);
		final EditText repeats = (EditText) dialog
				.findViewById(R.id.no_repeats);
		final EditText discription = (EditText) dialog
				.findViewById(R.id.discription);

		dialogButton1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String name = null;
				name = reminderName.getText().toString();
				Log.v("input name ", name);
				String noRepeats = null;
				noRepeats = repeats.getText().toString();
				Log.v("input fund ", noRepeats);
				String reminderDiscription = discription.getText().toString();
				NewReminderService accountTask = new NewReminderService(name,
						noRepeats, unit, Date, reminderDiscription, userName, password);
				accountTask.execute((Void) null);
				dialog.dismiss();
				Intent selfIntent = new Intent(ReminderMgrActivity.this,
						ReminderMgrActivity.class);
				selfIntent.putExtra("userName", userName);
				selfIntent.putExtra("password", password);
				selfIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(selfIntent);
			}
		});

		dialog.show();
	}
}
