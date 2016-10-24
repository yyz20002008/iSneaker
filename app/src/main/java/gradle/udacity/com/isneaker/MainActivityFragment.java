package gradle.udacity.com.isneaker;


import android.content.ContentValues;
import android.content.Context;
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
import android.widget.Toast;

import com.tonicartos.widget.stickygridheaders.StickyGridHeadersGridView;

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
    public MainActivityFragment() {
    }


    // references to our images
    private Integer[] mThumbIds = {
            R.drawable.aj1sb,
            R.drawable.aj12ovo, R.drawable.aj12wool,
            R.drawable.aj31orange, R.drawable.aj4premium
    };

    private void insert(){
        // Defines a new Uri object that receives the result of the insertion
        Uri mNewUri;

        // Defines an object to contain the new values to insert
        ContentValues mNewValues = new ContentValues();

                    /*
                     * Sets the values of each column and inserts the word. The arguments to the "put"
                     * method are "column name" and "value"
                     */

        //1. Insert Data
        int i=0;
        while (i<3) {
            mNewValues.put(SneakerDBColumns.MODEL, "AJ"+i);
            mNewValues.put(SneakerDBColumns.NAME, "Air_Jordan_1");
            mNewValues.put(SneakerDBColumns.RELEASE_DATE, "Oct,15 2016");
            mNewValues.put(SneakerDBColumns.RELEASE_TIME, "10:00AM");
            mNewValues.put(SneakerDBColumns.IMAGE_URL, mThumbIds[i]);
            mNewValues.put(SneakerDBColumns.ONLINE_STORE_LINK, "www.NIKE.com");

            mNewUri = mContext.getContentResolver().insert(
                    SneakerProvider.Sneakers.CONTENT_URI,   // content URI
                    mNewValues                          // the values to insert
            );

            i++;
        }

        while (i<mThumbIds.length) {
            mNewValues.put(SneakerDBColumns.MODEL, "AJ"+i);
            mNewValues.put(SneakerDBColumns.NAME, "Air_Jordan_1");
            mNewValues.put(SneakerDBColumns.RELEASE_DATE, "Oct,23 2016");
            mNewValues.put(SneakerDBColumns.RELEASE_TIME, "10:00AM");
            mNewValues.put(SneakerDBColumns.IMAGE_URL, mThumbIds[i]);
            mNewValues.put(SneakerDBColumns.ONLINE_STORE_LINK, "www.NIKE.com");

            mNewUri = mContext.getContentResolver().insert(
                    SneakerProvider.Sneakers.CONTENT_URI,   // content URI
                    mNewValues                          // the values to insert
            );

            i++;
        }
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




    private StickyGridHeadersGridView mGridView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_main, container, false);
        // Initialize the loader with a special ID and the defined callbacks from above

        mContext=getActivity();
        insert();

       // StickyListHeadersListView stickyList = (StickyListHeadersListView) rootView.findViewById(R.id.list);


        mImages=new ImageAdapter(getActivity(),mCursor,0);
        mGridView = (StickyGridHeadersGridView)rootView.findViewById(R.id.asset_grid);
        mGridView.setAdapter(mImages);

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(getActivity(), "" + position,
                        Toast.LENGTH_SHORT).show();
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
