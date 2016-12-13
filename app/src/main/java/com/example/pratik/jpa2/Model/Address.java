package com.example.pratik.jpa2.Model;

import java.util.ArrayList;

/**
 * Created by Pratik on 16-Nov-16.
 */

public class Address {
    String street,suite,city,zipcode;

    public ArrayList<Geo> getGeos() {
        return geos;
    }

    public void setGeos(ArrayList<Geo> geos) {
        this.geos = geos;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getSuite() {
        return suite;
    }

    public void setSuite(String suite) {
        this.suite = suite;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    ArrayList<Geo> geos;
}
