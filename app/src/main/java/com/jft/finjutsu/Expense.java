package com.jft.finjutsu;
import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;


public class Expense implements Parcelable {
    private String name;
    private String amount;
    private String date;
    private static final String JSON_NAME="name";
    private static final String JSON_AMOUNT="Amount";
    private static final String JSON_DATE="date";

    public Expense (String name, String amount, String date){
        this.name=name;
        this.amount=amount;
        this.date=date;
    }
    public String getName(){
        return name;
    }
    public String getAmount(){
        return amount;
    }
    public String getDate(){
        return date;
    }
    //Parcelable implamentation
    public void writeToParcel(Parcel parcel, int flags){
        parcel.writeString(name);
        parcel.writeString(amount);
        parcel.writeString(date);
    }
    public Expense(Parcel parcel){
        name=parcel.readString();
        amount=parcel.readString();
        date=parcel.readString();
    }
    public static final Parcelable.Creator<Expense> CREATOR= new Parcelable.Creator<Expense>(){
        @Override
        public Expense createFromParcel(Parcel parcel){
            return new Expense(parcel);
        }
        public Expense[]newArray(int size ){
            return new Expense[size];
        }
    };
    public int describeContents(){
        return hashCode();
    }
    //JSON Methods
    public JSONObject toJSON()throws JSONException{
        JSONObject json= new JSONObject();
        json.put(JSON_NAME,name);
        json.put(JSON_AMOUNT,amount);
        json.put(JSON_DATE,date);
        return json;
    }
    public Expense(JSONObject json) throws JSONException{
        this.name= json.getString(JSON_NAME);
        this.amount=json.getString(JSON_AMOUNT);
        this.date=json.getString(JSON_DATE);
    }
}
