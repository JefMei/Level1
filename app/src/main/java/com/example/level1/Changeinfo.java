package com.example.level1;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 梅梅 on 2016/2/28.
 */
public class Changeinfo extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input);

        //用户信息
        final EditText name = (EditText) findViewById(R.id.name);
        final EditText account = (EditText) findViewById(R.id.account);
        final EditText pass = (EditText) findViewById(R.id.pass);
        final EditText note = (EditText) findViewById(R.id.note);

        ImageView submit  = (ImageView) findViewById(R.id.submit);//信息提交键

        final InfoItem infoItem = (InfoItem) getIntent().getSerializableExtra("InfoItem");

        //使界面显示原来的信息
        name.setText(infoItem.getName());
        account.setText(infoItem.getAccount());
        pass.setText(infoItem.getPass());
        if(!infoItem.getNote().equals("无")){
            note.setText(infoItem.getNote());
        }


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final int position = (int) getIntent().getSerializableExtra("position");

                //创建存储对象Sharedpreferences  和 编辑对象Editor
                SharedPreferences pref = getSharedPreferences("info",MODE_APPEND);
                SharedPreferences.Editor editor = pref.edit();

                //将infodata data 从sp中取出
                Gson gson = new Gson();

                String json = pref.getString("infodata",null);
                String json1 = pref.getString("data",null);

                List<InfoItem> infodata = gson.fromJson(json,
                        new TypeToken<ArrayList<InfoItem>>(){}.getType());
                List<Item> data = gson.fromJson(json1,
                        new TypeToken<ArrayList<Item>>(){}.getType());


                //获取新输入的信息
                String Name = name.getText().toString().trim();
                String Account = account.getText().toString().trim();
                String Pass = pass.getText().toString().trim();
                String Note = note.getText().toString().trim();
                String Time = infodata.get(position).getTime();


                if(Note.equals("")){
                    Note = "无";
                }

                Item item = new Item(Name,Account,Pass);
                InfoItem infoItem = new InfoItem(Name,Account,Pass,Time,Note);

                //把新输入的数据修改到infodata和data  然后序列化存入sp
                infodata.set(position,infoItem);
                data.set(position,item);


                String json2 = gson.toJson(infodata);
                String json3 = gson.toJson(data);
                editor.putString("infodata",json2);
                editor.putString("data",json3);

                editor.commit();

//                Intent intent = new Intent();
//                intent.putExtra("InfoItem1",infoItem);

                setResult(RESULT_OK,getIntent().putExtra("InfoItem1",infoItem));
                finish();
            }
        });



        ImageView return1 = (ImageView) findViewById(R.id.return1);//返回键

        return1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }
}
