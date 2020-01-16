package org.chromium.chrome.browser.readlist;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

//import com.squareup.picasso.Picasso;

import java.io.IOException;

import java.io.InputStream;

import java.net.URL;

import java.util.ArrayList;

import org.chromium.chrome.R;

public class ReadingListAdapter extends ArrayAdapter<ReadingListModel> implements View.OnClickListener {
    private ArrayList<ReadingListModel> dataSet;
    Context mContext;
    private ReadListListener readListListener;

    public ReadingListAdapter(ArrayList<ReadingListModel> data, Context context, ReadListListener readListListener) {
        super(context, R.layout.readlist_item, data);
        this.dataSet = data;
        this.mContext = context;
        this.readListListener = readListListener;
    }

    @Override
    public void onClick(View v) {

        int position = (Integer) v.getTag();
        Object obj = getItem(position);
        ReadingListModel dataModel = (ReadingListModel)obj;
        if (readListListener != null) {
            readListListener.onItemClick(dataModel);
        }
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if(v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(mContext);
            v = vi.inflate(R.layout.readlist_item, null);

        }

        final ImageView iv = (ImageView) v.findViewById(R.id.icon_view);

        TextView tv1 = (TextView) v.findViewById(R.id.title);

        TextView tv2 = (TextView) v.findViewById(R.id.description);

        ImageView removeImageView = (ImageView) v.findViewById(R.id.remove);

        removeImageView.setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View view){
               if (readListListener != null) {
                   readListListener.onRemoveClick(position, getItem(position));
               }
           }
        });



        final int pos = position;



        //Picasso.get().load(getItem(position).getLogoURL()).into(iv);



//        new Thread(new Runnable() {
//
//            @Override
//
//            public void run() {
//
//                try {
//
//                    InputStream is = (InputStream)new URL(getItem(pos).getLogoURL()).getContent();
//
//                    iv.setImageBitmap(BitmapFactory.decodeStream(is));
//
//                }catch (IOException e) {
//
//                    e.printStackTrace();
//
//                }
//
//            }
//
//        }).start();

        tv1.setText(getItem(position).getTitle());
        tv2.setText(getItem(position).getUrl());

        return v;
    }
}