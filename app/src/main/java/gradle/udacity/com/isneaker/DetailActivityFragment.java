package gradle.udacity.com.isneaker;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import gradle.udacity.com.isneaker.data.SneakerDBColumns;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailActivityFragment extends Fragment {

    private static final String LOG_TAG = DetailActivityFragment.class.getSimpleName();
    static final String DETAIL_URI = "URI";

    public DetailActivityFragment() {
    }
    private View rootView;
    private ImageView mImageView;
    private TextView mSneakerName;
    private TextView mReleaseDate;
    private TextView mReleaseTime;
    private TextView mReleaseStore;
    Uri mUri;;

    private Context mContext;
    // Define the columns to retrieve
    String[] projectionFields = new String[] {SneakerDBColumns._ID,
            SneakerDBColumns.IMAGE_URL,
            SneakerDBColumns.RELEASE_TIME,
            SneakerDBColumns.RELEASE_DATE,
            SneakerDBColumns.ONLINE_STORE_LINK,
            SneakerDBColumns.NAME,
            SneakerDBColumns.MODEL};

    // Defines the asynchronous callback for the contacts data loader
    private LoaderManager.LoaderCallbacks<Cursor> contactsLoader =
            new LoaderManager.LoaderCallbacks<Cursor>() {
                // Create and return the actual cursor loader for the contacts data
                @Override
                public Loader<Cursor> onCreateLoader(int id, Bundle args) {
                    Log.v(LOG_TAG, "In onCreateLoader");

                    if(null!=mUri) {
                        // Construct the loader
                        CursorLoader cursorLoader = new CursorLoader(getActivity(),
                                mUri, // URI
                                projectionFields, // projection fields
                                null, // the selection criteria
                                null, // the selection args
                                SneakerDBColumns.RELEASE_DATE // the sort order

                        );
                        return cursorLoader;
                    }

                    return null;
                }

                // When the system finishes retrieving the Cursor through the CursorLoader,
                // a call to the onLoadFinished() method takes place.
                @Override
                public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
                    cursor.moveToFirst();
                    mSneakerName.setText(cursor.getString(cursor.getColumnIndexOrThrow("name")));
                    mReleaseDate.setText(cursor.getString(cursor.getColumnIndexOrThrow("release_date")));
                    mReleaseTime.setText(cursor.getString(cursor.getColumnIndexOrThrow("release_time")));
                    mReleaseStore.setText(cursor.getString(cursor.getColumnIndexOrThrow("online_store_link")));
                    int image = cursor.getInt(cursor.getColumnIndex(SneakerDBColumns.IMAGE_URL));
                    //add image to view
                    Picasso.with(mContext).load(image).into(mImageView);//Picasso API

                }

                // This method is triggered when the loader is being reset
                // and the loader data is no longer available. Called if the data
                // in the provider changes and the Cursor becomes stale.
                @Override
                public void onLoaderReset(Loader<Cursor> loader) {
                    // Clear the Cursor we were using with another call to the swapCursor()

                }
            };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle args = getArguments();
        if (args != null) {
            mUri = args.getParcelable(DetailActivityFragment.DETAIL_URI);
        }

        rootView = inflater.inflate(R.layout.fragment_detail, container, false);

        mSneakerName=(TextView) rootView.findViewById(R.id.text_sneaker_name);
        mImageView= (ImageView) rootView.findViewById(R.id.sneaker_poster);
        mReleaseDate=(TextView) rootView.findViewById(R.id.text_release_date);
        mReleaseTime=(TextView) rootView.findViewById(R.id.text_release_time);
        mReleaseStore=(TextView) rootView.findViewById(R.id.text_release_store);


        return rootView;
    }
    // Defines the id of the loader for later reference
    public static final int CONTACT_LOADER_ID = 11; // From docs: A unique identifier for this loader. Can be whatever you want.
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        getLoaderManager().initLoader(CONTACT_LOADER_ID, new Bundle(), contactsLoader);
    }
}
