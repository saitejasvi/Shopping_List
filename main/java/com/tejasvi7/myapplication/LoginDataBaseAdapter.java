package com.tejasvi7.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class LoginDataBaseAdapter
{
    static final String DATABASE_NAME = "login.db";
    private  static LoginDataBaseAdapter sInstance;
    static final int DATABASE_VERSION = 4;
    static User NewUser = User.getInstance();

    public static final int NAME_COLUMN = 1;
    // TODO: Create public field for each column in your table.
    // SQL Statement to create a new database.
    static final String DATABASE_CREATE = "create table "+"LOGIN"+
            "( " +"ID"+" integer primary key autoincrement,"+ "USERNAME  text,PASSWORD text, EMAIL text, MNUMBER text, GENDER text); ";
    static final String ITEM_LIST = "create table "+"ITEMS"+
            "( " +"ID"+" integer primary key autoincrement,"+ "NAME  text,QUANTITY text, PIC text,USERNAME text); ";
    static final String DONEITEM_LIST = "create table "+"DONEITEMS"+
            "( " +"ID"+" integer primary key autoincrement,"+ "NAME  text,QUANTITY text, PIC text,USERNAME text); ";


    // Variable to hold the database instance
    public static SQLiteDatabase db;
    // Context of the application using the database.
    private final Context context;
    // Database open/upgrade helper
    private DataBaseHelper dbHelper;
    public  LoginDataBaseAdapter(Context _context)
    {
        context = _context;
        dbHelper = new DataBaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Method to openthe Database
    public  LoginDataBaseAdapter open() throws SQLException
    {
        db = dbHelper.getWritableDatabase();
        return this;
    }

    // Method to close the Database
    public void close()
    {
        db.close();
    }

    // method returns an Instance of the Database
    public  SQLiteDatabase getDatabaseInstance()
    {
        return db;
    }
    public static synchronized LoginDataBaseAdapter getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.

        if (sInstance == null) {
            sInstance = new LoginDataBaseAdapter(context.getApplicationContext());
        }
        return sInstance;
    }
    // method to insert a record in Table
    public void insertEntry(User user)
    {


        ContentValues newValues = new ContentValues();
        // Assign values for each column.
        newValues.put("USERNAME", user.getUsername());
        newValues.put("PASSWORD",user.getPassword());
        newValues.put("EMAIL", user.getEmailID());
        newValues.put("MNUMBER",user.getmNumber());
        newValues.put("GENDER", user.getGender());




        // Insert the row into your table
        db.insert("LOGIN", null, newValues);
        Toast.makeText(context, "User Info Saved", Toast.LENGTH_LONG).show();


    }
    public void insertEntry1(Item item)
    {

        Log.d("DatbaseAdapter","here");
        ContentValues newValues1 = new ContentValues();
        // Assign values for each column.
        newValues1.put("NAME", item.getName() );
        newValues1.put("USERNAME", item.getUser().getUsername());
        newValues1.put("QUANTITY",item.getQuantity());
        newValues1.put("PIC", item.getPic());



        // Insert the row into your table
        db.insert("ITEMS", null, newValues1);
        Toast.makeText(context, "User Info Saved", Toast.LENGTH_LONG).show();


    }

    // method to delete a Record of UserName
    public int deleteEntry(String UserName)
    {

        String where="USERNAME=?";
        int numberOFEntriesDeleted= db.delete("LOGIN", where, new String[]{UserName}) ;
        Toast.makeText(context, "Number fo Entry Deleted Successfully : "+numberOFEntriesDeleted, Toast.LENGTH_LONG).show();
        return numberOFEntriesDeleted;

    }
    public static void deleteEntry1(String id)
    {

        String where="ID=?";
        db.delete("ITEMS", where, new String[]{id}) ;
    }
    public static Item addEntry(String id)
    {
        Item newItem = new Item();
        Cursor cursor = db.query("ITEMS", null, " ID=?", new String[]{id}, null, null, null);
        cursor.moveToFirst();
        newItem.quantity = cursor.getString(cursor.getColumnIndex("QUANTITY"));
        newItem.name = cursor.getString(cursor.getColumnIndex("NAME"));
        newItem.pic = cursor.getString(cursor.getColumnIndex("PIC"));
        ContentValues newValues1 = new ContentValues();
        // Assign values for each column.
        newValues1.put("NAME", newItem.getName() );
        newValues1.put("USERNAME", newItem.getUser().getUsername());
        newValues1.put("QUANTITY",newItem.getQuantity());
        newValues1.put("PIC", newItem.getPic());



        // Insert the row into your table
        db.insert("DONEITEMS", null, newValues1);
        return newItem;
    }

    // method to get the password  of userName
    public String getSinlgeEntry(String userName) {


        Cursor cursor = db.query("LOGIN", null, " USERNAME=?", new String[]{userName}, null, null, null);
        if (cursor.getCount() < 1) // UserName Not Exist
            return "NOT EXIST";
        cursor.moveToFirst();
        String password = cursor.getString(cursor.getColumnIndex("PASSWORD"));
        return password;
    }
    //this method takes username and sets gloabel user attribatues
    public static void setUser(String usern) {
        String[] projection = {
                "USERNAME",
                "PASSWORD",
                "EMAIL",
                "MNUMBER",
                "GENDER",
        };


        String selection = "USERNAME" + " = ?";
        String[] selectionArgs = {usern};


        Cursor c = db.query(
                "LOGIN",                              // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                      // don't The sort order
        );

        if (c != null && c.moveToFirst()) {
            NewUser.setUsername(c.getString(c.getColumnIndex("USERNAME")));
            NewUser.setEmailID(c.getString(c.getColumnIndex("EMAIL")));
            NewUser.setGender(c.getString(c.getColumnIndex("GENDER")));
            NewUser.setmNumber(c.getString(c.getColumnIndex("MNUMBER")));
            c.close();
        } else {
            c.close();
        }

    }

    // Get all posts in the database
    public List<Item> getUserItems(User user) {
        List<Item> items = new ArrayList<>();
        Log.d("CALLED", "GET all  items called");
        // SELECT * FROM POSTS
        // LEFT OUTER JOIN USERS
        // ON POSTS.KEY_POST_USER_ID_FK = USERS.KEY_USER_ID
       // String POSTS_SELECT_QUERY =
              //  String.format("SELECT * FROM ITEMS WHERE USERNAME = %s",
                   //     NewUser.getUsername());

        // "getReadableDatabase()" and "getWriteableDatabase()" return the same object (except under low
        // disk space scenarios)
       // Cursor cursor = db.rawQuery(POSTS_SELECT_QUERY, null);
        Cursor cursor = db.query("ITEMS", null, " USERNAME=?", new String[]{NewUser.getUsername()}, null, null, null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    Log.d("TAG", "Item Name: " + cursor.getString(cursor.getColumnIndex("NAME")) + "\nUsr NAME: " + cursor.getString(cursor.getColumnIndex("USERNAME")) +"\nItem Pic: " + cursor.getString(cursor.getColumnIndex("PIC")));
                    Item newItem = new Item();
                    newItem.name = cursor.getString(cursor.getColumnIndex("NAME"));
                    newItem.pic =  cursor.getString(cursor.getColumnIndex("PIC"));
                    newItem.quantity =  cursor.getString(cursor.getColumnIndex("QUANTITY"));
                    newItem.id=cursor.getString(cursor.getColumnIndex("ID"));
                    items.add(newItem);

                } while(cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d("TAG", "Error while trying to get posts from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return items;
    }
    public List<Item> getUserDoneItems(User user) {
        List<Item> items = new ArrayList<>();
        Log.d("CALLED", "GET all  items called");

        Cursor cursor = db.query("DONEITEMS", null, " USERNAME=?", new String[]{NewUser.getUsername()}, null, null, null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    Log.d("TAG", "Item Name: " + cursor.getString(cursor.getColumnIndex("NAME")) + "\nUsr NAME: " + cursor.getString(cursor.getColumnIndex("USERNAME")) +"\nItem Pic: " + cursor.getString(cursor.getColumnIndex("PIC")));
                    Item newItem = new Item();
                    newItem.name = cursor.getString(cursor.getColumnIndex("NAME"));
                    newItem.pic =  cursor.getString(cursor.getColumnIndex("PIC"));
                    newItem.quantity =  cursor.getString(cursor.getColumnIndex("QUANTITY"));
                    newItem.id=cursor.getString(cursor.getColumnIndex("ID"));
                    items.add(newItem);

                } while(cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d("TAG", "Error while trying to get posts from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return items;
    }

    // Method to Update an Existing Record
    public void  updateEntry(String userName,String password)
    {
        //  create object of ContentValues
        ContentValues updatedValues = new ContentValues();
        // Assign values for each Column.
        updatedValues.put("USERNAME", userName);
        updatedValues.put("PASSWORD",password);

        String where="USERNAME = ?";
        db.update("LOGIN",updatedValues, where, new String[]{userName});

    }


}







