package com.example.home.pdfviewer;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import java.io.File;
import java.util.ArrayList;
import java.io.File;
import java.util.ArrayList;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


public class HomeActivity extends AppCompatActivity {
    private File root;
    private ArrayList<File> fileList = new ArrayList<File>();
    private ArrayList<String> values = new ArrayList<String>();
    private ArrayList<String> paths = new ArrayList<String>();
    private String as,ss;
    ListView listView;
    ListViewAdapter adapter;
    String[] title;
    String[] size;
    ArrayList<Model> arrayList = new ArrayList<Model>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        listView = (ListView)findViewById(R.id.mainlist);
        root = new File(Environment.getExternalStorageDirectory()
                .getAbsolutePath());
        getfile(root);

        for (int i = 0; i < fileList.size(); i++) {
            as  = fileList.get(i).getName();
            ss = fileList.get(i).getAbsolutePath().toString();
            System.out.println(fileList.get(i).getName());

            if (fileList.get(i).isDirectory()) {
                continue;
            }
            values.add(as);
            paths.add(ss);
        }
        title = values.toArray(new String[values.size()]);
        size  = paths.toArray(new String[paths.size()]);

        for(int i=0;i<title.length;i++)
        {
            Model model = new Model(title[i],size[i]);
            arrayList.add(model);
        }
        adapter = new ListViewAdapter(this,arrayList);
        listView.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mainmenu,menu);

        MenuItem menuItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView)menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if(TextUtils.isEmpty(s)){
                    adapter.filter("");
                    listView.clearTextFilter();
                }
                else
                {
                    adapter.filter(s);
                }
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.aboutapp)
        {
            Intent intent = new Intent(HomeActivity.this,about_app.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    public ArrayList<File> getfile(File dir) {
        File listFile[] = dir.listFiles();
        if (listFile != null && listFile.length > 0) {
            for (int i = 0; i < listFile.length; i++) {

                if (listFile[i].isDirectory()) {
                    fileList.add(listFile[i]);
                    getfile(listFile[i]);

                } else {
                    if (listFile[i].getName().endsWith(".pdf"))

                    {
                        fileList.add(listFile[i]);
                    }
                }

            }
        }
        return fileList;
    }
}
