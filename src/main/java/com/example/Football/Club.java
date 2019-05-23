package com.example.Football;

import java.util.List;

public class Club {

    private String key;
    private String name;
    private String country;
    private String code;
    private Boolean inRunning;
   // private List<Club> clubs ;

    public Club(){

    }


    public Club(String key, String name, String country, String code) {
        this.key = key;
        this.name = name;
        this.country = country;
        this.code = code;
        this.inRunning = true;
    }


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
