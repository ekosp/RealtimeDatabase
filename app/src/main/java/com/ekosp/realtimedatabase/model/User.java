package com.ekosp.realtimedatabase.model;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Eko Setyo Purnomo on 06-Sep-17.
 * Find me on https://ekosp.com
 * Or email me directly to ekosetyopurnomo@gmail.com
 */

@IgnoreExtraProperties
public class User {

    public String name;
    public String email;
    public String longitude;
    public String latitude;

    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)
    public User() {
    }

    public User(String name, String email, String longi, String lati) {
        this.name = name;
        this.email = email;
        this.longitude = longi;
        this.latitude = lati;
    }
}