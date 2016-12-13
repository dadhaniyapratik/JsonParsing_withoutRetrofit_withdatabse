package com.example.pratik.jpa2.Model;

/**
 * Created by Pratik on 16-Nov-16.
 */

public class Company {
    String name;

    public String getCatchPhrase() {
        return catchPhrase;
    }

    public void setCatchPhrase(String catchPhrase) {
        this.catchPhrase = catchPhrase;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBs() {
        return bs;
    }

    public void setBs(String bs) {
        this.bs = bs;
    }

    String catchPhrase;
    String bs;

}
