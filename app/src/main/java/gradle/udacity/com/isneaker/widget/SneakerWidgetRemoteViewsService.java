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
        return mCollections.size();
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
        RemoteViews mView = new RemoteViews(mContext.getPackageName(), R.layout.sneaker_widget);
        mView.setTextViewText(R.id.text_item, mCollections.get(position).toString());

        mView.setTextColor(R.id.text_item, Color.BLACK);

        final Intent fillInIntent = new Intent();
        fillInIntent.setAction(SneakerWidgetProvider.ACTION_TOAST);
        final Bundle bundle = new Bundle();
        bundle.putString(SneakerWidgetProvider.EXTRA_STRING,
                mCollections.get(position).toString());
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

    @Override
    public void onCreate() {

        initData();
//        mCursor=mContext.getContentResolver().query(
//                SneakerProvider.Sneakers.CONTENT_URI,
//                projectionFields, // projection fields
//                null, // the selection criteria
//                null, // the selection args
//                SneakerDBColumns.RELEASE_DATE // the sort order
//        );

    }

    @Override
    public void onDataSetChanged() {
        initData();
    }

    private void initData() {
        mCollections.clear();
        for (int i = 1; i <= 10; i++) {
            mCollections.add("ListView item " + i);
            System.out.println("Widget!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        }
    }

    @Override
    public void onDestroy() {

    }

}