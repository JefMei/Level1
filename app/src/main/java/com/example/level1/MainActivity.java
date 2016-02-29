package com.example.level1;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private Adapter adapter;
    private ListView mlistview;
    private FloatingActionButton floatingActionButton;
    public List<Item> data = new ArrayList<>();
    public List<InfoItem> infodata = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);

        floatingActionButton = (FloatingActionButton) findViewById(R.id.add);


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,addinfo.class);
                startActivity(intent);
            }
        });

//        Item item = (Item) getIntent().getSerializableExtra("item");
//        InfoItem infoItem = (InfoItem) getIntent().getSerializableExtra("infoItem");

        SharedPreferences pref = getSharedPreferences("info",MODE_APPEND);

        Gson gson = new Gson();
        String json = pref.getString("infodata",null);
        String json1 = pref.getString("data",null);

        infodata = gson.fromJson(json,new TypeToken<ArrayList<InfoItem>>(){}.getType());
        data = gson.fromJson(json1,new TypeToken<ArrayList<Item>>(){}.getType());
        if(data==null){
            data = new ArrayList<>();
            infodata = new ArrayList<>();
        }


        //1、创建适配器
        adapter = new Adapter(this,R.layout.list_item,data);

        //2、加载数据源    数据已经加载好了


        //3、视图加载适配器
        mlistview = (ListView)findViewById(R.id.listview);
        mlistview.setAdapter(adapter);

        mlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
            int position, long id) {
                Intent intent = new Intent(MainActivity.this,info.class);
                intent.putExtra("InfoItem",infodata.get(position));
                intent.putExtra("position",position);
                startActivity(intent);
            }
        });


    }


}
