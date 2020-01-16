package org.chromium.chrome.browser.readlist;

public class ReadingListModel {
    int  id;
    String title, url, logo_url;

    public ReadingListModel(String url, String title, String logo_url) {
        this.url = url;
        this.title = title;
        this.logo_url = logo_url;
    }

    public ReadingListModel(int id, String url, String title, String logo_url) {
        this.id = id;
        this.url = url;
        this.title = title;
        this.logo_url = logo_url;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getLogoURL() {
        return logo_url;
    }
}