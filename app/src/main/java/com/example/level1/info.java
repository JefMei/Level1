package com.example.level1;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 梅梅 on 2016/2/25.
 */
public class info extends Activity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info);

        TextView bigname = (TextView) findViewById(R.id.bigname);
        TextView name = (TextView) findViewById(R.id.name);
        TextView pass = (TextView) findViewById(R.id.pass);
        TextView time = (TextView) findViewById(R.id.time);
        TextView note = (TextView) findViewById(R.id.note);

        ImageView rt = (ImageView) findViewById(R.id.rt);
        ImageView delete = (ImageView) findViewById(R.id.delete);
        android.support.design.widget.FloatingActionButton change = (FloatingActionButton) findViewById(R.id.change);


        final InfoItem infoItem = (InfoItem) getIntent().getSerializableExtra("InfoItem");
       // final InfoItem infoItem1 = (InfoItem) getIntent().getSerializableExtra("InfoItem1");///sfjl
        final int position = (int) getIntent().getSerializableExtra("position");
        final InfoItem infoItem1 = (InfoItem) getIntent().getSerializableExtra("InfoItem1");


            bigname.setText(infoItem.getName());
            name.setText(infoItem.getAccount());
            pass.setText(infoItem.getPass());
            time.setText(infoItem.getTime());
            note.setText(infoItem.getNote());


        //不返回结果值的intent 该怎样写
        rt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(info.this,MainActivity.class);
                startActivity(intent);
            }
        });

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(info.this,Changeinfo.class);
                intent.putExtra("position",position);
                intent.putExtra("InfoItem",infoItem);
                startActivityForResult(intent,0);
            }
        });

        //删除指定数据并返回主界面
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //删除数据
                Gson gson = new Gson();

                SharedPreferences pref = getSharedPreferences("info",MODE_APPEND);
                SharedPreferences.Editor editor = pref.edit();
                String json = pref.getString("infodata",null);
                String json1 = pref.getString("data",null);

                List<InfoItem> infodata = gson.fromJson(json,
                        new TypeToken<ArrayList<InfoItem>>(){}.getType());
                List<Item> data = gson.fromJson(json1,
                        new TypeToken<ArrayList<Item>>(){}.getType());

                infodata.remove(position);
                data.remove(position);

                String json2 = gson.toJson(infodata);
                String json3 = gson.toJson(data);
                editor.putString("infodata",json2);
                editor.putString("data",json3);

                editor.commit();

                //返回主界面
                Intent intent = new Intent(info.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }

    protected void onActivityResult(int requestCode , int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(resultCode == RESULT_OK){

            TextView bigname = (TextView) findViewById(R.id.bigname);
            TextView name = (TextView) findViewById(R.id.name);
            TextView pass = (TextView) findViewById(R.id.pass);
            TextView time = (TextView) findViewById(R.id.time);
            TextView note = (TextView) findViewById(R.id.note);

            final InfoItem infoItem1 = (InfoItem) getIntent().getSerializableExtra("InfoItem1");

            if(infoItem1 != null){
                bigname.setText(infoItem1.getName());
                name.setText(infoItem1.getAccount());
                pass.setText(infoItem1.getPass());
                time.setText(infoItem1.getTime());
                note.setText(infoItem1.getNote());
            }
        }
        else {
            Toast.makeText(this,"未作任何修改",Toast.LENGTH_SHORT).show();
        }
    }
}
