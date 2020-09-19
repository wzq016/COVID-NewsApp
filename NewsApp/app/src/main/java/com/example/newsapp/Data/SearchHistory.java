package com.example.newsapp.Data;

import org.litepal.crud.LitePalSupport;

public class SearchHistory extends LitePalSupport
{
    private String query;
    public SearchHistory(String query)
    {
        super();
        this.query=query;
    }
    public String getQuery()
    {
        return this.query;
    }
}
