package com.example.pushbox;

import android.content.Context;
import android.media.MediaPlayer;

public class musicHandle {
	private static MediaPlayer mp=null;
	public static void play(Context context,int resource)
	{
		stop(context);
		if(music.isMusicCheck(context))
		{
			mp=MediaPlayer.create(context, resource);
			mp.setLooping(true);
			mp.start();
		}
	}
	public static void stop(Context context) 
	{
		if(mp!=null)
		{
			mp.stop();
			mp.release();
			mp=null;
		}
		
	}
}
