package com.example.pushbox;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Game extends Activity 
{
	public LinearLayout lll = null;
	public static int gate=0;
	GameView gameView;
	//定义布尔值，表示游戏是继续 还是新游戏
	public boolean continue_game;
	private String s;
	//s用来接收Intent传递的数据
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		gate = getIntent().getIntExtra("choice",0);
		super.onCreate(savedInstanceState);
		//默认是新游戏
		continue_game=false;
		//Intent传递过来的值
		s=getIntent().getStringExtra("continue");
		//gate = getIntent().getIntExtra("choice",0);
		/*
		 * 假如接收的是yes,说明用户单击的是Continue按钮
		 * 那么，continue_game为真时，使用loadMap()来加载内存数据*/
		if(s.equals("yes"))
			continue_game=true;
		if(continue_game)
			loadMap();

		setContentView(R.layout.game);
		gameView=(GameView) findViewById(R.id.mygameview);
		lll = (LinearLayout)findViewById(R.id.lll);//接收布局
		lll.setBackgroundResource(R.drawable.mn);

	}

	//当界面运行时，根据用户的爱好
	//调用Musichandle类的play（）方法决定是否播放音乐
	@Override
	protected void onResume() 
	{
		// TODO Auto-generated method stub
		super.onResume();
		if(music.isMusicCheck(this))
			musicHandle.play(this, R.raw.bg);
	}
	
	//游戏暂停,调用Musichandle类的stop()方法停止音乐
	@Override
	protected void onPause() 
	{
		// TODO Auto-generated method stub
		super.onPause();
		musicHandle.stop(this);
		/*
		 * 游戏暂停时，游戏数据需要存储，游戏的地图信息，行列数都要存储
		 * savegame()的返回值时一个字符串，它的功能就是把二维数组转化为字符串
		 * maprow.mapcolumn保存的时游戏的行列数*/
		
		//新添的代码
		getPreferences(MODE_PRIVATE).edit().putLong("gate",gameView.gate).commit();
		
		getPreferences(MODE_PRIVATE).edit().putString("maps",saveGame()).commit();
		
		getPreferences(MODE_PRIVATE).edit().putLong("maprow",gameView.maprow).commit();
		
		getPreferences(MODE_PRIVATE).edit().putLong("mapcolumn",gameView.mapcolumn).commit();
	}
	/*
	 * saveGame()的返回值时一个字符串，它的功能就是把一个二维数组转化成字符串
	 * 先把二维数组转化为一维数组
	 * 然后把 一维数组使用StringBuilder类的append（）方法变为一个StringBuilder
	 * 最后使用Object类中的toString（）方法把StringBuilder转化为String对象*/
	public String saveGame() 
	{
		int[] saved = new int[gameView.maprow * gameView.mapcolumn];
		for (int i = 0; i < gameView.maprow; i++)
			for (int j = 0; j < gameView.mapcolumn; j++)
			{
				saved[i * gameView.mapcolumn + j] = gameView.map[i][j];
			}
		StringBuilder buf = new StringBuilder();
	      for (int element : saved)
	      {
	         buf.append(element);
	      }
	      String s=buf.toString();

	      return s;
	}
	/*
	 * 如果取值不成功，则还是第一关
	 * first就是第一关转化的字符串*/
	
	String first="0011100000121000001311111114342112346111111141000001210000011100";
	public int[][]stored;
	/*
	 * loadMap()的作用时在存储空间里取出字符串的游戏数值，并还原为二维数组*/
	public void loadMap() 
	{
		gate = (int)getPreferences(MODE_PRIVATE).getLong("gate",0);
		String mapstring =getPreferences(MODE_PRIVATE).getString("maps", first);
		int ii=(int) getPreferences(MODE_PRIVATE).getLong("maprow", 8);
		int jj=(int) getPreferences(MODE_PRIVATE).getLong("mapcolumn", 8);
		int []maps=new int [mapstring.length()];
		
		for(int i=0;i<maps.length;i++)
		{
			maps[i]=mapstring.charAt(i)-'0';
		}
		stored=new int [ii][jj];
		int a=0;
		for(int m=0;m<ii;m++)
			for(int n=0;n<jj;n++)
			{
				stored[m][n]=maps[a];
				a++;
			}
//		TextView mTextView=findViewById(R.id.gate_text);
//			mTextView.setText("当前为第"+gate+"关");
	}

	//本类中添加Menu菜单,使用音乐的控制
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuInflater inflater=new MenuInflater(this);
		inflater.inflate(R.menu.activity_game_main, menu);
		
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override                     
    public boolean onOptionsItemSelected(MenuItem item) {
    	// TODO Auto-generated method stub
    	switch (item.getItemId()) 
    	{
		case R.id.music:
			Intent in=new Intent(Game.this,music.class);
			startActivity(in);
			break;

		case R.id.help:			
			Log.i("info", "help");
			Intent inn=new Intent(Game.this,help.class);
			startActivity(inn);
			break;

	
		case R.id.bgset:
    		findBgset();
    		break;
    		
		case R.id.explain:
    		findAbout();
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
	    			lll.setBackgroundResource(R.drawable.pc1);
	    		}
	    		else if(Bgset[which]=="图片2")
	    		{
	    			s += "图片2";
	    			lll.setBackgroundResource(R.drawable.pc2);
	    		}
	    		else if(Bgset[which]=="图片3")
	    		{
	    			s += "图片3";
	    			lll.setBackgroundResource(R.drawable.pc3);
	    		}
	    		else
	    		{
	    			s += "图片4";
	    			lll.setBackgroundResource(R.drawable.pc4);
	    		}
	    		Toast.makeText(Game.this,s,1000).show();
	    	}
    	});
	    dll.create();
	    dll.show();
    }
    public void findAbout()
    {
    	String s="最经典的推箱子游戏，类似的游戏你一定早就玩过。要控制搬运工上下左右移动，来将箱子推到指定地点记得几年前，《推箱子》在PC机上刮起了一股不小的益智类游戏的旋风，现在许多资深玩家也都对《推箱子》赞不绝口，可见有深度的益智类游戏是非常受大家欢迎的。　\n推箱子游戏1981年由日本人今林宏行首创，是在1982年12月由Thinking Rabbit 公司首次发行，名“仓库番”。箱子只可以推, 不可以拉, 而且一次只能推动一个，胜利条件就是把所有的箱子都推到目的地。\n推箱子游戏是一种老少皆宜的益智游戏，既可以开发青少年学生的智力，又可以防止老年痴呆症，全家一起攻关还可以促进家庭和睦，何乐而不为？";
    	Toast.makeText(Game.this,s,Toast.LENGTH_LONG).show();
    }

	public void onClick1(View view){
		switch (view.getId()){
			case R.id.btn_back:
				gameView.back();
				gameView.invalidate();
				break;
			case R.id.btn_regame:
				gameView.reinitmap();
				//gameView.back();
				gameView.invalidate();
				break;

		}
	}

	public void nextgate(View view){
		gameView.nextGate();
	}
	public void pregate(View view){
		gameView.preGate();
	}


}

