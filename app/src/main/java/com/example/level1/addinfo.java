package com.example.level1;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by 梅梅 on 2016/2/19.
 */
public class addinfo extends Activity{

//    public List<Item> data = new ArrayList<>();
//    public List<InfoItem> infodata = new ArrayList<>();


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

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //创建存储对象Sharedpreferences  和 编辑对象Editor
                SharedPreferences pref = getSharedPreferences("info",MODE_APPEND);
                SharedPreferences.Editor editor = pref.edit();
                //获取用户输入的信息
                String Name = name.getText().toString().trim();
                String Account = account.getText().toString().trim();
                String Pass = pass.getText().toString().trim();
                String Note = note.getText().toString().trim();
                Calendar c = Calendar.getInstance();//获取日历对象
                int Year = c.get(Calendar.YEAR);
                int Month = c.get(Calendar.MONTH)+1;
                int Day = c.get(Calendar.DAY_OF_MONTH);
                int Hour = c.get(Calendar.HOUR_OF_DAY);
                int Minute = c.get(Calendar.MINUTE);

                String Time = Year+"年"+Month+"月"+Day+"日"+"  "+Hour+":"+Minute;

                if(Note.equals("")){
                    Note = "无";
                }


                //将infodata 从sp中取出并判断是否为空
                Gson gson = new Gson();

                String json = pref.getString("infodata",null);
                String json1 = pref.getString("data",null);

                List<Item> data ;
                List<InfoItem> infodata;
                if(json == null)
                {
                    infodata = new ArrayList<>();
                    data = new ArrayList<>();
                }
                else
                {
                    infodata = gson.fromJson(json,
                            new TypeToken<ArrayList<InfoItem>>(){}.getType());
                    data = gson.fromJson(json1,
                            new TypeToken<ArrayList<Item>>(){}.getType());
                }



                Item item = new Item(Name,Account,Pass);
                InfoItem infoItem = new InfoItem(Name,Account,Pass,Time,Note);

                //把新输入的数据添加到infodata  然后序列化存入sp
                infodata.add(infoItem);
                data.add(item);

                String json2 = gson.toJson(infodata);
                String json3 = gson.toJson(data);
                editor.putString("infodata",json2);
                editor.putString("data",json3);

                editor.commit();
//                System.out.println(pref.getString(Name,""));
//                System.out.println(pref.getString(Name+"Account",""));
//                System.out.println(pref.getString(Name+"Pass",""));
//                System.out.println(pref.getString(Name+"Note",""));
//                System.out.println(pref.getLong(Name+"Time",0));

                Intent intent = new Intent(addinfo.this,MainActivity.class);

                startActivity(intent);
            }
        });




        ImageView return1 = (ImageView) findViewById(R.id.return1);//返回键

        return1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(addinfo.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
