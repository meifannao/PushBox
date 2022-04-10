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
     * ͨ���ж��û������ť������Ӧ�Ķ���
     * ͨ��IntentЯ�����ݣ�Game����������ж�Ӧ�ü��ص�������Ϸ��ͼ
     * continue= no������Ϸ
     * continue = yes����Ҫ���ش洢����Ϸ״̬����������һ��δ��ɵ���Ϸ
     * */
    public void onClick(View view)//��ҳ��ť
    {
    	Intent in=null;
    	switch (view.getId()) 
    	{
		case R.id.btn_newgame://
			choice();
			in=new Intent(GameMain.this, Game.class);
			in.putExtra("choice",level);
			in.putExtra("continue", "no");//����Ϸ
			startActivity(in);
			break;

		case R.id.btn_exit:	 //��Ϸ�˳�
				isFinish();
			break;
			
		case R.id.btn_about:  //��Ϸ����
			in=new Intent(GameMain.this, about.class);
			startActivity(in);
			break;
			
		case R.id.btn_continue: //������Ϸ
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
    	final String[] Bgset = {"ͼƬ1","ͼƬ2","ͼƬ3","ͼƬ4"};
    	dll.setTitle("Which favorite's is select");
    	dll.setIcon(R.id.bgset);
    	dll.setItems(Bgset, new DialogInterface.OnClickListener() {
	    	@Override
	    	public void onClick(DialogInterface dialog,int which)
	    	{
	    		String s="���ѡ���ǣ�";
	    		if(Bgset[which]=="ͼƬ1")
	    		{
	    			s += "ͼƬ1";
	    			ll.setBackgroundResource(R.drawable.pc1);
	    		}
	    		else if(Bgset[which]=="ͼƬ2")
	    		{
	    			s += "ͼƬ2";
	    			ll.setBackgroundResource(R.drawable.pc2);
	    		}
	    		else if(Bgset[which]=="ͼƬ3")
	    		{
	    			s += "ͼƬ3";
	    			ll.setBackgroundResource(R.drawable.pc3);
	    		}
	    		else
	    		{
	    			s += "ͼƬ4";
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
    	String s="������������Ϸ�����Ƶ���Ϸ��һ����������Ҫ���ư��˹����������ƶ������������Ƶ�ָ���ص�ǵü���ǰ���������ӡ���PC���Ϲ�����һ�ɲ�С����������Ϸ�����磬��������������Ҳ���ԡ������ӡ��޲����ڣ��ɼ�����ȵ���������Ϸ�Ƿǳ��ܴ�һ�ӭ�ġ���\n��������Ϸ1981�����ձ��˽��ֺ����״�������1982��12����Thinking Rabbit ��˾�״η��У������ֿⷬ��������ֻ������, ��������, ����һ��ֻ���ƶ�һ����ʤ���������ǰ����е����Ӷ��Ƶ�Ŀ�ĵء�\n��������Ϸ��һ�����ٽ��˵�������Ϸ���ȿ��Կ���������ѧ�����������ֿ��Է�ֹ����մ�֢��ȫ��һ�𹥹ػ����Դٽ���ͥ���������ֶ���Ϊ��";
    	Toast.makeText(GameMain.this,s,Toast.LENGTH_LONG).show();
    }

	public int choice(){
		AlertDialog.Builder dll = new AlertDialog.Builder(this);
		final String[] Bgset = {"�ؿ�1","�ؿ�2","�ؿ�3","�ؿ�4","�ؿ�5"};
		dll.setTitle("��ѡ��ؿ�");

		dll.setItems(Bgset, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog,int which)
			{
				String s="���ѡ���ǣ�";
				if(Bgset[which]=="�ؿ�1")
				{
					level = 0;
				}
				else if(Bgset[which]=="�ؿ�2")
				{
					level = 1;
				}
				else if(Bgset[which]=="�ؿ�3")
				{
					level = 2;
				}
				else if(Bgset[which]=="�ؿ�4")
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

