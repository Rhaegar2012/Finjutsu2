package com.jft.finjutsu;
import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;


public class Goal implements Parcelable {
    private String name;
    private int saveGoal;
    private int imageResourceID;
    private int saveAmount;
    private static final String JSON_NAME="name";
    private static final String JSON_SAVEGOAL="saveGoal";
    private static final String JSON_IMAGE="imageResource";
    private static final String JSON_SAVEAMOUNT="saveAmount";

    //Goal array for ListView
    public static final Goal[] goals={
            new Goal("Wedding",R.drawable.wedding,0),
            new Goal("New car",R.drawable.car,0),
            new Goal("New house",R.drawable.house,0),
            new Goal("Tech",R.drawable.laptop,0),
            new Goal("Travel",R.drawable.travel,0),
            new Goal("Studies",R.drawable.college,0),
            new Goal("Gaming",R.drawable.gaming,0)};
    public Goal(String name,int imageResourceID,int saveGoal){
        this.name=name;
        this.imageResourceID=imageResourceID;
        this.saveGoal=saveGoal;
    }
    public String GetGoalName(){
        return name;
    }
    public int GetSaveGoal(){
        return saveGoal;
    }
    public int GetImageResourceID(){
        return imageResourceID;
    }
    public int GetSaveAmount(){
        return this.saveAmount;
    }
    //toString() is necessary so the ArrayAdapter knows to display the object name variable instead of the object memory location
    //Parcelable Implementation
    public void writeToParcel(Parcel parcel, int flags){
        parcel.writeString(name);
        parcel.writeInt(imageResourceID);
        parcel.writeInt(saveGoal);
        parcel.writeInt(saveAmount);
    }
    //Setters
    public void setSaveGoal(int saveGoal){
        this.saveGoal=saveGoal;
    }
    public void setSaveAmount(int amount){
        this.saveAmount=this.saveAmount+amount;
    }
    public Goal(Parcel parcel){
        name=parcel.readString();
        imageResourceID=parcel.readInt();
        saveGoal=parcel.readInt();
        saveAmount=parcel.readInt();

    }
    public static final Parcelable.Creator<Goal> CREATOR= new Parcelable.Creator<Goal>(){
        @Override
        public Goal createFromParcel(Parcel parcel){
            return new Goal(parcel);
        }
        @Override
        public Goal [] newArray(int size){
            return new Goal[size];
        }
    };
    public int describeContents(){
        return hashCode();
    }
    //JSON methods
    public JSONObject toJSON() throws JSONException{
        JSONObject json= new  JSONObject();
        json.put(JSON_NAME,name);
        json.put(JSON_IMAGE,Integer.toString(imageResourceID));
        json.put(JSON_SAVEGOAL,Integer.toString(saveGoal));
        json.put(JSON_SAVEAMOUNT,Integer.toString(saveAmount));
        return json;
    }
    public Goal(JSONObject json )throws JSONException{
        this.name=json.getString(JSON_NAME);
        this.imageResourceID=Integer.parseInt(json.getString(JSON_IMAGE));
        this.saveAmount=Integer.parseInt(json.getString(JSON_SAVEAMOUNT));
        this.saveGoal=Integer.parseInt(json.getString(JSON_SAVEGOAL));
    }
}
