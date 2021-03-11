package com.jft.finjutsu;
//TODO
//Implement a method so that the "SAVE" button is only active in the event of an account added in the activity, other wise the program will delete the display
import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.widget.TextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.view.View;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.io.FileOutputStream;

import static com.jft.finjutsu.LoginActivity.USER_PREFS;

public class BudgetActivity extends Activity {
    //counter test
    String account_name;
    String account_type;
    String account_amount;
    String fileName="user";
    Account[] userAccounts;
    int userTotalExpense;
    int userTotalIncome;
    int userBalance;
    //Placeholder Object
    User user=null;
    //activity context, this is used for the dialog box and requires further reading
    final Context c=this;
    String saveName="accounts.json";


    //TextView totalExpense=(TextView)findViewById(R.id.expense_field);
    //TextView Balance=(TextView) findViewById(R.id.balance_field);
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.budget_activity);
        Intent intent=getIntent();
        this.user=intent.getParcelableExtra("user");
        //Stores text view objects to update screen
        TextView incomeDisplay= (TextView) findViewById(R.id.income_field);
        TextView expenseDisplay=(TextView) findViewById(R.id.expense_field);
        TextView balanceDisplay=(TextView) findViewById(R.id.balance_field);
        LinearLayout nestedLayout=(LinearLayout) findViewById(R.id.nested_layout);
        //Restores state when the activity is destroyed
        incomeDisplay.setText(user.getTotalIncome());
        expenseDisplay.setText(user.getTotalExpense());
        balanceDisplay.setText(user.getBalance());
        userAccounts=user.getAccounts();
        //Print the user Account list , if the userAccounts contains objects
        if (userAccounts.length>0){
            for (int i=0;i<userAccounts.length;i++){
                account_name=userAccounts[i].getName();
                account_type=userAccounts[i].getType();
                account_amount=Integer.toString(userAccounts[i].getAmount());
                TextView accountDisplay= new TextView(BudgetActivity.this);
                accountDisplay.setText(String.format("%s-%s-%s",account_type,account_name,account_amount));
                nestedLayout.addView(accountDisplay);
            }
        }





    }
    //Saves instance status
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        User saved_user=this.updateUser();
        Account[]accountArray=saved_user.getAccounts();
        savedInstanceState.putParcelableArray("userAccounts",accountArray);
    }
    public void onAddAccount(View view){
        Intent intent=getIntent();
        final User user=intent.getParcelableExtra("user");
        final TextView totalIncome=(TextView) findViewById(R.id.income_field);
        final TextView totalExpense=(TextView)findViewById(R.id.expense_field);
        final TextView Balance=(TextView) findViewById(R.id.balance_field);
        //Creates a layoutInflater object to store the dialog_box(?) uses the same context as the activity
        LayoutInflater layoutInflater =LayoutInflater.from(c);
        //assigns the dialog box layout to a layout inflater object , this requires further reading
        View dialogView=layoutInflater.inflate(R.layout.dialog_create_account,null);
        //Creates the dialog box object
        AlertDialog.Builder dialogBox=new AlertDialog.Builder(c);
        //Sets the layout of the dialogBox to dialog view
        dialogBox.setView(dialogView);
        //Finds and creates objects from the dialogBox views
        final Spinner dialogSpinner=(Spinner) dialogView.findViewById(R.id.sp_account_type);
        final EditText dialogAccountName=(EditText) dialogView.findViewById(R.id.et_account_name);
        final EditText dialogAccountAmount=(EditText) dialogView.findViewById(R.id.et_account_amount);
       //displays the dialog box and obtains the data from the view objects
        dialogBox
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogBox, int id) {
                        account_type=dialogSpinner.getSelectedItem().toString();
                        account_name=dialogAccountName.getText().toString();
                        account_amount=dialogAccountAmount.getText().toString();
                        //Displays the newly created account in the nested layout

                        user.createAccount(account_type,account_name,Integer.parseInt(account_amount));
                        user.calculateBalance();
                        //Updates state variables for user
                        userAccounts=user.getAccounts();
                        userTotalExpense=Integer.parseInt(user.getTotalExpense());
                        userTotalIncome=Integer.parseInt(user.getTotalIncome());
                        userBalance=Integer.parseInt(user.getBalance());

                        //Displays Information Summary
                        totalIncome.setText(user.getTotalIncome());
                        totalExpense.setText(user.getTotalExpense());
                        Balance.setText(user.getBalance());
                        TextView textView=new TextView(BudgetActivity.this);
                        textView.setText(String.format("%s %s :%s",account_type,account_name,account_amount));
                        LinearLayout relative=(LinearLayout) findViewById(R.id.nested_layout);
                        relative.addView(textView);





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


    }
    private User updateUser(){
        Intent user_intent=getIntent();
        user=user_intent.getParcelableExtra("user");
        user.setTotalIncome(Integer.parseInt(user.getTotalIncome()));
        user.setTotalExpense(Integer.parseInt(user.getTotalExpense()));
        user.setBalance(Integer.parseInt(user.getBalance()));
        user.setAccounts(userAccounts);

        return user;
    }
    //TODO: Solve the bug when onSave is pressed without adding an account and the user balance is wiped
    public void onSave(View view){
        User updated_user=this.updateUser();
        Intent dojo_intent= new Intent(this,DojoActivity.class);
        dojo_intent.putExtra("user",updated_user);
        System.out.println(" hello user "+user);
        user.setSerializer(c,saveName);
        user.saveAccounts();
        startActivity(dojo_intent);
        SharedPreferences.Editor editor=getSharedPreferences(USER_PREFS,MODE_PRIVATE).edit();
        editor.putInt("income",userTotalIncome);
        editor.putInt("expense",userTotalExpense);
        editor.putInt("balance",userBalance);
        editor.apply();
    }


}
