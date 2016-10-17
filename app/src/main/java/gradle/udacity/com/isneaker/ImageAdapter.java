package gradle.udacity.com.isneaker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import gradle.udacity.com.isneaker.data.SneakerDBColumns;
import gradle.udacity.com.isneaker.data.SneakerProvider;
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * Created by James Yang on 10/7/2016.
 */
public class ImageAdapter extends BaseAdapter  implements StickyListHeadersAdapter {
    private Context mContext;
    private final int mHeight;
    private final int mWidth;
    private LayoutInflater inflater;
    //private String[] mCountries;

    public ImageAdapter(Context c) {

        mContext = c;
        mHeight= Math.round(mContext.getResources().getDimension(R.dimen.poster_height));
        mWidth = Math.round(mContext.getResources().getDimension(R.dimen.poster_width));
        inflater = LayoutInflater.from(c);
       // mCountries = c.getResources().getStringArray(R.array.countries);
    }

    public int getCount() {
        return mThumbIds.length;
    }

    public Object getItem(int position) {
        return mThumbIds[position];
    }

    public long getItemId(int position) {
        return 0;
    }

    public void insertSneaker(int image_id){

        // Defines a new Uri object that receives the result of the insertion
        Uri mNewUri;

        // Defines an object to contain the new values to insert
        ContentValues mNewValues = new ContentValues();

        /*
         * Sets the values of each column and inserts the word. The arguments to the "put"
         * method are "column name" and "value"
         */

        mNewValues.put(SneakerDBColumns.MODEL, "20161005");
        mNewValues.put(SneakerDBColumns.NAME, "Air_Jordan_1");
        mNewValues.put(SneakerDBColumns.RELEASE_DATE, "Oct,15 2016");
        mNewValues.put(SneakerDBColumns.RELEASE_TIME, "10:00AM");
        mNewValues.put(SneakerDBColumns.IMAGE_URL, image_id);
        mNewValues.put(SneakerDBColumns.ONLINE_STORE_LINK, "www.NIKE.com");

        mNewUri = mContext.getContentResolver().insert(
                SneakerProvider.Sneakers.CONTENT_URI,   // content URI
                mNewValues                          // the values to insert
        );


    }


    Cursor mCursor;
    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(mWidth, mHeight));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
           imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }
        //1. Insert Data
        insertSneaker(mThumbIds[position]);
        //imageView.setImageResource(mThumbIds[position]); //regular way to retrieve image

        //2.Retrieve Data
        // A "projection" defines the columns that will be returned for each row
        String[] mProjection =
                {
                        SneakerDBColumns.MODEL,
                        SneakerDBColumns.NAME,
                        SneakerDBColumns.RELEASE_DATE,
                        SneakerDBColumns.RELEASE_TIME,
                        SneakerDBColumns.IMAGE_URL,
                        SneakerDBColumns.ONLINE_STORE_LINK
                };

        // Defines a string to contain the selection clause
                String mSelectionClause = null;

        // Initializes an array to contain selection arguments
                String[] mSelectionArgs = {""};

        // Does a query against the table and returns a Cursor object
                mCursor = mContext.getContentResolver().query(
                        SneakerProvider.Sneakers.CONTENT_URI,
                        mProjection,                       // The columns to return for each row
                        mSelectionClause,                  // Either null, or the word the user entered
                        mSelectionArgs,                    // Either empty, or the string the user entered
                        SneakerDBColumns.RELEASE_DATE
                );



        // Determine the column index of the column named "word"
        int index = mCursor.getColumnIndex(SneakerDBColumns.IMAGE_URL);

        //Finally, add image to view
        Picasso.with(mContext).load(Integer.parseInt(mCursor.getString(index))).into(imageView);//Picasso API

        /*
         * Only executes if the cursor is valid. The User Dictionary Provider returns null if
         * an internal error occurs. Other providers may throw an Exception instead of returning null.
         */


        return imageView;

     /*   ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.test_list_item_layout, parent, false);
            holder.text = (TextView) convertView.findViewById(R.id.text);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.text.setText(mCountries[position]);

        return convertView;*/
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        HeaderViewHolder holder;
        if (convertView == null) {
            holder = new HeaderViewHolder();
            convertView = inflater.inflate(R.layout.header, parent, false);
            holder.text = (TextView) convertView.findViewById(R.id.text1);
            convertView.setTag(holder);
        } else {
            holder = (HeaderViewHolder) convertView.getTag();
        }
        //set header text as first char in name
        String headerText = "" + mThumbIds[position];
        holder.text.setText(headerText);
        return convertView;
    }

    @Override
    public long getHeaderId(int position) {
        //return the first character of the country as ID because this is what headers are based upon
        return mThumbIds[position];
    }

    class HeaderViewHolder {
        TextView text;
    }

    class ViewHolder {
        TextView text;
    }
    // references to our images
    private Integer[] mThumbIds = {
            R.drawable.aj1sb,
            R.drawable.aj12ovo, R.drawable.aj12wool,
            R.drawable.aj31orange, R.drawable.aj4premium
    };
}
