package com.jft.finjutsu;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

//Customo adapter for images and text
public class CustomListAdapter extends BaseAdapter {
    //State variables
    Context context;//Will store activity context
    private final String []values;//A list to hold the text Values
    private final int[]images;//A list to hold the image Values
    //Constructor,no need to pass the superclass constructor
    public CustomListAdapter(Context context, String [] values, int[] images){
        this.context=context;
        this.values=values;
        this.images=images;
    }
    //Unnecessary method
    @Override
    public int getCount(){
        return values.length;
    }
    //Unnecessary method
    @Override
    public Object getItem(int i){
        return i;
    }
    //Unnecessary method
    public long getItemId(int i){
        return i;
    }
    @NonNull
    @Override
    //This is the customizable ListView
    //Get view method is what the ListView is going to use to construct the dynamic ListView
    //Position is the position in the list
    //ConvertView is the resource xml file that is used to create the custom array adapter
    //The parent is the layout currently used by the adatper
    public View getView(int position, @Nullable View convertView,@NonNull ViewGroup parent){
        ViewHolder viewHolder;
        final View result;
        if (convertView==null){
            viewHolder =new ViewHolder();
            //Instantiates a layout inflater from the activity context
            //Layout inflater is essentially an xml layout so it needs a context as a constructor
            //In this case the context is passed when the CustomListAdapter class is created
            LayoutInflater inflater= LayoutInflater.from(context);
            //The convert view populates the Layout inflater with the predefined layout that is going to be
            //used for the list
            convertView=inflater.inflate(R.layout.goal_list_layout,parent,false);
            //Creates View objects from the XML resource file to be populated from the array data
            viewHolder.txtName=(TextView) convertView.findViewById(R.id.image_tag);
            viewHolder.icon=(ImageView)  convertView.findViewById(R.id.goal_icon);
            result=convertView;
            convertView.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder)convertView.getTag();
            result=convertView;
        }
        //Populates the View elements previously created with the date from the  arrays
        viewHolder.txtName.setText(values[position]);
        viewHolder.icon.setImageResource(images[position]);
        return convertView;
    }

    private static class ViewHolder{
        TextView txtName;
        ImageView icon;
    }

}
