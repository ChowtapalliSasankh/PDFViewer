package com.example.home.pdfviewer;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;

import java.io.File;

public class pdfpage extends AppCompatActivity {

    PDFView pdfView;
    ActionBar actionBar;
    private int pgno;
    SQLiteDatabase db;
    String path,file,file1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfpage);
        Bundle bundle = getIntent().getExtras();
        path = bundle.getString("path");
        file = bundle.getString("filename");
        file1 = bundle.getString("orgfile");
        int start = bundle.getInt("strt");
        pdfView = (PDFView)findViewById(R.id.pdf1);
        File h = new File(path);
        pdfView.fromFile(h).defaultPage(start).scrollHandle(new DefaultScrollHandle( pdfpage.this)).load();
        pdfView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int a = pdfView.getCurrentPage(),b = pdfView.getPageCount();
                a++;
                AlertDialog.Builder builder = new AlertDialog.Builder(pdfpage.this);
                View mview = getLayoutInflater().inflate(R.layout.dialog_bookmark,null);
                final EditText bkmkname = (EditText)mview.findViewById(R.id.bkmkname);
                Button bkmkbtn = (Button)mview.findViewById(R.id.bkmkbtn);
                builder.setView(mview);
                final AlertDialog dialog = builder.create();
                dialog.show();
                bkmkbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        if(!bkmkname.getText().toString().isEmpty())
                        {
                            db = openOrCreateDatabase("Bookmarks", Context.MODE_ENABLE_WRITE_AHEAD_LOGGING,null);
                            String ct = "create table if not exists "+file +"(pgno vaarchar(20),name vaarchar(100))";
                            db.execSQL(ct);
                            int cpg = pdfView.getCurrentPage();
                            String s = String.valueOf(cpg);
                            String x = bkmkname.getText().toString();
                            String ss = "Insert Into "+file+"(pgno,name)VALUES('"+ s +"','"+x+"');";
                            db.execSQL(ss);
                            Toast.makeText(pdfpage.this,s+"  "+x+"  record saved",Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    }
                });
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.pdf_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.bmrk)
        {
            Intent intent = new Intent(pdfpage.this, bkmk_list.class);
            intent.putExtra("tablename",file);
            intent.putExtra("orgfile",file1);
            intent.putExtra("path",path);
            startActivity(intent);
        }
        if(id == R.id.nts)
        {
            Intent intent = new Intent(pdfpage.this, notes.class);
            intent.putExtra("tablename",file);
            intent.putExtra("orgfile",file1);
            intent.putExtra("path",path);
            intent.putExtra("stp",pdfView.getCurrentPage());
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

}
