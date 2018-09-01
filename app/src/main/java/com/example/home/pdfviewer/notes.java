package com.example.home.pdfviewer;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

public class notes extends AppCompatActivity
{
    EditText x;
    SQLiteDatabase db;
    String table,path,orgfile,note;
    int stp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        x = (EditText)findViewById(R.id.etnts);
        Bundle bundle = getIntent().getExtras();
        table = bundle.getString("tablename");
        path = bundle.getString("path");
        orgfile = bundle.getString("orgfile");
        stp = bundle.getInt("stp");
        db = openOrCreateDatabase("Notes", Context.MODE_ENABLE_WRITE_AHEAD_LOGGING,null);
        String ct = "create table if not exists "+table+"(data vaarchar(1000))";
        db.execSQL(ct);
        try
        {
            Cursor c = db.rawQuery("Select * From " + table, null);
            if (c.getCount() == 0)
            {
                Toast.makeText(this, "No notes exist", Toast.LENGTH_SHORT).show();
            }
            else
            {
                while (c.moveToNext())
                {
                    note = c.getString(0);
                    x.setText(note);
                }
            }
        }
        catch (Exception e)
        {
            finish();
        }
    }

   /* @Override
    protected void onPause() {


                            String st = "delete from "+table+" where data = '"+note+"';";
                            db.execSQL(st);
                            String ss = "Insert Into "+table+"(data)VALUES('"+ x.getText().toString() +"');";
                            db.execSQL(ss);
        super.onPause();
    }*/

    @Override
    public void onBackPressed() {
        if(!x.getText().equals(note))
        {
            final AlertDialog.Builder build;
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which){
                        case DialogInterface.BUTTON_POSITIVE:
                        {
                            String st = "delete from "+table+" where data = '"+note+"';";
                            db.execSQL(st);
                            String ss = "Insert Into "+table+"(data)VALUES('"+ x.getText().toString() +"');";
                            db.execSQL(ss);
                            finish();
                            break;
                        }

                        case DialogInterface.BUTTON_NEGATIVE:
                        {
                            finish();
                            break;
                        }
                    }
                }
            };
            build = new AlertDialog.Builder(this);
            build.setMessage("Do you want to save the changes?").setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();
        }
        else
        {
            finish();
        }
        //super.onBackPressed();
    }
}
