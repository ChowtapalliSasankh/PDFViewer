package com.example.home.pdfviewer;

/**
 * Created by home on 8/25/2018.
 */

public class Bkmk
{
    String pgno;
    String name;

    public Bkmk(String pgno, String name)
    {
        this.pgno = pgno;
        this.name = name;
    }

    public String getPgno() {
        return this.pgno;
    }

    public String getName() {
        return this.name;
    }
}
