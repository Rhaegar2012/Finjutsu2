package com.jft.finjutsu;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.ListView;
import android.content.Context;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.ProgressBar;
import android.view.LayoutInflater;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.EditText;
public class GoalActivity extends Activity {
    ListView Lview;
    GoalListAdapter LAdapter;
    Context c=this;
    User user;
    int saveDeposit;
    int saveTarget;
    float progress;
    String saveName="goals.json";
    @Override
    protected void onCreate(Bundle onSavedInstance){
        super.onCreate(onSavedInstance);
        setContentView(R.layout.goal_activity);
        Intent goalIntent=getIntent();
        user=goalIntent.getParcelableExtra("user");
        final Goal[] goalArray=user.getGoalList();

        if (goalArray!=null){
            String [] goalNames= new String[goalArray.length];
            String [] goalAmounts= new String[goalArray.length];
            int[]imageResource= new int[goalArray.length];
            int[]saveProgress=new int[goalArray.length];
            for (int i=0;i<goalArray.length;i++){
                goalNames[i]=goalArray[i].GetGoalName();
                goalAmounts[i]=Integer.toString(goalArray[i].GetSaveGoal());
                imageResource[i]=goalArray[i].GetImageResourceID();
                saveProgress[i]=goalArray[i].GetSaveAmount();
            }
            Lview=(ListView) findViewById(R.id.goal_list_view);
            LAdapter= new GoalListAdapter(c,goalNames,goalAmounts,imageResource,saveProgress);
            Lview.setAdapter(LAdapter);

            AdapterView.OnItemClickListener itemClickListener=
                    new AdapterView.OnItemClickListener(){
                public void onItemClick(AdapterView<?> ListView,
                                        View v,
                                        final int position,
                                        long id){
                    //Dialog text box View
                    LayoutInflater inflater=LayoutInflater.from(c);
                    View dialogView=inflater.inflate(R.layout.goal_save_pop_up,null);
                    AlertDialog.Builder dialogBox=new AlertDialog.Builder(c);
                    dialogBox.setView(dialogView);
                    final EditText saveGoal=(EditText) dialogView.findViewById(R.id.popup_save_amount);
                    final TextView saveAmount=(TextView) v.findViewById(R.id.goal_save_tag);
                    saveTarget=Integer.parseInt(saveAmount.getText().toString());
                    dialogBox
                            .setCancelable(false)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogBox, int id) {
                                    saveDeposit=Integer.parseInt(saveGoal.getText().toString());
                                    System.out.println("Save Deposit" +saveDeposit);
                                    System.out.println("Save Target "+saveTarget);
                                    progress=((float)saveDeposit*100/saveTarget);
                                    if (progress<1){
                                        progress=1;
                                    }
                                    goalArray[position].setSaveAmount((int)progress);
                                    System.out.println("Selected goal "+goalArray[position].GetGoalName());
                                    System.out.println("Progress "+ progress);
                                    System.out.println("intProgress "+(int)progress);
                                    System.out.println("Total Progress "+goalArray[position].GetSaveAmount());
                                    recreate();



                                }
                            })
                            .setNegativeButton("Cancel",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogBox, int id) {
                                            dialogBox.cancel();

                                        }
                                    });
                        AlertDialog box=dialogBox.create();
                        box.show();
                        ProgressBar saveProgress=(ProgressBar) v.findViewById(R.id.progress_bar);
                        saveProgress.setProgress(goalArray[position].GetSaveAmount());

                    }

                };
                Lview.setOnItemClickListener(itemClickListener);
                    }


        }
    public void onClickAddGoal(View view){
        Intent intent =new Intent(this,GoalAddingActivity.class);
        intent.putExtra("user",user);
        startActivity(intent);
    }
    public void onClickBack(View view){
        Intent intent=new Intent(this , DojoActivity.class);
        user.setSerializer(c,saveName);
        user.saveGoals();
        intent.putExtra("user",user);
        startActivity(intent);

    }

    }







