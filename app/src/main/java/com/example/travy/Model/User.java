package com.example.travy.Model;

import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

import java.util.Date;

public class User extends SugarRecord<User> {
    public int backendId;

    public Date created_at;
    public Date updated_at;

    public String name;
    public String email;

    public String authToken;
    public Date tokenExpiration;

    //Required by Sugar
    public User() {
        name = "";
        email = "";
        created_at = new Date();
        updated_at = new Date();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("\n\t id: " + backendId);
        sb.append("\n\t name: " + name);
        sb.append("\n\t email: " + email);
        sb.append("\n\t authToken: " + authToken);
        sb.append("\n\t tokenExpiration: " + tokenExpiration.toString());

        return sb.toString();
    }
}
