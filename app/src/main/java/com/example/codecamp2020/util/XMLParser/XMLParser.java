package com.example.codecamp2020.util.XMLParser;

import android.util.Xml;

import com.example.codecamp2020.util.newsfeed.Publication;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class XMLParser {

    private static final String namespace = null;


    //TODO: Make generic List and represent responses as Object
    public List<Publication> parse(InputStream in) throws XmlPullParserException, IOException, ParseException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            return readEntries(parser);
        } finally {
            in.close();
        }
    }

    private List<Publication> readEntries(XmlPullParser parser) throws IOException, XmlPullParserException, ParseException {
        List<Publication> entries = new ArrayList<Publication>();

        parser.require(XmlPullParser.START_TAG, namespace,"feed");
        while(parser.next() != XmlPullParser.END_TAG){
            if(parser.getEventType() != XmlPullParser.START_TAG) continue;
            if(parser.getName().equals("entry")){
                entries.add(readEntry(parser));
            }
            else skip(parser);
        }
        return entries;
    }

    private Publication readEntry(XmlPullParser parser) throws IOException, XmlPullParserException, ParseException {
        parser.require(XmlPullParser.START_TAG, null, "entry");
        String title = "";
        String abstractURL = "";
        String summary = "";
        String author = "";
        String category = "";
        Date published = null;
        Date updated = null;

        while(parser.next() != XmlPullParser.END_TAG){
            if(parser.getEventType() != XmlPullParser.START_TAG) continue;
            String tag = parser.getName();
            switch (tag){
                case "title":
                    title = readTitle(parser);
                    break;
                case "id":
                    abstractURL = readAbstractURL(parser);
                    break;
                case "summary":
                    summary = readSummary(parser);
                    break;
                case "author":
                    author += readAuthor(parser);
                    break;
                case "category":
                    category = readCategory(parser);
                    break;
                case "updated":
                    updated = readUpdated(parser);
                    break;
                case "published":
                    published = readPublished(parser);
                    break;
                default:
                    skip(parser);
                    break;
            }
        }
        return new Publication(title, abstractURL, published, updated, summary, author, category);
    }

    private String readTitle(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, null, "title");
        String title = readText(parser);
        parser.require(XmlPullParser.END_TAG, null, "title");
        return title;
    }

    private String readAuthor(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, namespace,"author");
        String author = "";
        while(parser.next() != XmlPullParser.END_TAG){
            if(parser.getEventType() != XmlPullParser.START_TAG) continue;
            if(parser.getName().equals("name")){
                author+=readText(parser)+"\n";
            }
        }

       // while(parser.next() != XmlPullParser.START_TAG) continue;
       // parser.require(XmlPullParser.START_TAG, null, "name");
       // String author = readText(parser);
       // parser.require(XmlPullParser.END_TAG, null, "name");

        return author;
    }

    private String readSummary(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, null, "summary");
        String summary = readText(parser);
        parser.require(XmlPullParser.END_TAG, null, "summary");
        return summary;
    }

    private String readCategory(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, null, "category");
        String category = readText(parser);
        parser.require(XmlPullParser.END_TAG, null, "category");
        return category;
    }
    private String readAbstractURL(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, null, "id");
        String id = readText(parser);
        parser.require(XmlPullParser.END_TAG, null, "id");
        return id;
    }

    private Date readPublished(XmlPullParser parser) throws IOException, XmlPullParserException, ParseException {
        parser.require(XmlPullParser.START_TAG, null, "published");
        String temp = readText(parser);
        parser.require(XmlPullParser.END_TAG, null, "published");
        //DateFormat formatter = new SimpleDateFormat("yyyy-mm-dd'T'HH:mm:ss'-'HH:mm");
        //Date published = formatter.parse(temp);
        Date published = Date.from(Instant.parse(temp));
        return published;
    }

    private Date readUpdated(XmlPullParser parser) throws IOException, XmlPullParserException, ParseException {
        parser.require(XmlPullParser.START_TAG, null, "updated");
        String temp = readText(parser);
        parser.require(XmlPullParser.END_TAG, null, "updated");
        Date updated = Date.from(Instant.parse(temp));
        return updated;
    }

    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if(parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if(parser.getEventType() != XmlPullParser.START_TAG) throw new IllegalStateException();
        int depth = 1;
        while(depth != 0){
            switch (parser.next()){
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;

            }
        }
    }

}
