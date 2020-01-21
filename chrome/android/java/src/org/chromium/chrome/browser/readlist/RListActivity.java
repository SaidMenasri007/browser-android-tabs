package org.chromium.chrome.browser.readlist;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Browser;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.chromium.base.VisibleForTesting;
import org.chromium.chrome.R;
import org.chromium.chrome.browser.ChromeTabbedActivity;
import org.chromium.chrome.browser.IntentHandler;
import org.chromium.chrome.browser.SnackbarActivity;
import org.chromium.chrome.browser.document.ChromeLauncherActivity;
import org.chromium.chrome.browser.util.IntentUtils;
import org.chromium.ui.base.DeviceFormFactor;
import org.chromium.ui.base.PageTransition;

import java.util.ArrayList;

public class RListActivity extends SnackbarActivity {

    private static final int PAGE_TRANSITION_TYPE = PageTransition.AUTO_BOOKMARK;


    ArrayList<ReadingListModel> dataModel;
    ListView ReadingListView;
    private static ReadingListAdapter adapter;
    Activity tempActivity;
    SQLiteDatabase db;
    RListHelper rListHelper;
    TextView clearReadingList;
    ImageView closeReadingList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.readinglist_main);

        ReadingListView = (ListView) findViewById(R.id.lv_rlist);
        clearReadingList = (TextView) findViewById(R.id.clear_reading_list);
        closeReadingList = (ImageView) findViewById(R.id.close_reading_list);
        tempActivity = RListActivity.this;
        rListHelper = new RListHelper(this);
        db = rListHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT _id, url, description, logo_url, created FROM READLIST ORDER BY _id DESC", new String[]{});
        dataModel = new ArrayList<>();

        if (cursor != null && cursor.moveToFirst()) {

            do {
                int id = Integer.parseInt(cursor.getString(0));
                String url = cursor.getString(1);
                String title = cursor.getString(2);
                String logo_url = cursor.getString(3);
                dataModel.add(new ReadingListModel(id, url, title, logo_url));


            } while (cursor.moveToNext());
            cursor.close();
        }




        closeReadingList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        adapter = new ReadingListAdapter(dataModel, getApplicationContext(), new ReadListListener() {
            @Override
            public void onItemClick(ReadingListModel readingListModel) {
                //todo
                openUrl(readingListModel.url, false, true);
            }

            @Override
            public void onRemoveClick(ReadingListModel readingListModel) {
                //todo
                rListHelper.deleteRow(readingListModel.getId(), db);
                notifyItemRemoval();
            }

        });
        ReadingListView.setAdapter(adapter);
        clearReadingList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("We are clearing all data");
                rListHelper.deleteAll(db);
                dataModel.clear();
                notifyItemRemoval();
            }
        });

    }

    public void notifyItemRemoval() {
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void openUrl(String url, Boolean isIncognito, boolean createNewTab) {
        IntentHandler.startActivityForTrustedIntent(getOpenUrlIntent(url, isIncognito, createNewTab));

    }

    @VisibleForTesting
    Intent getOpenUrlIntent(String url, Boolean isIncognito, boolean createNewTab) {
        // Construct basic intent.
        Intent viewIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        viewIntent.putExtra(Browser.EXTRA_APPLICATION_ID,
                this.getApplicationContext().getPackageName());
        viewIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Determine component or class name.
        ComponentName component;
        if (DeviceFormFactor.isNonMultiDisplayContextOnTablet(this)) {
            component = this.getComponentName();
        } else {
            component = IntentUtils.safeGetParcelableExtra(
                    this.getIntent(), IntentHandler.EXTRA_PARENT_COMPONENT);
        }
        if (component != null) {
            ChromeTabbedActivity.setNonAliasedComponent(viewIntent, component);
        } else {
            viewIntent.setClass(this, ChromeLauncherActivity.class);
        }

        // Set other intent extras.
        if (isIncognito != null) {
            viewIntent.putExtra(IntentHandler.EXTRA_OPEN_NEW_INCOGNITO_TAB, isIncognito);
        }
        if (createNewTab) viewIntent.putExtra(Browser.EXTRA_CREATE_NEW_TAB, true);

        viewIntent.putExtra(IntentHandler.EXTRA_PAGE_TRANSITION_TYPE, PAGE_TRANSITION_TYPE);
        return viewIntent;
    }
}