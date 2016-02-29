package com.example.level1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 梅梅 on 2016/2/23.
 */
public class Adapter extends ArrayAdapter<Item>{

    private int resourceId;

    public Adapter(Context context, int resource, List<Item> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        Item item = getItem(position);
        View view ;
        ViewHodler viewHodler;
        if(convertView == null){
            view = LayoutInflater.from(getContext())
                    .inflate(R.layout.list_item,null);
            viewHodler = new ViewHodler();
            viewHodler.Name = (TextView) view.findViewById(R.id.info1);
            viewHodler.Account = (TextView) view.findViewById(R.id.info2);
            viewHodler.Pass = (TextView) view.findViewById(R.id.info3);
            viewHodler.Name.setText(item.getName());
            viewHodler.Account.setText(item.getAccount());
            viewHodler.Pass.setText(item.getPass());
            view.setTag(viewHodler);  //将ViewHolder存储在view中
        }
        else {
            view  = convertView;
            viewHodler = (ViewHodler) convertView.getTag();
        }

        return view;
    }
}

class ViewHodler{
    TextView Name;
    TextView Account;
    TextView Pass;
}

class Item implements Serializable{ //序列化
    private String Name;
    private String Account;
    private String Pass;

    public Item(String Name,String Account,String Pass){
        this.Name = Name;
        this.Account = Account;
        this.Pass = Pass;
    }
    public String getName(){
        return Name;
    }
    public String getAccount(){
        return Account;
    }
    public String getPass(){
        return Pass;
    }
}

class InfoItem implements Serializable{
    private String Name;
    private String Account;
    private String Pass;
    private String Time;
    private String Note;

    public InfoItem(String Name,String Account,String Pass,String Time,String Note){
        this.Name = Name;
        this.Account = Account;
        this.Pass = Pass;
        this.Time = Time;
        this.Note = Note;
    }
    public String getName(){
        return Name;
    }
    public String getAccount(){
        return Account;
    }
    public String getPass(){
        return Pass;
    }
    public String getTime(){
        return Time;
    }
    public String getNote(){
        return Note;
    }
}



