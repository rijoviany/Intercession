package com.aetos.intercession;

/**
 * Created by Rijo__JR on 2/17/2015.
 */

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("Intercessions")
public class Model extends ParseObject {
    public Model() {

    }

    public static String key = "IvVwtSwglXXDrwffWiWroeyNwSfI6yNkKQpLJNs8";
    public static String key2 = "W9I7XC4HTBK1RX661sf7ar8jvGJ0AUJJ21wSdyyh";

    public String getPrayer() {
        return getString("prayer");
    }

    public void setPrayer(String prayer) {
        put("prayer", prayer);
    }

    public String getName() {
        return getString("name");
    }

    public void setName(String Name) {
        put("name", Name);
    }

    public String getPlace() {
        return getString("place");
    }

    public void setPlace(String Place) {
        put("place", Place);
    }

    public Integer getCount() {
        return getInt("Count");
    }

    public void setCount(Integer Count) {
        put("Count", Count);
    }
}