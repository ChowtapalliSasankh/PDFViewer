package com.example.home.pdfviewer;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by home on 8/25/2018.
 */

public class BkmkListViewAdapter extends BaseAdapter
{
    Context mContext;
    SQLiteDatabase db;
    LayoutInflater inflater;
    List<Bkmk> modellist;
    ArrayList<Bkmk> arrayList;
    String table,path,orgfile;
    public BkmkListViewAdapter(Context context, List<Bkmk> modellist,String table,String path,String orgfile,SQLiteDatabase db)
    {
        mContext = context;
        this.modellist = modellist;
        this.table = table;
        this.path = path;
        this.db = db;
        this.orgfile = orgfile;
        inflater = LayoutInflater.from(mContext);
        this.arrayList = new ArrayList<Bkmk>();
        this.arrayList.addAll(modellist);
    }


    public class ViewHolder
    {
        TextView mpgno,mname;
    }

    @Override
    public int getCount()
    {
        return modellist.size();
    }

    @Override
    public Object getItem(int position)
    {
        return modellist.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent)
    {
        ViewHolder holder;
        if(view == null)
        {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.row1,null);
            holder.mpgno = (TextView) view.findViewById(R.id.pgno);
            holder.mname = (TextView) view.findViewById(R.id.bname);
            view.setTag(holder);
        }
        else
        {
            holder = (ViewHolder)view.getTag();
        }
        holder.mpgno.setText(modellist.get(position).getPgno());
        holder.mname.setText(modellist.get(position).getName());
        ImageButton delbtn = (ImageButton)view.findViewById(R.id.bkdel);
        delbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                final AlertDialog.Builder build;
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                            {
                                String st = "delete from "+table+" where pgno = '"+modellist.get(position).getPgno()+"'and name = '"+modellist.get(position).getName()+"';";
                                db.execSQL(st);
                                modellist.remove(position);
                                notifyDataSetChanged();
                                break;
                            }

                            case DialogInterface.BUTTON_NEGATIVE:
                            {
                                break;
                            }
                        }
                    }
                };
                 build = new AlertDialog.Builder(mContext);
                build.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                try

                {
                    Intent intent = new Intent(mContext,pdfpage.class);
                    String temp =  modellist.get(position).getPgno();
                    String temp1 = modellist.get(position).getName();
                    int stp = Integer.parseInt(temp);
                    //Toast.makeText(mContext,temp,Toast.LENGTH_SHORT).show();
                    intent.putExtra("path",path);
                    intent.putExtra("filename",table);
                    intent.putExtra("orgfile",orgfile);
                    intent.putExtra("strt",stp);
                    mContext.startActivity(intent);

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
        return view;
    }

}
