package com.example.codecamp2020.util.newsfeed;

import java.net.URL;
import java.util.Date;

public class Article {

    private String source;
    private String author;
    private String title;
    private String description;
    private URL articleURL;
    private URL thumbnail;
    private Date published;
    private String content;

    public void setSource(String source) {
        this.source = source;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setArticleURL(URL articleURL) {
        this.articleURL = articleURL;
    }

    public void setThumbnail(URL thumbnail) {
        this.thumbnail = thumbnail;
    }

    public void setPublished(Date published) {
        this.published = published;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSource() {
        return source;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public URL getArticleURL() {
        return articleURL;
    }

    public URL getThumbnail() {
        return thumbnail;
    }

    public Date getPublished() {
        return published;
    }

    public String getContent() {
        return content;
    }

    public Article(String source, String author, String title, String description, URL articleURL, URL thumbnail, Date published, String content) {
        this.source = source;
        this.author = author;
        this.title = title;
        this.description = description;
        this.articleURL = articleURL;
        this.thumbnail = thumbnail;
        this.published = published;
        this.content = content;
    }
}
