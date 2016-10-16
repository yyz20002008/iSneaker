package gradle.udacity.com.isneaker.data;

/**
 * Created by James Yang on 10/11/2016.
 */

import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;

import net.simonvt.schematic.annotation.ContentProvider;
import net.simonvt.schematic.annotation.ContentUri;
import net.simonvt.schematic.annotation.InexactContentUri;
import net.simonvt.schematic.annotation.TableEndpoint;

@ContentProvider(authority = SneakerProvider.AUTHORITY,database = SneakerDB.class)
public class SneakerProvider {
    public static final String AUTHORITY = "gradle.udacity.com.isneaker.data.sneakerprovider";

    static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    private static Uri buildUri(String... paths) {
        Uri.Builder builder = BASE_CONTENT_URI.buildUpon();
        for (String path : paths) {
            builder.appendPath(path);
        }
        return builder.build();
    }

    interface Path {
        String SNEAKERS = "Sneakers";
    }

    @TableEndpoint(table = SneakerDB.SNEAKERS) public static class Sneakers {

        //@ContentUri will return all our rows from our tables,
        //the @InexactContetUri will return a single row based on an ID (this depends on the kind of queries that you want for your database).
        @ContentUri(
                path = Path.SNEAKERS,
                type = "vnd.android.cursor.dir/sneaker",
                defaultSort = SneakerDBColumns.RELEASE_DATE + " ASC")
        public static final Uri CONTENT_URI = buildUri(Path.SNEAKERS);

        @InexactContentUri(
                name = "SNEAKER_NAME",
                path = Path.SNEAKERS + "/#",
                type = "vnd.android.cursor.item/sneaker",
                whereColumn = SneakerDBColumns.NAME,
                pathSegment = 1
        )

        public static Uri withId(int id) {

            return buildUri(Path.SNEAKERS, String.valueOf(id));
        }
    }
    // Implements ContentProvider.query()
    // Creates a UriMatcher object.
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        /*
         * The calls to addURI() go here, for all of the content URI patterns that the provider
         * should recognize. For this snippet, only the calls for table 3 are shown.
         */

        /*
         * Sets the integer value for multiple rows in table 3 to 1. Notice that no wildcard is used
         * in the path
         */
        sUriMatcher.addURI(AUTHORITY, "SNEAKERS", 1);


    }
    @Override
    public Cursor query(
            Uri uri,
            String[] projection,
            String selection,
            String[] selectionArgs,
            String sortOrder) {

        /*
         * Choose the table to query and a sort order based on the code returned for the incoming
         * URI. Here, too, only the statements for table 3 are shown.
         */
        switch (sUriMatcher.match(uri)) {


            // If the incoming URI was for all of table3
            case 1:

                if (TextUtils.isEmpty(sortOrder)) sortOrder = "_ID ASC";
                break;

            // If the incoming URI was for a single row
            case 2:

                /*
                 * Because this URI was for a single row, the _ID value part is
                 * present. Get the last path segment from the URI; this is the _ID value.
                 * Then, append the value to the WHERE clause for the query
                 */
                selection = selection + "_ID = " + uri.getLastPathSegment();
                break;

            default:
                System.out.println("No Match, Error!");
                // If the URI is not recognized, you should do some error handling here.
        }

        return ;
    }

}
