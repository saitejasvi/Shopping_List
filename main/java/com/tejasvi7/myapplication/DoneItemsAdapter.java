package com.tejasvi7.myapplication;

/**
 * Created by tejasvi7 on 10/15/2016.
 */


import com.tejasvi7.myapplication.R;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static com.tejasvi7.myapplication.R.id.ok;

/**
 * Provide views to RecyclerView with data from mDataSet.
 */
public class DoneItemsAdapter extends RecyclerView.Adapter<DoneItemsAdapter.ViewHolder> {

    public static String id;
    public int cPosition;
    static List<Item> dbList1;
    static Context context;
    DoneItemsAdapter(Context context, List<Item> dbList ){
        this.dbList1 = new ArrayList<Item>();
        this.context = context;
        this.dbList1 = dbList;

    }

    @Override
    public DoneItemsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item1, null);

        // create ViewHolder

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(DoneItemsAdapter.ViewHolder holder, int position) {
        Log.d("Position",""+position);
        holder.name.setText(dbList1.get(position).getName());
        holder.usern.setText(dbList1.get(position).getQuantity());

        //decode image from base64 to bitmap
        // byte[] decodedString = Base64.decode(dbList.get(position).getPic(), Base64.DEFAULT);
        // Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        // set image
        // holder.pict.setImageBitmap(decodedByte);
        if(dbList1.get(position).getPic()!=null){
            byte[] decodedString = Base64.decode(dbList1.get(position).getPic(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

            // set image
            holder.pict.setImageBitmap(decodedByte);}
    }



    @Override
    public int getItemCount() {
        return dbList1.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView name,usern;
        public ImageView pict;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            name = (TextView) itemLayoutView
                    .findViewById(R.id.itname);
            usern = (TextView) itemLayoutView.findViewById(R.id.usern);
            pict = (ImageView) itemLayoutView
                    .findViewById(R.id.PicView);
            itemLayoutView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            /*
            Intent intent = new Intent(context,DetailsActivity.class);
            Bundle extras = new Bundle();
            extras.putInt("position",getAdapterPosition());
            intent.putExtras(extras);
            /*
            int i=getAdapterPosition();
            intent.putExtra("position", getAdapterPosition());*/
            //context.startActivity(intent);
            Toast.makeText(DoneItemsAdapter.context, "clicked on row " + getAdapterPosition(), Toast.LENGTH_LONG).show();




        }
    }
}

