package com.jft.finjutsu;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.lang.String;
import java.util.Arrays;
import android.os.Parcel;
import android.os.Parcelable;
import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ObjectOutputStream;

public class User implements Parcelable{
    private String email_address;
    private String user_name;
    private String password;
    private int totalIncome=0;
    private int balance=0;
    private int totalExpense=0;
    //Store a reference to the Account object so the parcelable constructor can use it
    private Account account;
    private Goal goal;
    private Expense expense;
    private ArrayList<Account> userAccounts = new ArrayList<>();
    //We´re going to store a reference to the goal list array so we can save them in user and rebuild them
    private ArrayList<Goal> userGoals=new ArrayList<>();
    private Account[] accountArray=new Account[userAccounts.size()];
    private ArrayList<Expense> userExpenses= new ArrayList<>();
    //Json serializer variables
    private static final String TAG="User";
    private static final String FILENAME="accounts.json";
    private UserJSONSerializer serializer;
    private static User sUser;
    private Context c;
    //JSON Variables to reconstitute the user
    private String loadedEmail;
    private String loadedName;
    private String loadedPassword;
    private int loadedIncome;
    private int loadedExpense;
    private int loadedBalance;
    private ArrayList<Account> loadedAccounts=new ArrayList<>();
    private ArrayList<Goal> loadedGoals= new ArrayList<>();
    private ArrayList<Expense> loadedExpenses=new ArrayList<>();



    public User(String email_address,String user_name,String password){
        this.email_address=email_address;
        this.user_name=user_name;
        this.password=password;

    }
    //method for the user to create accounts
    public void createAccount(String type,String name, int amount){
        Account new_account= new Account(type,name,amount);
        userAccounts.add(new_account);
        if (new_account.getType().equals("Income")){
            totalIncome=totalIncome+new_account.getAmount();
        }
        else if (new_account.getType().equals("Expense")){
            totalExpense=totalExpense+new_account.getAmount();
        }

    }
    //Adds a goal to goal List
    public void createGoal(String goalName, int goalImageID,int goalSave){
        Goal new_goal= new Goal(goalName,goalImageID,goalSave);
        userGoals.add(new_goal);
        System.out.println("Goal Created");
        System.out.println("User goal list size "+userGoals.size());
    }
    //Adds a expense to expense List
    public void createExpense(String name , String amount,String date){
        Expense new_expense=new Expense(name,amount,date);
        userExpenses.add(new_expense);
        totalExpense=totalExpense+Integer.parseInt(new_expense.getAmount());
        System.out.println("Expense Created!");
        System.out.println("User expense list size "+userExpenses.size());
    }
    //Calculates the balance of all the accounts
    public int calculateBalance (){
        balance=totalIncome-totalExpense;
        return balance;
    }
    //getters
    public String getTotalIncome(){
        return Integer.toString(totalIncome);
    }
    public String getTotalExpense(){
        return Integer.toString(totalExpense);
    }
    public String getBalance(){
        balance=calculateBalance();
        return Integer.toString(balance);
    }
    public String getEmailAddress(){
        return email_address;
    }
    public String getUserName(){
        return user_name;
    }
    public String getPassword(){
        return password;
    }
    public Account[] getAccounts(){
        accountArray=this.convertToArray();
        System.out.println("Accounts accesed");
        System.out.println("accountArray size "+accountArray.length);
        return accountArray;
    }
    public Goal[] getGoalList(){
        return userGoals.toArray(new Goal [userGoals.size()]);
    }
    public Expense[]getExpenseList(){
        return userExpenses.toArray(new Expense [userExpenses.size()]);
    }
    //setters
    public void setName(String name){
        this.user_name=name;
    }
    public void setPassword(String password){
        this.password=password;
    }
    public void setEmailaddress(String email_address){
        this.email_address=email_address;
    }
    public void setTotalIncome(int totalIncome){
        this.totalIncome=totalIncome;
    }
    public void setTotalExpense(int totalExpense){
        this.totalExpense=totalExpense;
    }
    public void setAccounts(Account[] accounts){
        this.userAccounts=new ArrayList<Account>(Arrays.asList(accounts));
    }
    public void setBalance(int balance){
        this.balance=balance;
    }
    //User load methods
    public void loadAccounts(ArrayList<Account> loadAccounts){
        this.userAccounts=loadAccounts;
    }
    public void loadExpenses(ArrayList<Expense> loadExpenses){
        this.userExpenses=loadExpenses;
    }
    public void loadGoals(ArrayList<Goal> loadGoals){
        this.userGoals=loadGoals;
    }
    //Parcelable methods
    //Converts userAccounts to Array for parcelable
    public Account[] convertToArray(){
        accountArray=userAccounts.toArray(new Account[userAccounts.size()]);
        return accountArray;
    }
    public void setSerializer(Context c,String fileName){
        this.serializer=new UserJSONSerializer(c,fileName);
    }

