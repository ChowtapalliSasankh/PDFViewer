package com.example.home.pdfviewer;

/**
 * Created by home on 8/22/2018.
 */

public class Model
{
    String title;
    String size;

    public Model(String title, String size)
    {
        this.title = title;
        this.size = size;
    }

    public String getTitle() {
        return this.title;
    }

    public String getSize() {
        return this.size;
    }
}
