package com.jft.finjutsu;
import android.os.Parcel;
import android.os.Parcelable;
import org.json.JSONException;
import org.json.JSONObject;

public class Account implements Parcelable {
    private String type;
    private String name;
    private int amount;
    private static final String JSON_TYPE="type";
    private static final String JSON_NAME="name";
    private static final String JSON_AMOUNT="amount";
    public Account(String type, String name, int amount){
        this.type=type;
        this.name=name;
        this.amount=amount;
    }
    public String getType(){
        return type;
    }
    public String getName(){
        return name;
    }
    public int getAmount(){
        return amount;
    }
    //Parcelable methods
    public void writeToParcel(Parcel dest, int flags){
        dest.writeString(type);
        dest.writeString(name);
        dest.writeInt(amount);

    }
    //Parcelable constructor
    public Account(Parcel parcel){
        type=parcel.readString();
        name=parcel.readString();
        amount=parcel.readInt();

    }
    public static final Parcelable.Creator<Account> CREATOR= new Parcelable.Creator<Account>(){
        @Override
        public Account createFromParcel(Parcel parcel){
            return new Account(parcel);
        }
        @Override
        public Account [] newArray(int size){
            return new Account[size];
        }
    };
    public int describeContents(){
        return hashCode();
    }
    //JSON methods
    public JSONObject toJSON() throws JSONException {
        JSONObject json=new JSONObject();
        json.put(JSON_NAME,name);
        json.put(JSON_AMOUNT,Integer.toString(amount));
        json.put(JSON_TYPE,type);
        return json;

    }
    //Constructor for JSON account objects
    public Account(JSONObject json) throws JSONException{
        this.name=json.getString(JSON_NAME);
        this.type=json.getString(JSON_TYPE);
        this.amount=Integer.parseInt(json.getString(JSON_AMOUNT));

    }

}
