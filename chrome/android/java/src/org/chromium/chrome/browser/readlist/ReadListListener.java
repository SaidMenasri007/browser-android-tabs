package org.chromium.chrome.browser.readlist;


public interface ReadListListener{

    void onRemoveClick(int position, ReadingListModel readingListModel);
    void onItemClick(ReadingListModel readingListModel);


}