package com.tejasvi7.myapplication;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.media.Image;

import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

import static android.app.Activity.RESULT_OK;


public class Add extends Fragment {
    EditText item, quantity;
    LoginDataBaseAdapter loginDataBaseAdapter;
    ImageView image;
    Item newitem=new Item();

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        /**
         * Inflate the layout for this fragment
         */
        newitem.user = User.getInstance();
        Log.d("add class",newitem.user.getUsername());
        View view= inflater.inflate(
                R.layout.activity_add, container, false);
        Button b = (Button) view.findViewById(R.id.buttonAdd);
        item= (EditText) view.findViewById(R.id.iname);
        quantity=(EditText) view.findViewById(R.id.quantity1);
        Button TakePicBut = (Button) view.findViewById(R.id.buttonTakePic);
        image = (ImageView) view.findViewById(R.id.imageView);

        loginDataBaseAdapter=new LoginDataBaseAdapter(getActivity());
        loginDataBaseAdapter=loginDataBaseAdapter.open();
        // Take a picture button
        TakePicBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginDataBaseAdapter.getInstance(getActivity()).getUserItems(newitem.user);
                dispatchTakePictureIntent();
            }
        });


        b.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String Item1=item.getText().toString();
                String Quantity=quantity.getText().toString();

                Log.d("1",Quantity);
                if(item.length()==0 || quantity.length()==0){
                    Toast.makeText(getActivity().getApplicationContext(), "Enter values", Toast.LENGTH_SHORT).show();
                }

                else {
                    // Save the Data in Database
                    newitem.name=Item1;
                    newitem.quantity=Quantity;
                    loginDataBaseAdapter.insertEntry1(newitem);
                    item.setText("");
                    quantity.setText("");
                }
            }
        });

return view;
    }

    private void dispatchTakePictureIntent() {

            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity( getActivity().getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, 1);
            }


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("add activityresult",newitem.user.getUsername());

        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            //Convert bitmap to byte array
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream .toByteArray();
            //Encode base64 from byte array
            newitem.pic = Base64.encodeToString(byteArray, Base64.DEFAULT);

            // set view thumbnail
            image.setImageBitmap(imageBitmap);
        }}
}