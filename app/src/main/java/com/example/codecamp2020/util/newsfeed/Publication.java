package com.example.codecamp2020.util.newsfeed;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Publication {

    private String title;

    public Publication(String title, String abstractURL, Date published, Date updated, String summary, String author, String category) {
        this.title = title;
        this.abstractURL = abstractURL;
        this.published = published;
        this.updated = updated;
        this.summary = summary;
        this.author = author;
        this.category = category;
    }

    private String abstractURL;
    private Date published;
    private Date updated;
    private String summary;
    private String author;
    private String category;

    public String getAbstractURL() {
        return abstractURL;
    }

    public Date getPublished() {
        return published;
    }

    public Date getUpdated() {
        return updated;
    }

    public String getSummary() {
        return summary;
    }

    public String getAuthor() {
        return author;
    }

    public String getCategory() {
        return category;
    }

    public String getTitle() {
        return title;
    }
}
