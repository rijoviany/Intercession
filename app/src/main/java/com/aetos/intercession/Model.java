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