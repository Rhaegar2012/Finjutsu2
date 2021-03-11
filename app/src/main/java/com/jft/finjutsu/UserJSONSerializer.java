package com.jft.finjutsu;
import android.content.Context;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

public class UserJSONSerializer extends Object {
    private Context context;
    private String  fileName;
    public UserJSONSerializer(Context c,String fileName){
        this.context=c;
        this.fileName=fileName;
    }
    //Saves AccountsArrayList
    public void saveAccounts(Account[]accountArray)
        throws JSONException, IOException {
        //Puts account objects into a JSON array
        JSONArray array = new JSONArray();
        for (int i=0;i<accountArray.length;i++){
            array.put(accountArray[i].toJSON());
            System.out.println("JSON Array updated: "+array.length());
        }
        Writer writer=null;
        try{
            OutputStream out=context.openFileOutput(fileName,Context.MODE_PRIVATE);
            writer=new OutputStreamWriter(out);
            writer.write(array.toString());
            System.out.println("Accounts Saved");
        }finally{
            if(writer!=null){
                writer.close();
            }
        }

    }
    //Saves Expense  ArrayList
    public void saveExpense(Expense[] expenseArray)
    throws JSONException, IOException{
        JSONArray array=new JSONArray();
        for (int i=0;i<expenseArray.length;i++){
            array.put(expenseArray[i].toJSON());
        }
        Writer writer=null;
        try{
            OutputStream out=context.openFileOutput(fileName,Context.MODE_PRIVATE);
            writer=new OutputStreamWriter(out);
            writer.write(array.toString());

        }finally{
            if(writer!=null){
                writer.close();
            }
        }

    }
    //Saves Goal ArrayList
    public void saveGoal(Goal[] goalArray)
    throws JSONException,IOException{
        JSONArray array= new JSONArray();
        for (int i=0;i<goalArray.length;i++){
            array.put(goalArray[i].toJSON());
        }
        Writer writer=null;
        try{
            OutputStream out=context.openFileOutput(fileName,Context.MODE_PRIVATE);
            writer= new OutputStreamWriter(out);
            writer.write(array.toString());

        }finally{
            if(writer!=null){
                writer.close();
            }
        }
    }
    // Load methods
    //Loads Account arrayList
    public ArrayList<Account> loadAccounts() throws IOException, JSONException{
        ArrayList<Account> loadedAccounts= new ArrayList<Account>();
        BufferedReader reader= null;
        try{
            //Open and read the file into a StringBuilder
            InputStream in= context.openFileInput(fileName);
            reader=new BufferedReader(new InputStreamReader(in));
            StringBuilder jsonString=new StringBuilder();
            String line= null;
            while ((line=reader.readLine())!=null){
                jsonString.append(line);
            }
            //Parse JSON string to an array using JSONTokener
            JSONArray array=(JSONArray) new JSONTokener(jsonString.toString()).nextValue();
            //build the array of accounts from JSONObjects
            for (int i=0;i<array.length();i++){
                loadedAccounts.add(new Account(array.getJSONObject(i)));
                System.out.println("Loading JSON objects");
            }
        }catch(FileNotFoundException e){
          //Occurs when starting fresh
        }finally{
            if (reader !=null){
                reader.close();
            }

        }
        for (Account a :loadedAccounts){
             System.out.println("Account Names: "+a.getName());
        }
        return loadedAccounts;
    }
    public ArrayList<Goal> loadGoals() throws IOException, JSONException {
        ArrayList<Goal> loadedGoals = new ArrayList<>();
        BufferedReader reader = null;
        try {
            InputStream in = context.openFileInput(fileName);
            reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder jsonString = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                jsonString.append(line);
            }
            JSONArray array = (JSONArray) new JSONTokener(jsonString.toString()).nextValue();
            //Build the array of accounts from JSONObjects
            for (int i = 0; i < array.length(); i++) {
                loadedGoals.add(new Goal(array.getJSONObject(i)));

            }

        } catch (FileNotFoundException e) {

        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        for (Goal g : loadedGoals) {
            System.out.println("Goal names: " + g.GetGoalName());
        }
        return loadedGoals;
    }
    public ArrayList<Expense> loadExpenses ()throws IOException,JSONException{
        ArrayList <Expense> loadedExpenses= new ArrayList<>();
        BufferedReader reader=null;
        try{
            InputStream in=context.openFileInput(fileName);
            reader=new BufferedReader(new InputStreamReader(in));
            StringBuilder jsonString =new StringBuilder();
            String line=null;
            while((line=reader.readLine())!=null){
                jsonString.append(line);
            }
            JSONArray array=(JSONArray) new JSONTokener(jsonString.toString()).nextValue();
            //Build the array of accounts from JSONObJECTS
            for(int i=0; i<array.length();i++){
                loadedExpenses.add(new Expense(array.getJSONObject(i)));

            }
        }catch (FileNotFoundException e){

        }finally{
            if (reader !=null){
                reader.close();
            }
        }
        for (Expense e:loadedExpenses){
            System.out.println("Expense names: "+e.getName());
        }
        return loadedExpenses;
    }
}
