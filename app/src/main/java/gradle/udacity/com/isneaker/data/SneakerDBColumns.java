package gradle.udacity.com.isneaker.data;

import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.ConflictResolutionType;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.PrimaryKey;
import net.simonvt.schematic.annotation.Unique;

/**
 * Created by James Yang on 10/11/2016.
 */
public interface SneakerDBColumns {
    @DataType(DataType.Type.INTEGER) @PrimaryKey
    @AutoIncrement
    String _ID = "_id";

    @DataType(DataType.Type.TEXT)
    String MODEL = "model";

    @DataType(DataType.Type.TEXT)
    String NAME = "name";

    @DataType(DataType.Type.TEXT)
    String RELEASE_DATE = "release_date";

    @DataType(DataType.Type.TEXT)
    String RELEASE_TIME = "release_time";

    @DataType(DataType.Type.TEXT)
    String ONLINE_STORE_LINK = "online_store_link";

    @Unique(onConflict = ConflictResolutionType.REPLACE)
    @DataType(DataType.Type.TEXT)
    //@NotNull
    String IMAGE_URL = "image_url";
}
