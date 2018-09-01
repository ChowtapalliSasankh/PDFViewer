package com.example.home.pdfviewer;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by home on 8/22/2018.
 */

public class ListViewAdapter extends BaseAdapter
{

    Context mContext;
    LayoutInflater inflater;
    List<Model> modellist;
    ArrayList<Model> arrayList;

    public ListViewAdapter(Context context, List<Model> modellist) {
        mContext = context;
        this.modellist = modellist;
        inflater = LayoutInflater.from(mContext);
        this.arrayList = new ArrayList<Model>();
        this.arrayList.addAll(modellist);

    }


    public class ViewHolder
    {
        TextView mtitle,msize;

    }
    @Override
    public int getCount() {
        return modellist.size();
    }

    @Override
    public Object getItem(int position) {
        return modellist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent)
    {
        ViewHolder holder;
        if(view == null)
        {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.row,null);
            holder.mtitle = (TextView) view.findViewById(R.id.title);
            holder.msize = (TextView) view.findViewById(R.id.size);
            view.setTag(holder);
        }
        else
        {
            holder = (ViewHolder)view.getTag();
        }
        holder.mtitle.setText(modellist.get(position).getTitle());
        holder.msize.setText(modellist.get(position).getSize());
        ImageButton delbtn = (ImageButton)view.findViewById(R.id.maindel);
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
                                File file = new File(modellist.get(position).getSize());
                                file.delete();
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
                build.setMessage("Do you want to delete the file permanently?").setPositiveButton("Yes", dialogClickListener)
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
                    String temp =  modellist.get(position).getSize();
                    String temp1 = modellist.get(position).getTitle();
                    String temp2 = temp1.substring(0,temp1.length()-4);
                    temp2 = temp1.replaceAll("\\W","");
                    intent.putExtra("path",temp);
                    intent.putExtra("filename",temp2);
                    intent.putExtra("orgfile",temp1);
                    intent.putExtra("strt",0);
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

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        modellist.clear();
        if (charText.length() == 0) {
            modellist.addAll(arrayList);
        } else {
            for (Model model : arrayList) {
                if (model.getTitle().toLowerCase(Locale.getDefault()).contains(charText)) {
                    modellist.add(model);
                }
            }
        }
        notifyDataSetChanged();
    }
}