    //Write object values to parcel for storage
    public void writeToParcel(Parcel parcel, int flags){
        parcel.writeString(email_address);
        parcel.writeString(user_name);
        parcel.writeString(password);
        parcel.writeInt(totalIncome);
        parcel.writeInt(balance);
        parcel.writeInt(totalExpense);
        //Write a Parcelable object accounts (accounts also implements the parcelable interface)
        parcel.writeParcelable(account,flags);
        //Write userAccounts ArrayList as a Parcelable writeTypedList
        parcel.writeTypedList(userAccounts);
        //Write a parcelable object goals
        parcel.writeParcelable(goal,flags);
        //Write userGoals ArrayList as a Parcelable writeTypedList
        parcel.writeTypedList(userGoals);
        //Write a parcelable object expense
        parcel.writeParcelable(expense,flags);
        //Write userExpense ArrayList as a parcelable writeTypedList
        parcel.writeTypedList(userExpenses);



    }
    //Parcel constructor
    public User(Parcel parcel){
        this.email_address=parcel.readString();
        this.user_name=parcel.readString();
        this.password=parcel.readString();
        this.totalIncome=parcel.readInt();
        this.balance=parcel.readInt();
        this.totalExpense=parcel.readInt();
        //Finally able to make the parcelable array of objects work with this structure
        //Implement the userAccounts ArrayList as a TypedLis
        // The implementation of TypedList
        //It is very important that the read method and the writeToParcel method follow the same order
        this.account=parcel.readParcelable(Account.class.getClassLoader());
        parcel.readTypedList(userAccounts,Account.CREATOR);
        this.goal=parcel.readParcelable(Goal.class.getClassLoader());
        parcel.readTypedList(userGoals,Goal.CREATOR);
        this.expense=parcel.readParcelable(Expense.class.getClassLoader());
        parcel.readTypedList(userExpenses,Expense.CREATOR);


    }
    //Parcel creator
    public static final Parcelable.Creator<User> CREATOR=new Parcelable.Creator<User>(){
        @Override
        public User createFromParcel(Parcel parcel){
            return new User(parcel);
        }
        @Override
        public User[]newArray(int size){
            return new User[0];
        }

    };
    //Return hashcode of object
    public int describeContents(){
        return hashCode();
    }
    //JSON methods
    public boolean saveAccounts(){
        try{
            this.serializer.saveAccounts(this.getAccounts());
            Log.d(TAG,"Accounts saved to file");
            return true;
        }catch(Exception e){
            Log.e(TAG,"Error saving accounts: ",e);
            return false;
        }
    }
    public boolean saveGoals(){
        try{
            this.serializer.saveGoal(this.getGoalList());
            Log.d(TAG,"Goals saved to file");
            return true;
        }catch(Exception e){
            Log.e(TAG,"Error saving goals: ",e);
            return false;
        }
    }
    public boolean saveExpenses(){
        try{
            this.serializer.saveExpense(this.getExpenseList());
            Log.d(TAG,"Expenses saved to file ");
            return true;
        }catch(Exception e){
            Log.e(TAG,"Error saving expenses: ",e);
            return false;
        }
    }
    public ArrayList<Account> loadAccounts(){
        try {
            loadedAccounts=this.serializer.loadAccounts();
        }catch(Exception e){
            Log.e(TAG,"Error loading accounts: ",e);
        }
        return loadedAccounts;
    }
    public ArrayList<Goal> loadGoals(){
        try{
            loadedGoals=this.serializer.loadGoals();
        }catch(Exception e){
            Log.e(TAG,"Error loading goals: ", e);
        }
        return loadedGoals;
    }
    public ArrayList<Expense>loadExpenses(){
        try{
            loadedExpenses=this.serializer.loadExpenses();
        }catch (Exception e){
            Log.e(TAG,"Error loading expenses: ",e);
        }
        return loadedExpenses;
    }


    }

