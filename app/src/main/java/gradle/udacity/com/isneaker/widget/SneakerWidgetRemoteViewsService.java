package gradle.udacity.com.isneaker.widget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;
import java.util.List;

import gradle.udacity.com.isneaker.R;
import gradle.udacity.com.isneaker.data.SneakerDBColumns;
import gradle.udacity.com.isneaker.data.SneakerProvider;

/**
 * Created by James Yang on 10/31/2016.
 */
public class SneakerWidgetRemoteViewsService extends RemoteViewsService {
    public final String LOG_TAG = SneakerWidgetRemoteViewsService.class.getSimpleName();
    @Override
    public SneakerWidgetRemoteViewsFactory onGetViewFactory(Intent intent) {

        return new SneakerWidgetRemoteViewsFactory(this.getApplicationContext(), intent);
    }
}
class SneakerWidgetRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    List mCollections = new ArrayList();

    Context mContext =null ;
    Cursor mCursor;


    public SneakerWidgetRemoteViewsFactory(Context context, Intent intent) {

        mContext = context;

    }

    @Override
    public int getCount() {
        return mCursor.getCount();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public RemoteViews getViewAt(int position) {
        mCursor.moveToPosition(position);
        RemoteViews mView = new RemoteViews(mContext.getPackageName(), R.layout.sneaker_widget_item);
        mView.setTextViewText(R.id.text_item, mCursor.getString(mCursor.getColumnIndexOrThrow("name")));
        mView.setTextColor(R.id.text_item, Color.BLACK);

        final Intent fillInIntent = new Intent();
        fillInIntent.setAction(SneakerWidgetProvider.ACTION_TOAST);
        final Bundle bundle = new Bundle();

        bundle.putInt(SneakerWidgetProvider.EXTRA_STRING,
                mCursor.getInt(mCursor.getColumnIndexOrThrow("_id")));
        fillInIntent.putExtras(bundle);
        mView.setOnClickFillInIntent(R.id.text_item, fillInIntent);
        return mView;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    // Define the columns to retrieve
    String[] projectionFields = new String[] {SneakerDBColumns._ID,
            SneakerDBColumns.IMAGE_URL,
            SneakerDBColumns.RELEASE_TIME,
            SneakerDBColumns.RELEASE_DATE,
            SneakerDBColumns.ONLINE_STORE_LINK,
            SneakerDBColumns.NAME,
            SneakerDBColumns.MODEL};
    @Override
    public void onCreate() {

        initData();
        mCursor=mContext.getContentResolver().query(
                SneakerProvider.Sneakers.CONTENT_URI,
                projectionFields, // projection fields
                null, // the selection criteria
                null, // the selection args
                SneakerDBColumns.RELEASE_DATE // the sort order
        );


    }

    @Override
    public void onDataSetChanged() {
        initData();
    }

    private void initData() {
        mCollections.clear();
        for (int i = 1; i <= 10; i++) {
            mCollections.add("ListView item " + i);

        }

    }

    @Override
    public void onDestroy() {

    }

}