package com.tejasvi7.myapplication;

/**
 * Created by tejasvi7 on 10/15/2016.
 */

public class Item {

public String id;
        public User user;
        public String name;
        public String pic;
        public int done;
    public User getUser(){
        user=User.getInstance();
        return user;
    }
public String quantity;

        public String getName() {
            return name;
        }

    public String getid() {
        return id;
    }

    public String getQuantity() {
        return quantity;
    }

        public String getPic() {
            return pic;
        }

        public int getDone() {
            return done;
        }

}
