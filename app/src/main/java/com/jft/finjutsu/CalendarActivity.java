package com.jft.finjutsu;
import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.widget.CalendarView;
import android.widget.TextView;
import android.view.LayoutInflater;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.ListAdapter;
import android.widget.AdapterView;
import android.content.Context;
import android.view.View;
import android.widget.EditText;
import java.util.Calendar;
import android.widget.LinearLayout;

public class CalendarActivity extends Activity {
    User user;
    String budget;
    Context c=this;
    String expenseAmount;
    String expenseName;
    String date;
    Expense[]expenseList;
    int updatedBudget;
    String saveName="expenses.json";

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_layout);
        Intent intent=getIntent();
        user=intent.getParcelableExtra("user");
        final TextView budgetView=(TextView) findViewById(R.id.budget_text);
        CalendarView calendar= findViewById(R.id.Calendar);
        budget=user.getBalance();
        budgetView.setText("Monthly Expense Budget: "+budget);
        expenseList=user.getExpenseList();
        System.out.println("Expense List size "+expenseList.length);
        final LinearLayout expenseDisplay=(LinearLayout)findViewById(R.id.expense_linear_layout);
        if (expenseList.length!=0){
            for (int i=0;i<expenseList.length;i++){
                TextView expense =new TextView(CalendarActivity.this);
                expenseName=expenseList[i].getName();
                expenseAmount=expenseList[i].getAmount();
                date=expenseList[i].getDate();
                expense.setText(date+" "+expenseName+" "+expenseAmount);
                expenseDisplay.addView(expense);
            }
        }
        //Date Click Listener for the calendar view
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int i, int i1, int i2) {
                 final String date=i+"/"+(i1+1)+"/"+i2;
                 LayoutInflater inflater=LayoutInflater.from(c);
                 View dialogView=inflater.inflate(R.layout.expense_popup,null);
                 AlertDialog.Builder dialogBox =new AlertDialog.Builder(c);
                 dialogBox.setView(dialogView);
                 final EditText amountField= (EditText)dialogView.findViewById(R.id.expense_popup);
                 final EditText nameField=(EditText)dialogView.findViewById(R.id.expense_name_popup);
                 System.out.println("Amount field "+amountField);

                 dialogBox
                         .setCancelable(false)
                         .setPositiveButton("Ok",new DialogInterface.OnClickListener(){
                             @Override
                             public void onClick(DialogInterface dialogBox,int id){
                                 expenseAmount=amountField.getText().toString();
                                 expenseName=nameField.getText().toString();
                                 user.createExpense(expenseName,expenseAmount,date);
                                 System.out.println("Expense: "+expenseName);
                                 System.out.println("Amount: "+expenseAmount);
                                 TextView expenseView=new TextView(CalendarActivity.this);
                                 expenseView.setText(date+" "+expenseName+" "+expenseAmount);
                                 expenseDisplay.addView(expenseView);
                                 updatedBudget=Integer.parseInt(budget)-Integer.parseInt(expenseAmount);
                                 budget=Integer.toString(updatedBudget);
                                 budgetView.setText("Monthly expense budget: "+ Integer.toString(updatedBudget));
                                 user.setBalance(Integer.parseInt(budget));

                             }
                         })
                         .setNegativeButton("Cancel",
                                 new DialogInterface.OnClickListener(){
                            @Override
                             public void onClick(DialogInterface dialogBox,int id){
                            dialogBox.cancel();
                    }



                });
                 AlertDialog box =dialogBox.create();
                 box.show();



            }
        });



    }
    public void onPressBack(View view){
        Intent intent = new Intent(this, DojoActivity.class);
        user.setSerializer(c,saveName);
        user.saveExpenses();
        intent.putExtra("user",user);
        startActivity(intent);
    }
}
