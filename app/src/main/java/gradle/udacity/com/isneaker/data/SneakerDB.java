package gradle.udacity.com.isneaker.data;

import net.simonvt.schematic.annotation.Database;
import net.simonvt.schematic.annotation.Table;

/**
 * Created by James Yang on 10/11/2016.
 */
@Database(version = SneakerDB.VERSION)
public class SneakerDB {

    public static final int VERSION = 1;

    @Table(SneakerDBColumns.class)
    public static final String SNEAKERS = "sneakers";

    private SneakerDB(){}
}
