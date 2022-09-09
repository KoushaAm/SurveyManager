package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;


import java.util.ArrayList;

// this class represents a user that has a username and a password
public class User implements Writable {

    private String userName;
    private String password;

    // EFFECT: constructs a User with the given userName and passW
    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
        //EventLog.getInstance().logEvent((new Event("Create an user with username '" + this.userName + "'")));
    }




    public String getUserName() {
        if (userName == "") {
            return "anonymous";
        } else {
            return userName;
        }
    }

    public String getPassword() {
        return password;
    }


    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("username", userName);
        json.put("password", password);
        return json;
    }



}
