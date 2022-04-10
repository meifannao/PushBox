package com.example.pushbox;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class electricity extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		if(intent.getAction().equals(intent.ACTION_BATTERY_CHANGED)){
			int level=intent.getIntExtra("level", 0);
			int total=intent.getIntExtra("scale", 100);
			Toast.makeText(context, "当前电量是:"+(level*100)/total+"%",Toast.LENGTH_SHORT).show();
		}

	}

}
