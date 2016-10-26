package gradle.udacity.com.isneaker.data;

/**
 * Created by James Yang on 10/11/2016.
 */

import android.net.Uri;

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

        public static Uri withId(long id) {

            return buildUri(Path.SNEAKERS, String.valueOf(id));
        }
    }


}
