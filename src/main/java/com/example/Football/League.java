package com.example.Football;

import java.util.ArrayList;
import java.util.List;

public class League {

    private String key;
    private String name;
    private String country;
    private Boolean active;
    private List<Club> clubs ;


    public League() {


    }

    public League(String key, String name, String country) {
        this.key = key;
        this.name = name;
        this.country = country;
        this.active = false;
        clubs = new ArrayList<>();

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

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public List<Club> getClubs() {
        return clubs;
    }

    public void addClubToLeague(Club club) {
        clubs.add(club);
    }

    public void  deleteClubFromList(Club club){
        clubs.remove(club);

    }

    public  void clearClubList(){
        clubs.clear();
    }

    public int countClubs(){
        return clubs.size();
    }

}
