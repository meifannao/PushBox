package com.example.pushbox;


import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;



public class GameMain extends Activity {
	electricity elect;
	public LinearLayout ll = null;
	int level;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_main);
        ll = (LinearLayout)findViewById(R.id.ll);
    }
    /*
     * 通过判断用户点击按钮来做相应的动作
     * 通过Intent携带数据，Game类根据数据判断应该加载的那类游戏视图
     * continue= no是新游戏
     * continue = yes是需要加载存储的游戏状态，来继续上一次未完成的游戏
     * */
    public void onClick(View view)//首页按钮
    {
    	Intent in=null;
    	switch (view.getId()) 
    	{
		case R.id.btn_newgame://
			choice();
			in=new Intent(GameMain.this, Game.class);
			in.putExtra("choice",level);
			in.putExtra("continue", "no");//新游戏
			startActivity(in);
			break;

		case R.id.btn_exit:	 //游戏退出
				isFinish();
			break;
			
		case R.id.btn_about:  //游戏介绍
			in=new Intent(GameMain.this, about.class);
			startActivity(in);
			break;
			
		case R.id.btn_continue: //继续游戏
			in=new Intent(this, Game.class);
			in.putExtra("continue", "yes");
			startActivity(in);
			break;

		}
    }



    public void isFinish()
    {
    	AlertDialog.Builder dl1 = new AlertDialog.Builder(this);
		dl1.setTitle("Warnning!");
		dl1.setMessage("Do you want exit?");
		dl1.setPositiveButton("yes", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				GameMain.this.finish();
			}
		});
		dl1.setNeutralButton("no", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
			}
		});
		dl1.create();
		dl1.show();

    }
    @Override
    protected void onPause()
    {
    	// TODO Auto-generated method stub
    	super.onPause();
    	unregisterReceiver(elect);
    }
    
    @Override
    protected void onResume()
    {
    	// TODO Auto-generated method stub
    	super.onResume();
    	elect=new electricity();
    	IntentFilter intentFilter=new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
    	registerReceiver(elect, intentFilter);
    }
   

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	MenuInflater inflater =new MenuInflater(this);

        inflater.inflate(R.menu.activity_game_main, menu);
        return super.onCreateOptionsMenu(menu);
    }
    
    @Override                     
    public boolean onOptionsItemSelected(MenuItem item) {
    	// TODO Auto-generated method stub
    	switch (item.getItemId()) 
    	{
		case R.id.music:
			//ll.setBackgroundResource(R.drawable.pc3);
			Intent in=new Intent(GameMain.this,music.class);
			startActivity(in);
			break;

		case R.id.help:			
			Log.i("info", "help");
			Intent inn=new Intent(GameMain.this,help.class);
			startActivity(inn);
			//ll.setBackgroundResource(R.drawable.pc4);
			break;
				
		case R.id.bgset:
    		findBgset();
    		//ll.setBackgroundResource(R.drawable.pc2);
    		break;
    		
		case R.id.explain:
    		findAbout();
    		//ll.setBackgroundResource(R.drawable.pc1);
    		break;
		}
    	
    	return super.onOptionsItemSelected(item);
    }
	@SuppressWarnings("ResourceType")
    public void findBgset()
    {
    	AlertDialog.Builder dll = new AlertDialog.Builder(this);
    	final String[] Bgset = {"图片1","图片2","图片3","图片4"};
    	dll.setTitle("Which favorite's is select");
    	dll.setIcon(R.id.bgset);
    	dll.setItems(Bgset, new DialogInterface.OnClickListener() {
	    	@Override
	    	public void onClick(DialogInterface dialog,int which)
	    	{
	    		String s="你的选择是：";
	    		if(Bgset[which]=="图片1")
	    		{
	    			s += "图片1";
	    			ll.setBackgroundResource(R.drawable.pc1);
	    		}
	    		else if(Bgset[which]=="图片2")
	    		{
	    			s += "图片2";
	    			ll.setBackgroundResource(R.drawable.pc2);
	    		}
	    		else if(Bgset[which]=="图片3")
	    		{
	    			s += "图片3";
	    			ll.setBackgroundResource(R.drawable.pc3);
	    		}
	    		else
	    		{
	    			s += "图片4";
	    			ll.setBackgroundResource(R.drawable.pc4);
	    		}
	    		Toast.makeText(GameMain.this,s,1000).show();
	    	}
    	});
	    dll.create();
	    dll.show();
    }
    public void findAbout()
    {
    	String s="最经典的推箱子游戏，类似的游戏你一定早就玩过。要控制搬运工上下左右移动，来将箱子推到指定地点记得几年前，《推箱子》在PC机上刮起了一股不小的益智类游戏的旋风，现在许多资深玩家也都对《推箱子》赞不绝口，可见有深度的益智类游戏是非常受大家欢迎的。　\n推箱子游戏1981年由日本人今林宏行首创，是在1982年12月由Thinking Rabbit 公司首次发行，名“仓库番”。箱子只可以推, 不可以拉, 而且一次只能推动一个，胜利条件就是把所有的箱子都推到目的地。\n推箱子游戏是一种老少皆宜的益智游戏，既可以开发青少年学生的智力，又可以防止老年痴呆症，全家一起攻关还可以促进家庭和睦，何乐而不为？";
    	Toast.makeText(GameMain.this,s,Toast.LENGTH_LONG).show();
    }

	public int choice(){
		AlertDialog.Builder dll = new AlertDialog.Builder(this);
		final String[] Bgset = {"关卡1","关卡2","关卡3","关卡4","关卡5"};
		dll.setTitle("请选择关卡");

		dll.setItems(Bgset, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog,int which)
			{
				String s="你的选择是：";
				if(Bgset[which]=="关卡1")
				{
					level = 0;
				}
				else if(Bgset[which]=="关卡2")
				{
					level = 1;
				}
				else if(Bgset[which]=="关卡3")
				{
					level = 2;
				}
				else if(Bgset[which]=="关卡4")
				{
					level = 3;
				}
				else
				{
					level = 4;
				}
				//Toast.makeText(Game.this,s,1000).show();
			}
		});
		dll.create();
		dll.show();
		//dll.setCancelable(false);
		//SystemClock.sleep(1000);
		return level;
	}



}

