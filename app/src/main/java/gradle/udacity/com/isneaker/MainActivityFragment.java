package gradle.udacity.com.isneaker;


import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import gradle.udacity.com.isneaker.data.SneakerDBColumns;
import gradle.udacity.com.isneaker.data.SneakerProvider;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    // Defines the id of the loader for later reference
    public static final int CONTACT_LOADER_ID = 78; // From docs: A unique identifier for this loader. Can be whatever you want.

    private ImageAdapter mImages;
    private Context mContext;
    Cursor mCursor;
    Uri mNewUri;
    public MainActivityFragment() {
    }


    // references to our images
    private Integer[] mThumbIds = {
            R.drawable.aj1sb,
            R.drawable.aj12ovo, R.drawable.aj12wool,
            R.drawable.aj31orange, R.drawable.aj4premium
    };

    private void insert(){

        // Defines an object to contain the new values to insert
        ContentValues mNewValues = new ContentValues();

        //1. Insert Data

        mNewValues.put(SneakerDBColumns.MODEL, "888888-1");
        mNewValues.put(SneakerDBColumns.NAME, "Air_Jordan_1");
        mNewValues.put(SneakerDBColumns.RELEASE_DATE, "10/15/2016");
        mNewValues.put(SneakerDBColumns.RELEASE_TIME, "10:00AM");
        mNewValues.put(SneakerDBColumns.IMAGE_URL, mThumbIds[0]);
        mNewValues.put(SneakerDBColumns.ONLINE_STORE_LINK, "www.NIKE.com");

        mContext.getContentResolver().insert(
                SneakerProvider.Sneakers.CONTENT_URI,   // content URI
                mNewValues                          // the values to insert
        );


        mNewValues = new ContentValues();
        mNewValues.put(SneakerDBColumns.MODEL,"888888-2");
        mNewValues.put(SneakerDBColumns.NAME, "Air_Jordan_12_OVO");
        mNewValues.put(SneakerDBColumns.RELEASE_DATE, "10/23/2016");
        mNewValues.put(SneakerDBColumns.RELEASE_TIME, "10:00AM");
        mNewValues.put(SneakerDBColumns.IMAGE_URL, mThumbIds[1]);
        mNewValues.put(SneakerDBColumns.ONLINE_STORE_LINK, "www.NIKE.com");

        mContext.getContentResolver().insert(
                SneakerProvider.Sneakers.CONTENT_URI,   // content URI
                mNewValues                          // the values to insert
        );

        mNewValues = new ContentValues();
        mNewValues.put(SneakerDBColumns.MODEL,"888888-3");
        mNewValues.put(SneakerDBColumns.NAME, "Air_Jordan_12_Wool");
        mNewValues.put(SneakerDBColumns.RELEASE_DATE, "10/23/2016");
        mNewValues.put(SneakerDBColumns.RELEASE_TIME, "10:00AM");
        mNewValues.put(SneakerDBColumns.IMAGE_URL, mThumbIds[2]);
        mNewValues.put(SneakerDBColumns.ONLINE_STORE_LINK, "www.FOOTLOCKER.com");

        mContext.getContentResolver().insert(
                SneakerProvider.Sneakers.CONTENT_URI,   // content URI
                mNewValues                          // the values to insert
        );

        mNewValues = new ContentValues();
        mNewValues.put(SneakerDBColumns.MODEL,"888888-4");
        mNewValues.put(SneakerDBColumns.NAME, "Air_Jordan_31_Orange");
        mNewValues.put(SneakerDBColumns.RELEASE_DATE, "10/23/2016");
        mNewValues.put(SneakerDBColumns.RELEASE_TIME, "10:00AM");
        mNewValues.put(SneakerDBColumns.IMAGE_URL, mThumbIds[3]);
        mNewValues.put(SneakerDBColumns.ONLINE_STORE_LINK, "www.FOOTLOCKER.com");

        mContext.getContentResolver().insert(
                SneakerProvider.Sneakers.CONTENT_URI,   // content URI
                mNewValues                          // the values to insert
        );

        mNewValues = new ContentValues();
        mNewValues.put(SneakerDBColumns.MODEL,"888888-5");
        mNewValues.put(SneakerDBColumns.NAME, "Air_Jordan_4_Premium");
        mNewValues.put(SneakerDBColumns.RELEASE_DATE, "10/15/2016");
        mNewValues.put(SneakerDBColumns.RELEASE_TIME, "10:00AM");
        mNewValues.put(SneakerDBColumns.IMAGE_URL, mThumbIds[4]);
        mNewValues.put(SneakerDBColumns.ONLINE_STORE_LINK, "www.Finishline.com");

        mContext.getContentResolver().insert(
                SneakerProvider.Sneakers.CONTENT_URI,   // content URI
                mNewValues                          // the values to insert
        );




    }
    // Defines the asynchronous callback for the contacts data loader
    private LoaderManager.LoaderCallbacks<Cursor> contactsLoader =
            new LoaderManager.LoaderCallbacks<Cursor>() {
                // Create and return the actual cursor loader for the contacts data
                @Override
                public Loader<Cursor> onCreateLoader(int id, Bundle args) {

                    // Define the columns to retrieve
                    String[] projectionFields = new String[] {SneakerDBColumns._ID,
                            SneakerDBColumns.IMAGE_URL,SneakerDBColumns.RELEASE_DATE};
                    // Construct the loader
                    CursorLoader cursorLoader = new CursorLoader(mContext,
                            SneakerProvider.Sneakers.CONTENT_URI, // URI
                            projectionFields, // projection fields
                            null, // the selection criteria
                            null, // the selection args
                            SneakerDBColumns.RELEASE_DATE // the sort order
                    );
                    // Return the loader for use
                    return cursorLoader;
                }

                // When the system finishes retrieving the Cursor through the CursorLoader,
                // a call to the onLoadFinished() method takes place.
                @Override
                public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
                    // The swapCursor() method assigns the new Cursor to the adapter
                    mImages.swapCursor(cursor);
                }

                // This method is triggered when the loader is being reset
                // and the loader data is no longer available. Called if the data
                // in the provider changes and the Cursor becomes stale.
                @Override
                public void onLoaderReset(Loader<Cursor> loader) {
                    // Clear the Cursor we were using with another call to the swapCursor()
                    mImages.swapCursor(null);
                }
            };




    private GridView mGridView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_main, container, false);
        // Initialize the loader with a special ID and the defined callbacks from above

        mContext=getActivity();
        //insert();
        // Define the columns to retrieve
        String[] projectionFields = new String[] {SneakerDBColumns._ID,
                SneakerDBColumns.IMAGE_URL,SneakerDBColumns.RELEASE_DATE};
        //2.Retrieve Data
        mCursor=getActivity().getContentResolver().query(
                SneakerProvider.Sneakers.CONTENT_URI,
                projectionFields, // projection fields
                null, // the selection criteria
                null, // the selection args
                SneakerDBColumns.RELEASE_DATE // the sort order
        );

       // StickyListHeadersListView stickyList = (StickyListHeadersListView) rootView.findViewById(R.id.list);

        mImages=new ImageAdapter(getActivity(),mCursor,0);
        mGridView = (GridView)rootView.findViewById(R.id.asset_grid);
        mGridView.setAdapter(mImages);

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                mCursor.moveToPosition(position);
                Toast.makeText(getActivity(), "" + mCursor.getInt(mCursor.getColumnIndexOrThrow("_id")),
                        Toast.LENGTH_SHORT).show();

                int sneakerid=mCursor.getInt(mCursor.getColumnIndexOrThrow("_id"));
                Intent intent = new Intent(getActivity(), DetailActivity.class).setData(SneakerProvider.Sneakers.withId(sneakerid));

                startActivity(intent);
            }
        });

        return rootView;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        getLoaderManager().initLoader(CONTACT_LOADER_ID, new Bundle(), contactsLoader);
    }
}
