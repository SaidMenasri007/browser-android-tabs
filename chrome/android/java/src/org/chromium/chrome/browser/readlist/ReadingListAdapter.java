package org.chromium.chrome.browser.readlist;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.chromium.chrome.R;
import org.chromium.ui.widget.ChromeImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

//import com.squareup.picasso.Picasso;

public class ReadingListAdapter extends ArrayAdapter<ReadingListModel> {
    private ArrayList<ReadingListModel> dataSet;
    Context mContext;
    private ReadListListener readListListener;

    public ReadingListAdapter(ArrayList<ReadingListModel> data, Context context, ReadListListener readListListener) {
        super(context, R.layout.readlist_item, data);
        this.dataSet = data;
        this.mContext = context;
        this.readListListener = readListListener;
    }

    public void setList(ArrayList<ReadingListModel> readingListModelArrayList) {
        this.dataSet = readingListModelArrayList;
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(mContext);
            v = vi.inflate(R.layout.readlist_item, null);

        }
        LinearLayout linearLayout = (LinearLayout) v.findViewById(R.id.content);

        ChromeImageView iv = (ChromeImageView) v.findViewById(R.id.icon_view);
        TextView tv1 = (TextView) v.findViewById(R.id.title);

        TextView tv2 = (TextView) v.findViewById(R.id.description);

        ImageView removeImageView = (ImageView) v.findViewById(R.id.remove);

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (readListListener != null) {
                    readListListener.onItemClick(getItem(position));
                }
            }
        });

        removeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (readListListener != null) {
                    final int pos = position;
                    ReadingListModel currentModel = (ReadingListModel) getItem(position);
                    dataSet.remove(pos);
                    readListListener.onRemoveClick(currentModel);
                }
            }
        });


        final int pos = position;
        if (getItem(pos).getLogoURL() != null) {

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        InputStream is = (InputStream) new URL(getItem(pos).getLogoURL()).getContent();
                        iv.setImageBitmap(BitmapFactory.decodeStream(is));
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.out.println("There is an error");
                        iv.setImageResource(R.drawable.default_favicon);
                    }

                }
            }).start();
        } else {
            iv.setImageResource(R.drawable.default_favicon);
        }


        tv1.setText(getItem(position).getTitle());
        tv2.setText(getItem(position).getUrl());
        return v;
    }
}