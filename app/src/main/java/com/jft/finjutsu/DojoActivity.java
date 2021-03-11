package com.jft.finjutsu;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.ProgressBar;
import android.widget.TextView;



public class DojoActivity extends Activity  {
    User user;
    int fixedExpense=0;
    int variableExpense=0;
    int totalExpense=0;
    Account [] accounts;
    Expense [] expenses;
    int income;
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.dojo_activity);
        System.out.println("Saved Instance State "+savedInstanceState);


        //SavedInstance state is called in case the screen is moved
        if(savedInstanceState!=null){
            user=savedInstanceState.getParcelable("user");
            System.out.println("Block accesed");
            String userName= user.getUserName();
            System.out.println(userName);
            String balance=user.getBalance();
            TextView userDisplay=(TextView) findViewById(R.id.user_name_display);
            userDisplay.setText(userName);
            TextView balanceDisplay=(TextView) findViewById(R.id.balance_display);
            balanceDisplay.setText("Net Balance: "+balance);


        }
        //retrieves user object data
        Intent intent=getIntent();
        if (intent.getExtras()!=null){
            ProgressBar green =(ProgressBar) findViewById(R.id.background_progressbar);
            ProgressBar red=(ProgressBar) findViewById(R.id.stats_progressbar);
            TextView expenseText=(TextView) findViewById(R.id.expenseProportion);
            user =intent.getParcelableExtra("user");
            String userName= user.getUserName();
            String balance=user.getBalance();
            income=Integer.parseInt(user.getTotalIncome());
            if (Integer.parseInt(balance)>0){
                green.setProgress(100);
            }
            TextView userDisplay=(TextView) findViewById(R.id.user_name_display);
            userDisplay.setText(userName);
            TextView balanceDisplay=(TextView) findViewById(R.id.balance_display);
            balanceDisplay.setText(balance);
            accounts=user.getAccounts();
            expenses=user.getExpenseList();
            System.out.println("User total expenses: "+user.getTotalExpense());
            for (int i=0;i<accounts.length;i++){
                if(accounts[i].getType().equals("Expense")){
                    fixedExpense=fixedExpense+accounts[i].getAmount();
                }
            }
            for(int i=0;i<expenses.length;i++){
                variableExpense=variableExpense+Integer.parseInt(expenses[i].getAmount());
            }
            totalExpense=variableExpense+fixedExpense;
            expenseText.setText(Integer.toString(totalExpense)+"/"+Integer.toString(income));
            double expProportion=(double)totalExpense/(double)income;
            int redProgress=(int)(expProportion*100);
            red.setProgress(redProgress);
            balanceDisplay.setText("Net Balance: "+balance);

        }

    }
    public void onClickBudget(View view){
       Intent intent=getIntent();
       user=intent.getParcelableExtra("user");
       Intent budget_intent= new Intent(this,BudgetActivity.class);
       budget_intent.putExtra("user",user);
       startActivity(budget_intent);
    }
    public void onClickGoals(View view){
        Intent intent= new Intent(this,GoalActivity.class);
        //We´re passing the user too , so that the user data is not lost when the activity is changed
        //We can use "user" to put the goal list assay in it and reconstruct the goal list
        //Similar to what we did to the account list 
        intent.putExtra("user",user);
        startActivity(intent);
    }
    public void onClickCalendar(View view){
        Intent intent=new Intent(this,CalendarActivity.class);
        intent.putExtra("user",user);
        startActivity(intent);
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        savedInstanceState.putParcelable("user",user);
        System.out.println("user "+user);
        System.out.println("Saved user");
        System.out.println("User name "+user.getUserName());
        System.out.println("User Balance "+user.getBalance());
        System.out.println(savedInstanceState.getParcelable("user"));

    }
}
