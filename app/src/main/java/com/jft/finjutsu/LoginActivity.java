package com.jft.finjutsu;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.content.Intent;
import android.content.Context;
import java.util.ArrayList;
import android.content.SharedPreferences;

public class LoginActivity extends Activity {
    //Variables
    private String user_name;
    private String password;
    private String email;
    private Context context=this;
    private String saveName="file.json";
    private int income;
    private int expense;
    private int balance;
    public static final String USER_PREFS="USER_PREFS";
    private int userVersion=0;
    User loadUser=new User("email","name","password");

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        ArrayList<Account> loadedAccounts;
        ArrayList<Goal> loadedGoals;
        ArrayList<Expense> loadedExpenses;
        setContentView(R.layout.login_activity);
        loadUser.setSerializer(context,"accounts.json");
        loadedAccounts=loadUser.loadAccounts();
        loadUser.setSerializer(context,"expenses.json");
        loadedExpenses=loadUser.loadExpenses();
        loadUser.setSerializer(context,"goals.json");
        loadedGoals=loadUser.loadGoals();
        //Loads user primitive data
        SharedPreferences primitives =getSharedPreferences("USER_PREFS",MODE_PRIVATE);
        user_name=primitives.getString("user_name","no name defined");
        password=primitives.getString("password","no password defined");
        email=primitives.getString("email","no email defined");
        userVersion=primitives.getInt("User Version",0);
        income=primitives.getInt("income",0);
        expense=primitives.getInt("expense",0);
        balance=primitives.getInt("balance",0);
        //Assigning values to user
        loadUser.setName(user_name);
        loadUser.setPassword(password);
        loadUser.setEmailaddress(email);
        loadUser.loadAccounts(loadedAccounts);
        loadUser.loadExpenses(loadedExpenses);
        loadUser.loadGoals(loadedGoals);
        loadUser.setTotalIncome(income);
        loadUser.setTotalExpense(expense);
        loadUser.setBalance(balance);
        //Showing primitives in the EditText fields
        EditText nameField=(EditText)findViewById(R.id.user_name_field);
        EditText passwordField=(EditText) findViewById(R.id.password_field);
        EditText emailField=(EditText) findViewById(R.id.email_field);
        System.out.println("User Version "+ userVersion);
        if (userVersion>0){
            nameField.setText(user_name);
            passwordField.setText(password);
            emailField.setText(email);
        }




    }
    public void onLogin(View view){
        EditText user =(EditText) findViewById(R.id.user_name_field);
        EditText pass=(EditText) findViewById(R.id.password_field);
        EditText mail=(EditText) findViewById(R.id.email_field);
        String userName=loadUser.getUserName();
        String password=loadUser.getPassword();
        if(userName.equals(user.getText().toString())&& password.equals(pass.getText().toString())){
            Intent intent =new Intent(this,DojoActivity.class);
            intent.putExtra("user",loadUser);
            startActivity(intent);

        }else{
            System.out.println("Wrong password or user name");
        }


    }
    public void onSignUp(View view){
        EditText user=(EditText) findViewById(R.id.user_name_field);
        EditText pass=(EditText) findViewById(R.id.password_field);
        EditText mail=(EditText) findViewById(R.id.email_field);
        user_name=user.getText().toString();
        password=pass.getText().toString();
        email=mail.getText().toString();
        //Uses SharedPreferences to store user primititve data
        SharedPreferences.Editor editor=getSharedPreferences(USER_PREFS,MODE_PRIVATE).edit();
        editor.putString("user_name",user_name);
        editor.putString("password",password);
        editor.putString("email",email);
        editor.apply();
        User createdUser= new User(email,user_name,password);
        if (userVersion>0){
            Intent intent = new Intent(this,DojoActivity.class);
            intent.putExtra("user",loadUser);
            startActivity(intent);

        }else{
            Intent intent = new Intent(this,DojoActivity.class);
            intent.putExtra("user",createdUser);
            userVersion=userVersion+1;
            editor.putInt("User Version",userVersion);
            editor.apply();
            startActivity(intent);
        }





    }

}
