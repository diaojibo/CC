package hk.hku.comp7506.callercheck.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by rocklct on 2017/10/2.
 */

public class Contact implements Parcelable {
    protected Contact(Parcel in) {
        name = in.readString();
        number = in.readString();
    }

    public static final Creator<Contact> CREATOR = new Creator<Contact>() {
        @Override
        public Contact createFromParcel(Parcel in) {
            return new Contact(in);
        }

        @Override
        public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };

    public String getNumber() {
        return number;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.name);
        parcel.writeString(this.number);

    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String number;
    private String name;

    public Contact(String name,String number){
        this.number = number;
        this.name = name;
    }

}
