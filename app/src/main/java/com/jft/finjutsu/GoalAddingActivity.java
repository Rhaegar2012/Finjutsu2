package com.jft.finjutsu;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.AdapterView;
import android.content.Intent;
import android.view.View;
import android.content.Context;

public class GoalAddingActivity extends Activity {
    String saveGoal="0";
    ListView Lview;
    User user;
    Context c=this;
    CustomListAdapter LAdapter;
    final Goal[] goalsArray = Goal.goals;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Intent intent=getIntent();
        user= intent.getParcelableExtra("user");
        setContentView(R.layout.goal_adding_activity);
        int [] images =new int[goalsArray.length];
        String [] text=new String [goalsArray.length];
        for (int i=0;i<goalsArray.length;i++){
            images[i]=goalsArray[i].GetImageResourceID();
            text[i]=goalsArray[i].GetGoalName();
        }
        //Creates a list view object
        Lview=(ListView)findViewById(R.id.list);
        //Used the CustomListAdapter by passing the context and the 2 data arrays to be used
        LAdapter= new CustomListAdapter (this,text,images);
        //The setAdapter method calls getView method?
        Lview.setAdapter(LAdapter);
        //Adding the onClickListener
        //onClickListener is an extension of AdapterView
        //First we implement Listener obkect which is going to impelement the onItemClick method
        //The onItemClick method thats a list view, a position , a view and an id as parameters
        //These parameters are passed when the user clicks a list item

        AdapterView.OnItemClickListener itemCLickListener=
                new AdapterView.OnItemClickListener(){
            public void onItemClick (AdapterView<?> listView,
                                     View v,
                                     final int position,
                                     long id){

                final Intent intent =new Intent(GoalAddingActivity.this,GoalActivity.class);
                LayoutInflater layoutInflater =LayoutInflater.from(c);
                //assigns the dialog box layout to a layout inflater object , this requires further reading
                View dialogView=layoutInflater.inflate(R.layout.goal_save_pop_up,null);
                //Creates the dialog box object
                AlertDialog.Builder dialogBox=new AlertDialog.Builder(c);
                //Sets the layout of the dialogBox to dialog view
                dialogBox.setView(dialogView);
                //Finds and creates objects from the dialogBox views
                final EditText saveGoalDialog=(EditText) dialogView.findViewById(R.id.popup_save_amount);
                //displays the dialog box and obtains the data from the view objects
                dialogBox
                        .setCancelable(false)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogBox, int id) {
                                saveGoal=saveGoalDialog.getText().toString();
                                int selectedImage = goalsArray[position].GetImageResourceID();
                                String selectedText=goalsArray[position].GetGoalName();
                                user.createGoal(selectedText,selectedImage,Integer.parseInt(saveGoal));
                                intent.putExtra("user",user);
                                intent.putExtra("Image",selectedImage);
                                intent.putExtra("Text",selectedText);
                                startActivity(intent);

                            }
                        })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogBox, int id) {
                                        dialogBox.cancel();
                                    }
                                });
                //creates the dialog box

                AlertDialog inputBox=dialogBox.create();
                inputBox.show();

                System.out.println("Save goal" +saveGoal);

            }
                };

        //The ot Item click listener is added to the ListView object form the layout
        ListView listView=(ListView) findViewById(R.id.list);
        //By adding the Listener to the listview , we pass the parameters necessary when the onItemClick method is called
        listView.setOnItemClickListener(itemCLickListener);
    }


}
