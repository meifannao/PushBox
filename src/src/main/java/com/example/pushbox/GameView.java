package com.example.pushbox;
import java.util.ArrayList;
import java.util.HashMap;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

public class GameView extends View {
	public int gate=Game.gate;
	private int manx;
	private int many;
	private float xoff=10;
	private float yoff=20;
	private int tilesize;
	public int maprow=0;
	public int mapcolumn=0;
	private int width=0;
	private int heith=0;
	private Bitmap pic[]=null;
	final int WALL=1;
	final int GOAL=2;
	final int ROAD=3;
	final int BOX=4;
	final int BOXATGOAL=5;
	final int MAN=6;
	private Paint paint;
	private Game game=null;
	public  int [][]map=null;
	private int [][]tem;//原来的地图
	float currentx;
	float currenty;
	String gatenum;

	SoundPool soundPool;
	HashMap<Integer, Integer>musicId=new HashMap<Integer, Integer>();


	
	public GameView(Context context, AttributeSet attrs) 
	{

		super(context, attrs);
		//实例化game，后面用
		game=(Game)context;
		soundPool=new SoundPool(3, 0,5);
		musicId.put(1, soundPool.load(getContext(), R.raw.click, 1));
		
		/*WindowManager = manager = game.getWindowManager();
		width = manager.getDefaultDisplay().getWidth();
		heith = manager.getDefaultDisplay().getHeight(); */
		
		//上面的那种获取宽高改成下面的那种
		
		//获取当前游戏屏幕的宽和高
		DisplayMetrics dm = getResources().getDisplayMetrics();
		width= dm.widthPixels;
		heith= dm.heightPixels;
		this.setFocusable(true);
		/*
		 * 这里不再是initMap()
		 * 我们需要判断一下，用户单击的时哪个按钮
		 * 如果是continue按钮，则需要使用存储的地图game.stored
		 * 如果是New Game按钮，则直接使用initMap()从MapList中调取地图*/
		if(game.continue_game)
		{
			map=game.stored;
			getMapdetail();
			getmanposition();
		}
		else
		{
			initMap();
		}
		initpic();	
	}
	public void initMap() 
	{
		int Gate=gate+1;

		Toast.makeText(this.getContext(), "目前是第"+Gate+"关", Toast.LENGTH_SHORT).show();
		map=getmap(gate);
		getMapdetail();
		getmanposition();
		list.clear();
	}
	public int [][]getmap(int grate){
		return MapList.getmap(grate);
	}
	//获取地图的行列数，地图边距，每个图片的大小，保存当前游戏的原始地图
	private void getMapdetail()
	{
		maprow=map.length;
		mapcolumn=map[gate].length;
		xoff=30;
		yoff=60;
		int t=maprow>mapcolumn?maprow:mapcolumn;
		int s1=(int) Math.floor((width-2*xoff)/t);
		int s2=(int) Math.floor((heith-xoff)/t);
		tilesize=s1<s2?s1:s2;
		tem=MapList.getmap(gate);
		
	}
	public  void getmanposition()
	{
			for(int i=0;i<map.length;i++)
				for(int j=0;j<map[0].length;j++)
					if(map[i][j]==MAN)
					{
						manx=i;
						many=j;
						break;
					}
	}
	public void initpic()
	{
		pic =new Bitmap[7];
		Resources r=this.getContext().getResources();
		loadPic(WALL,r.getDrawable(R.drawable.wall));
		loadPic(GOAL,r.getDrawable(R.drawable.goal));
		loadPic(ROAD,r.getDrawable(R.drawable.road));
		loadPic(BOX,r.getDrawable(R.drawable.box));
		loadPic(BOXATGOAL,r.getDrawable(R.drawable.box_at_goal));
		loadPic(MAN,r.getDrawable(R.drawable.man));
	}
	
	
	public void loadPic(int key, Drawable tile) 
	{
		Bitmap bitmap=Bitmap.createBitmap(tilesize, tilesize, Bitmap.Config.ARGB_8888);
		Canvas canvas=new Canvas(bitmap);
		tile.setBounds(0, 0, tilesize, tilesize);
		tile.draw(canvas);
		pic[key]=bitmap;
	}
	
	
	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		soundPool.play(musicId.get(1),1,1, 0, 0, 1);
//		MediaPlayer mPlayer = MediaPlayer.create(this.getContext(), R.raw.click);
//		mPlayer.start();
		currentx=event.getX();
		currenty=event.getY();
		float x=(float)(xoff+many*tilesize);
		float y=(float)(yoff+manx*tilesize);
		if(currenty>y&&(currenty<y+tilesize))
		{
			if(currentx>x+tilesize)
			{
				moveRight();	
			}
			if(currentx<x)
			{
				moveLeft();
			}
		}
		if(currentx>x&&(currentx<x+tilesize))
		{
			if(currenty>y+tilesize){
				moveDown();
			}
			if(currenty<y){
				moveup();
			}
		}
		if(finished())
		{
			
			nextGate();
		}
		this.invalidate();
		return super.onTouchEvent(event);
	}
	
	private void moveup() 
	{
		if(map[manx-1][many]==BOX||map[manx-1][many]==BOXATGOAL)
		{
			if(map[manx-2][many]==GOAL||map[manx-2][many]==ROAD)
			{
				storyMap(map);
				map[manx-2][many]=map[manx-2][many]==GOAL?BOXATGOAL:BOX;
				map[manx-1][many]=MAN;
				map[manx][many]=roadorgoal(manx,many);
				manx--;
			}
		}
		else
		{
			if(map[manx-1][many]==ROAD||map[manx-1][many]==GOAL){
				storyMap(map);
				map[manx-1][many]=MAN;
				map[manx][many]=roadorgoal(manx,many);
				manx--;
			}
		}
	}
	
	private void moveDown() 
	{
		if(map[manx+1][many]==BOX||map[manx+1][many]==BOXATGOAL)
		{
			if(map[manx+2][many]==GOAL||map[manx+2][many]==ROAD)
			{
				storyMap(map);
				map[manx+2][many]=map[manx+2][many]==GOAL?BOXATGOAL:BOX;
				map[manx+1][many]=MAN;
				map[manx][many]=roadorgoal(manx,many);
				manx++;
			}
		}
		else
		{
			if(map[manx+1][many]==ROAD||map[manx+1][many]==GOAL)
			{
				storyMap(map);
				map[manx+1][many]=MAN;
				map[manx][many]=roadorgoal(manx,many);
				manx++;
			}
		}
	}
	
	private void moveLeft() 
	{
		if (map[manx][many - 1] == BOX || map[manx][many - 1] == BOXATGOAL) 
		{
			if (map[manx][many - 2] == GOAL || map[manx][many - 2] == ROAD) 
			{
				storyMap(map);
				map[manx][many - 2] = map[manx][many - 2] == GOAL ? BOXATGOAL: BOX;
				map[manx][many - 1] = MAN;
				map[manx][many] = roadorgoal(manx, many);
				many--;
			}
		}
		else 
		{
			if (map[manx][many - 1] == ROAD || map[manx][many - 1] == GOAL) 
			{
				storyMap(map);
				map[manx][many - 1] = MAN;
				map[manx][many] = roadorgoal(manx, many);
				many--;
			}
		}
	}
	
	private void moveRight() 
	{
		if(map[manx][many+1]==BOX||map[manx][many+1]==BOXATGOAL)
		{
			if(map[manx][many+2]==GOAL||map[manx][many+2]==ROAD)
			{
				storyMap(map);
				map[manx][many+2]=map[manx][many+2]==GOAL?BOXATGOAL:BOX;
				map[manx][many+1]=MAN;
				map[manx][many]=roadorgoal(manx,many);
				many++;
			}
		}
		else
		{
			if(map[manx][many+1]==ROAD||map[manx][many+1]==GOAL)
			{
				storyMap(map);
				map[manx][many+1]=MAN;
				map[manx][many]=roadorgoal(manx,many);
				many++;
			}
		}
	}
	
	private boolean finished() 
	{
		boolean finish=true;
		for(int i=0;i<maprow;i++)
			for(int j=0;j<mapcolumn;j++)
			{
				if(map[i][j]==GOAL||map[i][j]==BOX)
					finish= false;
			}
		
		return finish;
	}



	public void nextGate() 
	{
		if(gate<MapList.getcount()-1)
		{
			gate++;
		}
		else
			Toast.makeText(this.getContext(), "目前是最后一关,you win!", Toast.LENGTH_LONG).show();

		this.reinitmap();
		this.invalidate();
		
	}

	public void preGate()
	{
		if(gate > 0)
		{
			gate --;
			this.reinitmap();
			this.invalidate();
		}
		else
			Toast.makeText(this.getContext(), "目前是第一关", Toast.LENGTH_LONG).show();

	}



	public int roadorgoal(int x, int y) 
	{
		int result=ROAD;
		if(tem[x][y]==GOAL)
		{
			result=GOAL;
		}
		return result;
	}
	
	public void reinitmap()
	{
		initMap();
		initpic();
		
	}
	

	@Override
	protected void onDraw(Canvas canvas) 
	{
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		for (int i = 0; i < maprow; i++)
			for (int j = 0; j < mapcolumn; j++) 
			{
				if (map[i][j] != 0)
					canvas.drawBitmap(pic[map[i][j]], xoff + j * tilesize,
							yoff + i * tilesize, paint);
			}

	}
	class CurrentMap
	{
		int[][]currMap;
		public CurrentMap(int[][] maps)
		{
			int row=maps.length;
			int column=maps[0].length;
			int [][]temp=new int [row][column];
			for(int i=0;i<row;i++)
				for(int j=0;j<column;j++)
				{
					temp[i][j]=maps[i][j];
				}
				this.currMap=temp;
			}
			public int[][]getmap()
			{
				return currMap;
			}
		}
	public ArrayList<CurrentMap> list=new ArrayList<GameView.CurrentMap>();


	public void back() 
	{
		if(list.size()>0)
		{
			CurrentMap proMap=list.get(list.size()-1);
			map=proMap.getmap();
			getmanposition();
			list.remove(list.size()-1);
			
		}
		else
		{
			Toast.makeText(getContext(), "You Can't Back", Toast.LENGTH_LONG).show();
		}
	}
	public void storyMap(int map[][])
	{
		CurrentMap cMap=new CurrentMap(map);
		list.add(cMap);
		/*if(list.size()>10)
			list.remove(0);*/
		
	}
	public void remake(){



	}
		
}
