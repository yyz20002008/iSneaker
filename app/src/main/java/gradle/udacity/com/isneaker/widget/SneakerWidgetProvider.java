package gradle.udacity.com.isneaker.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import gradle.udacity.com.isneaker.DetailActivity;
import gradle.udacity.com.isneaker.R;

/**
 * Created by James Yang on 10/29/2016.
 */
public class SneakerWidgetProvider extends AppWidgetProvider {

    public static final String ACTION_TOAST = "gradle.udacity.com.isneaker.widget.ACTION_TOAST";
    public static final String EXTRA_STRING = "gradle.udacity.com.isneaker.widget.EXTRA_STRING";
//    @Override
//    public void onReceive(Context context, Intent intent) {
//        if (intent.getAction().equals(ACTION_TOAST)) {
//            String item = intent.getExtras().getString(EXTRA_STRING);
//            Toast.makeText(context, item, Toast.LENGTH_LONG).show();
//           // intent = new Intent(context, DetailActivity.class).setData(SneakerProvider.Sneakers.withId(item));
//        }
//        super.onReceive(context, intent);
//    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final int count = appWidgetIds.length;

        // update each of the app widgets with the remote adapter
        for (int i = 0; i < count; ++i) {

            // Set up the intent that starts the StackViewService, which will
            // provide the views for this collection.
            Intent intent = new Intent(context, SneakerWidgetRemoteViewsService.class);
            // Add the app widget ID to the intent extras.
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);
            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
            // Instantiate the RemoteViews object for the app widget layout.
            RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.sneaker_widget);
            // Set up the RemoteViews object to use a RemoteViews adapter.
            // This adapter connects
            // to a RemoteViewsService  through the specified intent.
            // This is how you populate the data.
            rv.setRemoteAdapter(appWidgetIds[i], R.id.widget_list, intent);

            // The empty view is displayed when the collection has no items.
            // It should be in the same layout used to instantiate the RemoteViews
            // object above.
           // rv.setEmptyView(R.id.stack_view, R.id.empty_view);


            // Adding collection list item handler

            final Intent onItemClick = new Intent(context, DetailActivity.class);

            final PendingIntent onClickPendingIntent = PendingIntent
                    .getActivity(context, 0, onItemClick,   0);
           rv.setPendingIntentTemplate(R.id.widget_list,
                    onClickPendingIntent);
            appWidgetManager.updateAppWidget(appWidgetIds[i], rv);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }
}