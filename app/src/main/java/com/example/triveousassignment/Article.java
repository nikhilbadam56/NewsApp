package com.example.triveousassignment;

import java.io.Serializable;

public class Article implements Serializable {

    //this class deserializes the json data into human readable variables for easy
    //separation and to work with it .
    private String source;
    private String Description;
    private String url;
    private String urltoimage;
    private String title;
    public Article(String source, String description,String url,String urltoimage,String title)
    {
        this.source = source;
        this.Description = description;
        this.url = url;
        this.urltoimage = urltoimage;
        this.title = title;
    }
    public String getSource()
    {
       return this.source;
    }
    public String getDescription()
    {
        return this.Description;
    }
    public String getUrl()
    {
        return this.url;
    }
    public String getUrltoimage(){return this.urltoimage;}
    public String getTitle(){return this.title;}
}
