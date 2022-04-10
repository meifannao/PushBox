//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.example.pushbox;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

public class help extends Activity {
    VideoView vv;

    public help() {
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.help);
        this.vv = (VideoView)this.findViewById(R.id.videoview1);//this.vv.setVideoPath("android.resource://" + this.getPackageName() + "/" + R.raw.gate1);
        // this.vv.setVideoPath("android.resource://" + "com.example.pushbox" + "/" + R.raw.gate1);

        AlertDialog.Builder dll = new AlertDialog.Builder(this);
        final String[] Bgset = {"关卡1","关卡2","关卡3","关卡4","关卡5"};
        dll.setTitle("Which favorite's is select");
        //dll.setIcon(R.id.bgset);
        dll.setItems(Bgset, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog,int which)
            {
                String s="你的选择是：";
                if(Bgset[which]=="关卡1")
                {
                    //s += "图片1";
                    vv.setVideoPath("android.resource://" + "com.example.pushbox" + "/" + R.raw.gate_1);
                }
                else if(Bgset[which]=="关卡2")
                {
                    //s += "跑车2";
                    vv.setVideoPath("android.resource://" + "com.example.pushbox" + "/" + R.raw.gate_2);
                }
                else if(Bgset[which]=="关卡3")
                {
                    //s += "跑车3";
                    vv.setVideoPath("android.resource://" + "com.example.pushbox" + "/" + R.raw.gate_3);
                }
                else if(Bgset[which]=="关卡4")
                {
                    //s += "跑车4";
                    vv.setVideoPath("android.resource://" + "com.example.pushbox" + "/" + R.raw.gate_4);
                }
                else
                {
                    vv.setVideoPath("android.resource://" + "com.example.pushbox" + "/" + R.raw.gate_5);
                }
                //Toast.makeText(Game.this,s,1000).show();
            }
        });
        dll.create();
        dll.show();

        //this.vv.setVideoPath();
        this.vv.setMediaController(new MediaController(this));
        this.vv.start();
        this.vv.requestFocus();
    }
}
