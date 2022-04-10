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
	//���岼��ֵ����ʾ��Ϸ�Ǽ��� ��������Ϸ
	public boolean continue_game;
	private String s;
	//s��������Intent���ݵ�����
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		gate = getIntent().getIntExtra("choice",0);
		super.onCreate(savedInstanceState);
		//Ĭ��������Ϸ
		continue_game=false;
		//Intent���ݹ�����ֵ
		s=getIntent().getStringExtra("continue");
		//gate = getIntent().getIntExtra("choice",0);
		/*
		 * ������յ���yes,˵���û���������Continue��ť
		 * ��ô��continue_gameΪ��ʱ��ʹ��loadMap()�������ڴ�����*/
		if(s.equals("yes"))
			continue_game=true;
		if(continue_game)
			loadMap();

		setContentView(R.layout.game);
		gameView=(GameView) findViewById(R.id.mygameview);
		lll = (LinearLayout)findViewById(R.id.lll);//���ղ���
		lll.setBackgroundResource(R.drawable.mn);

	}

	//����������ʱ�������û��İ���
	//����Musichandle���play�������������Ƿ񲥷�����
	@Override
	protected void onResume() 
	{
		// TODO Auto-generated method stub
		super.onResume();
		if(music.isMusicCheck(this))
			musicHandle.play(this, R.raw.bg);
	}
	
	//��Ϸ��ͣ,����Musichandle���stop()����ֹͣ����
	@Override
	protected void onPause() 
	{
		// TODO Auto-generated method stub
		super.onPause();
		musicHandle.stop(this);
		/*
		 * ��Ϸ��ͣʱ����Ϸ������Ҫ�洢����Ϸ�ĵ�ͼ��Ϣ����������Ҫ�洢
		 * savegame()�ķ���ֵʱһ���ַ��������Ĺ��ܾ��ǰѶ�ά����ת��Ϊ�ַ���
		 * maprow.mapcolumn�����ʱ��Ϸ��������*/
		
		//����Ĵ���
		getPreferences(MODE_PRIVATE).edit().putLong("gate",gameView.gate).commit();
		
		getPreferences(MODE_PRIVATE).edit().putString("maps",saveGame()).commit();
		
		getPreferences(MODE_PRIVATE).edit().putLong("maprow",gameView.maprow).commit();
		
		getPreferences(MODE_PRIVATE).edit().putLong("mapcolumn",gameView.mapcolumn).commit();
	}
	/*
	 * saveGame()�ķ���ֵʱһ���ַ��������Ĺ��ܾ��ǰ�һ����ά����ת�����ַ���
	 * �ȰѶ�ά����ת��Ϊһά����
	 * Ȼ��� һά����ʹ��StringBuilder���append����������Ϊһ��StringBuilder
	 * ���ʹ��Object���е�toString����������StringBuilderת��ΪString����*/
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
	 * ���ȡֵ���ɹ������ǵ�һ��
	 * first���ǵ�һ��ת�����ַ���*/
	
	String first="0011100000121000001311111114342112346111111141000001210000011100";
	public int[][]stored;
	/*
	 * loadMap()������ʱ�ڴ洢�ռ���ȡ���ַ�������Ϸ��ֵ������ԭΪ��ά����*/
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
//			mTextView.setText("��ǰΪ��"+gate+"��");
	}

	//���������Menu�˵�,ʹ�����ֵĿ���
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
	    			lll.setBackgroundResource(R.drawable.pc1);
	    		}
	    		else if(Bgset[which]=="ͼƬ2")
	    		{
	    			s += "ͼƬ2";
	    			lll.setBackgroundResource(R.drawable.pc2);
	    		}
	    		else if(Bgset[which]=="ͼƬ3")
	    		{
	    			s += "ͼƬ3";
	    			lll.setBackgroundResource(R.drawable.pc3);
	    		}
	    		else
	    		{
	    			s += "ͼƬ4";
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
    	String s="������������Ϸ�����Ƶ���Ϸ��һ����������Ҫ���ư��˹����������ƶ������������Ƶ�ָ���ص�ǵü���ǰ���������ӡ���PC���Ϲ�����һ�ɲ�С����������Ϸ�����磬��������������Ҳ���ԡ������ӡ��޲����ڣ��ɼ�����ȵ���������Ϸ�Ƿǳ��ܴ�һ�ӭ�ġ���\n��������Ϸ1981�����ձ��˽��ֺ����״�������1982��12����Thinking Rabbit ��˾�״η��У������ֿⷬ��������ֻ������, ��������, ����һ��ֻ���ƶ�һ����ʤ���������ǰ����е����Ӷ��Ƶ�Ŀ�ĵء�\n��������Ϸ��һ�����ٽ��˵�������Ϸ���ȿ��Կ���������ѧ�����������ֿ��Է�ֹ����մ�֢��ȫ��һ�𹥹ػ����Դٽ���ͥ���������ֶ���Ϊ��";
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

