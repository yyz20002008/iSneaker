package gradle.udacity.com.isneaker;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by James Yang on 10/9/2016.
 * Updated By James Yang on 10/11/2016.
 */



public class Sneaker implements Parcelable {
    public static final String FLAG_Sneaker = "gradle.udacity.com.isneaker.FLAG_Sneaker";
    public static final String ID="id";
    public static final String MODEL="model";
    public static final String NAME="name";
    public static final String RELEASE_DATE="release_date";
    public static final String RELEASE_TIME="release_time";
    public static final String IMAGE_URL="image_url";
    public static final String ONLINE_STORE_LINK="online_store_link";


    public final int id;
    public final String model;
    public final String name;
    public final String release_date;
    public final String release_time;
    public final String image_url;
    public final String online_store_link;

    public Sneaker(int id, String model, String name, String release_date,String release_time,
                 String image_url,String online_store_link ){
        this.id=id;
        this.model=model;
        this.name=name;
        this.release_date=release_date;
        this.release_time=release_time;
        this.image_url=image_url;
        this.online_store_link=online_store_link;

    }

    public Sneaker (Bundle bundle){
        this(
                bundle.getInt(ID),
                bundle.getString(MODEL),
                bundle.getString(NAME),
                bundle.getString(RELEASE_DATE),
                bundle.getString(RELEASE_TIME),
                bundle.getString(IMAGE_URL),
                bundle.getString(ONLINE_STORE_LINK)
        );
    }

    public Bundle toBundle() {
        Bundle bundle = new Bundle();

        bundle.putInt(ID, id);
        bundle.putString(NAME, name);
        bundle.putString(MODEL, model);
        bundle.putString(RELEASE_DATE, release_date);
        bundle.putString(RELEASE_TIME, release_time);
        bundle.putString(IMAGE_URL, image_url);
        bundle.putString(ONLINE_STORE_LINK, online_store_link);


        return bundle;
    }


    public static Sneaker fromJson(JSONObject jsonObject) throws JSONException {
        return new Sneaker(
                jsonObject.getInt(ID),
                jsonObject.getString(MODEL),
                jsonObject.getString(NAME),
                jsonObject.getString(RELEASE_DATE),
                jsonObject.getString(RELEASE_TIME),
                jsonObject.getString(IMAGE_URL),
                jsonObject.getString(ONLINE_STORE_LINK)

        );
    }

    public String getRating(){
        return "Rating";
    }
    //Implement Parcel
    private Sneaker(Parcel in){
        id=in.readInt();
        model=in.readString();
        name=in.readString();
        release_date=in.readString();
        release_time=in.readString();
        image_url=in.readString();
        online_store_link=in.readString();

    }
    public static final Parcelable.Creator<Sneaker> CREATOR = new Parcelable.Creator<Sneaker>() {
        public Sneaker createFromParcel(Parcel in) {
            return new Sneaker(in);
        }

        public Sneaker[] newArray(int size) {
            return new Sneaker[size];
        }
    };

    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(id);
        parcel.writeString(model);
        parcel.writeString(name);
        parcel.writeString(release_date);
        parcel.writeString(release_time);
        parcel.writeString(image_url);
        parcel.writeString(online_store_link);

    }
    public int describeContents() {
        return 0;
    }

}

