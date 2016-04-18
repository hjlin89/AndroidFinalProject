package com.example.gwygw_000.project;

/**
 * Created by HuijunLin on 4/15/16.
 */
public class NewsSectionData {
    private String headerTitle;
    private String firbaseRef;

    public NewsSectionData(String headerTitle, String firbaseRef) {
        this.headerTitle = headerTitle;
        this.firbaseRef = firbaseRef;
    }

    public String getHeaderTitle() {
        return headerTitle;
    }

    public String getFirbaseRef() {
        return firbaseRef;
    }

    public void setHeaderTitle(String headerTitle) {
        this.headerTitle = headerTitle;
    }

    public void setFirbaseRef(String firbaseRef) {
        this.firbaseRef = firbaseRef;
    }
}
