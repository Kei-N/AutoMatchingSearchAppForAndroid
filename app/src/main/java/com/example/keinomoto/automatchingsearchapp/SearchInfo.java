package com.example.keinomoto.automatchingsearchapp;

public class SearchInfo {

    /**
     *WebサイトID
     */
    private Integer searchId;
    /**
     * Webサイトタイトル
     */
    private String title;
    /**
     * WebサイトURL
     */
    private String url;

    /**
     * @return searchId
     */
    public Integer getSearchId() {
        return searchId;
    }
    /**
     * @param searchIdをセットする
     */
    public void setSearchId(Integer searchId) {
        this.searchId = searchId;
    }
    /**
     * @return title
     */
    public String getTitle() {
        return title;
    }
    /**
     * @param titleをセットする
     */
    public void setTitle(String title) {
        this.title = title;
    }
    /**
     * @return url
     */
    public String getUrl() {
        return url;
    }
    /**
     * @param urlをセットする
     */
    public void setUrl(String url) {
        this.url = url;
    }
}
