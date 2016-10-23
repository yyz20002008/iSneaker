package gradle.udacity.com.isneaker;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * Created by James Yang on 10/7/2016.
 */
public class ImageAdapter extends CursorAdapter implements StickyListHeadersAdapter {

    private final int mHeight;
    private final int mWidth;
    private LayoutInflater inflater;
    private Context mContext;
    private Cursor mCursor;
    public ImageAdapter(Context c, Cursor cursor,int flags) {
        super(c, cursor, flags);
        mContext=c;
        mCursor=cursor;
        mHeight= Math.round(c.getResources().getDimension(R.dimen.poster_height));
        mWidth = Math.round(c.getResources().getDimension(R.dimen.poster_width));
        inflater = LayoutInflater.from(c);
    }



    // The newView method is used to inflate a new view and return it,
    // you don't bind any data to the view at this point.
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return inflater.inflate(R.layout.fragment_main, parent, false);
    }

    // The bindView method is used to bind all data to a given view
    // such as setting the text on a TextView.
    @Override
    public void bindView(View convertView, Context context, Cursor cursor) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(mWidth, mHeight));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView.findViewById(R.id.imageView1);
        }

        int image = mCursor.getInt(cursor.getColumnIndexOrThrow("image_url"));
        //add image to view
        // Populate fields with extracted properties
        Picasso.with(mContext).load(image).into(imageView);//Picasso API



    }
    // create a new ImageView for each item referenced by the Adapter
//    public View getView(int position, View convertView, ViewGroup parent) {
//        ImageView imageView;
//        if (convertView == null) {
//            // if it's not recycled, initialize some attributes
//            imageView = new ImageView(mContext);
//            imageView.setLayoutParams(new GridView.LayoutParams(mWidth, mHeight));
//            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//           imageView.setPadding(8, 8, 8, 8);
//        } else {
//            imageView = (ImageView) convertView;
//        }
//
//        //imageView.setImageResource(mThumbIds[position]); //regular way to retrieve image
//
//        //2.Retrieve Data
//      /*  // A "projection" defines the columns that will be returned for each row
//        String[] mProjection =
//                {
//                        SneakerDBColumns.MODEL,
//                        SneakerDBColumns.NAME,
//                        SneakerDBColumns.RELEASE_DATE,
//                        SneakerDBColumns.RELEASE_TIME,
//                        SneakerDBColumns.IMAGE_URL,
//                        SneakerDBColumns.ONLINE_STORE_LINK
//                };
//
//        // Defines a string to contain the selection clause
//                String mSelectionClause = null;
//
//        // Initializes an array to contain selection arguments
//                String[] mSelectionArgs = {""};
//
//        // Does a query against the table and returns a Cursor object
//                mCursor = mContext.getContentResolver().query(
//                        SneakerProvider.Sneakers.CONTENT_URI,
//                        mProjection,                       // The columns to return for each row
//                        mSelectionClause,                  // Either null, or the word the user entered
//                        mSelectionArgs,                    // Either empty, or the string the user entered
//                        SneakerDBColumns.RELEASE_DATE
//                );
//
//
//
//        // Determine the column index of the column named "word"
//        int index = mCursor.getColumnIndex(SneakerDBColumns.IMAGE_URL);*/
//
//        //Finally, add image to view
//        Picasso.with(mContext).load(mThumbIds[position]).into(imageView);//Picasso API
//        return imageView;
//
//
//    }

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
