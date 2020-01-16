package org.chromium.chrome.browser.readlist;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.chromium.chrome.browser.IntentHandler;
import org.chromium.chrome.browser.ChromeActivity;
import org.chromium.chrome.browser.tabmodel.TabCreatorManager.TabCreator;
import org.chromium.chrome.browser.tabmodel.TabLaunchType;
import org.chromium.content_public.browser.LoadUrlParams;
import org.chromium.ui.base.PageTransition;


import java.util.ArrayList;

import org.chromium.base.VisibleForTesting;
import org.chromium.chrome.browser.IntentHandler;
import org.chromium.chrome.browser.SnackbarActivity;
import org.chromium.chrome.browser.util.IntentUtils;
import org.chromium.chrome.R;

public class RListActivity extends SnackbarActivity {

    private static final int PAGE_TRANSITION_TYPE = PageTransition.AUTO_BOOKMARK;


    ArrayList<ReadingListModel> dataModel;
    ListView ReadingListView;
    private static ReadingListAdapter adapter;
    Activity tempActivity;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.readinglist_main);

        ReadingListView = (ListView) findViewById(R.id.lv_rlist);
        tempActivity = RListActivity.this;
        RListHelper rListHelper = new RListHelper(this);
        SQLiteDatabase db = rListHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT url, description, logo_url, created FROM READLIST", new String[]{});

        if(cursor != null) {
            cursor.moveToFirst();
        }

        dataModel = new ArrayList<>();

        do {
            String url = cursor.getString(0);
            String title = cursor.getString(1);
            String logo_url = cursor.getString(2);

            dataModel.add(new ReadingListModel(url, title, logo_url));


        } while (cursor.moveToNext());

        adapter = new ReadingListAdapter(dataModel, getApplicationContext(), new ReadListListener(){
            @Override
            public void onItemClick(ReadingListModel readingListModel){
                //todo
                openUrl(readingListModel.url, false, true);
            }

            @Override
            public void onRemoveClick(int position, ReadingListModel readingListModel){
                //todo
            }
        });
        ReadingListView.setAdapter(adapter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void openUrl(String url, Boolean isIncognito, boolean createNewTab) {
        if (isDisplayedInSeparateActivity()) {
            IntentHandler.startActivityForTrustedIntent(
                    getOpenUrlIntent(url, isIncognito, createNewTab));
            return;
        }
        ChromeActivity activity = (ChromeActivity) tempActivity;
        if (createNewTab) {
            TabCreator tabCreator = (isIncognito == null) ? activity.getCurrentTabCreator()
                    : activity.getTabCreator(isIncognito);
            tabCreator.createNewTab(new LoadUrlParams(url, PAGE_TRANSITION_TYPE),
                    TabLaunchType.FROM_LINK, activity.getActivityTab());
        } else {
            activity.getActivityTab().loadUrl(new LoadUrlParams(url, PAGE_TRANSITION_TYPE));
        }
    }
}