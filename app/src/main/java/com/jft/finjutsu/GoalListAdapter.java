package com.jft.finjutsu;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


public class GoalListAdapter extends BaseAdapter {
    Context context;
    private final String[] goalNames;
    private final String[] goalAmounts;
    private final int[] imageResources;
    private final int[] saveAmounts;

    public GoalListAdapter (Context context,String[]goalNames,String[]goalAmounts,int[]imageResources,int[]saveAmounts){
        this.context=context;
        this.goalNames=goalNames;
        this.goalAmounts=goalAmounts;
        this.imageResources=imageResources;
        this.saveAmounts=saveAmounts;
    }
    @Override
    public int getCount(){
        return goalNames.length;
    }
    @Override
    public Object getItem(int i){
        return i;
    }
    @Override
    public long getItemId(int i){
        return i;
    }
    @NonNull
    @Override
    public View getView(int position,@Nullable View convertView,@NonNull ViewGroup parent){
        ViewHolder viewHolder;
        final View result;
        if(convertView==null){
            viewHolder= new ViewHolder();
            LayoutInflater inflater=LayoutInflater.from(context);
            convertView=inflater.inflate(R.layout.added_goal,parent,false);
            viewHolder.goalText=(TextView)convertView.findViewById(R.id.added_goal_text);
            viewHolder.goalSave=(TextView)convertView.findViewById(R.id.goal_save_tag);
            viewHolder.imageId=(ImageView)convertView.findViewById(R.id.added_goal_icon);
            viewHolder.progressBar=(ProgressBar) convertView.findViewById(R.id.progress_bar);
            result=convertView;
            convertView.setTag(viewHolder);

        }else{
            viewHolder= (ViewHolder)convertView.getTag();

        }
        viewHolder.goalText.setText(goalNames[position]);
        viewHolder.goalSave.setText(goalAmounts[position]);
        viewHolder.imageId.setImageResource(imageResources[position]);
        viewHolder.progressBar.setProgress(saveAmounts[position]);
        return convertView;

    }

    private class ViewHolder {
        TextView goalText;
        TextView goalSave;
        ImageView imageId;
        ProgressBar progressBar;
    }



}
