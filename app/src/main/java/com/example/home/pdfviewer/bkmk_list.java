package com.example.home.pdfviewer;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class bkmk_list extends AppCompatActivity {
    private ListView lvew;
    BkmkListViewAdapter adapter;
    String table,path,orgfile;
    ArrayList<String> bkmspg = new ArrayList<String>();
    ArrayList<String> bkmsname = new ArrayList<String>();
    SQLiteDatabase db;
    String[] pgno;
    String[] name;
    ArrayList<Bkmk> arrayList = new ArrayList<Bkmk>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bkmk_list);
        lvew = (ListView)findViewById(R.id.llview);
        db = openOrCreateDatabase("Bookmarks", Context.MODE_PRIVATE,null);
        Bundle bundle = getIntent().getExtras();
        table = bundle.getString("tablename");
        path = bundle.getString("path");
        orgfile = bundle.getString("orgfile");
        setlistitems(table);
        pgno = bkmspg.toArray(new String[bkmspg.size()]);
        name  = bkmsname.toArray(new String[bkmsname.size()]);
        for(int i=0;i<name.length;i++)
        {
            Bkmk bkmk = new Bkmk(pgno[i],name[i]);
            arrayList.add(bkmk);
        }
        adapter = new BkmkListViewAdapter(this,arrayList,table,path,orgfile,db);
        lvew.setAdapter(adapter);

    }

    private void setlistitems(String table) {
        try
        {
            Cursor c = db.rawQuery("Select * From " + table, null);
            if (c.getCount() == 0)
            {
                Toast.makeText(bkmk_list.this, "database is empty", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(bkmk_list.this, pdfpage.class);
                intent.putExtra("path", path);
                intent.putExtra("filename", table);
                intent.putExtra("orgfile", orgfile);
                intent.putExtra("strt", 0);
                startActivity(intent);
                finish();
            }
            else
            {
                while (c.moveToNext())
                {
                    //buff.append(c.getString(0)+"\n");
                    bkmspg.add(c.getString(0));
                    bkmsname.add(c.getString(1));
                }
            }
        }
        catch (Exception e)
        {
            Toast.makeText(this, "No bookmarks exist", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(bkmk_list.this, pdfpage.class);
            intent.putExtra("path", path);
            intent.putExtra("filename", table);
            intent.putExtra("orgfile", orgfile);
            intent.putExtra("strt", 0);
            //setResult(2,intent);
            startActivity(intent);
            finish();
        }
    }
}
