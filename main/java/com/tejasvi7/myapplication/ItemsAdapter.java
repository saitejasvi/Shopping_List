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



    public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder> {

    public static String id;
    public int cPosition;
    public static List<Item>dbList1= new ArrayList<Item>();

    static List<Item> dbList;
        static Context context;
        ItemsAdapter(Context context, List<Item> dbList ){
            this.dbList = new ArrayList<Item>();
            this.context = context;
            this.dbList = dbList;

        }

        @Override
        public ItemsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.item, null);

            // create ViewHolder

            ViewHolder viewHolder = new ViewHolder(itemLayoutView);


            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ItemsAdapter.ViewHolder holder, int position) {
            Log.d("Position",""+position);
            holder.name.setText(dbList.get(position).getName());
            holder.usern.setText(dbList.get(position).getQuantity());
            if(dbList.get(position).getPic()!=null){
            byte[] decodedString = Base64.decode(dbList.get(position).getPic(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

            // set image
            holder.pict.setImageBitmap(decodedByte);}
            final Item infoData=dbList.get(position);
            holder.ok.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    cPosition=dbList.indexOf(infoData);
                  removeItem(infoData);
                    Log.d("cPosition",""+cPosition);
                }
            });
           id= dbList.get(cPosition).getid();

        }

    private int removeItem(Item infoData) {
int cPosition= dbList.indexOf(infoData);
        Log.d("before removing",""+dbList.size());
        dbList.remove(cPosition);
        notifyItemRemoved(cPosition);
        notifyItemRangeChanged(cPosition, dbList.size());
        Item newItem=new Item();
        newItem=LoginDataBaseAdapter.addEntry(id);
        dbList1.add(newItem);
        LoginDataBaseAdapter.deleteEntry1(id);
        return cPosition;


    }
    @Override
        public int getItemCount() {
            return dbList.size();
        }


        public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            public TextView name,usern;
            public Button ok;
            public ImageView pict;

            public ViewHolder(View itemLayoutView) {
                super(itemLayoutView);
                name = (TextView) itemLayoutView
                        .findViewById(R.id.itname);
                usern = (TextView) itemLayoutView.findViewById(R.id.usern);
                ok = (Button) itemLayoutView.findViewById(R.id.ok);
                pict = (ImageView) itemLayoutView
                        .findViewById(R.id.PicView);
                ok.setOnClickListener(this);
                itemLayoutView.setOnClickListener(this);
                itemLayoutView.setTag(this);
            }

            @Override
            public void onClick(View v) {


                Toast.makeText(ItemsAdapter.context, "Item added to the cart ", Toast.LENGTH_LONG).show();
                



            }
        }
    }

