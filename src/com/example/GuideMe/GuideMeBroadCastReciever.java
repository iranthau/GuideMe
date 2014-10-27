package com.example.GuideMe;
//Class created by Iran
//This class start the test10 application at the boot time
//Date: 08/05/2014



import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class GuideMeBroadCastReciever extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		Intent serviceIntent = new Intent(context, MainActivity.class);
		serviceIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(serviceIntent);
	}

}
