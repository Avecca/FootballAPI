package com.example.Football;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import org.springframework.util.ReflectionUtils;
import java.io.*;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Clubs {

    private List<Club> clubs;

    public Clubs() {
        this.clubs = new ArrayList<>();

        startData();
    }



    //GET methods

    //Get all leagues or leagues with name
    public List<Club> getClubs(String searchString, String country) {

        if (searchString.isEmpty() && country.isEmpty()){
            return clubs;
        }

        ArrayList<Club> cl = new ArrayList<>();

        if ( !searchString.isEmpty() && !country.isEmpty()) {
            for (Club club : clubs) {
                if (club.getName().toLowerCase().contains(searchString.toLowerCase()) && club.getCountry().toUpperCase().equals(country.toUpperCase())) {
                    cl.add(club);
                }
            }
        } else if ( !searchString.isEmpty()) {
            for (Club club : clubs) {
                if (club.getName().toLowerCase().contains(searchString.toLowerCase())) {
                    cl.add(club);
                }
            }
        } else  if (!country.isEmpty()) {
            for (Club club : clubs) {
                if (club.getCountry().toUpperCase().equals(country.toUpperCase())){
                    cl.add(club);
                }

            }
        }


        return cl;
    }

    //get the club with key(id)
    public  Club getClubByKey(String key){

        return findClubByKey(key);

    }

    //get the clubs players
    public List<Player> getPlayersInClub(String key, String country, String status){

        Club cl = findClubByKey(key);


        if( country.isEmpty() && status.isEmpty()){
            return cl.getPlayers();
        }

        ArrayList<Player> pl = new ArrayList<>();


        if ( !status.isEmpty() && !country.isEmpty()) {
            for (Player player : cl.getPlayers()) {
                if (player.getStatus().toLowerCase().equals(status.toLowerCase()) && player.getCountry().toUpperCase().equals(country.toUpperCase())) {
                    pl.add(player);
                }
            }
        } else if ( !status.isEmpty()) {
            for (Player player : cl.getPlayers()) {
                if (player.getStatus().toLowerCase().equals(status.toLowerCase())) {
                    pl.add(player);
                }
            }
        } else  if (!country.isEmpty()) {

            for (Player player: cl.getPlayers()) {
                if (player.getCountry().toUpperCase().equals(country.toUpperCase())){
                    pl.add(player);
                }

            }
        }

        return pl;


    }

    //get specified player in club

    public Player getPlayerInClub(String key, String id){

        return findPlayerInClub(key, id);
    }




    //POST methods


    //add a club
    public  void addClub(Club club) {
        clubs.add(club);
        saveClubs();
    }


    //PUT and PATCH methods


    //update a player in a club
    public  void updatePlayerInClub(String key, String id, Map<Object, Object> newPlayerInfo){
       // Club theClub = findClubByKey(key);
        Player thePlayer = findPlayerInClub(key, id);

        newPlayerInfo.remove("id");

        newPlayerInfo.forEach((k, v) -> {

            // Field field = ReflectionUtils.findField(k)

            Field field = ReflectionUtils.findField(Player.class, k.toString());
            field.setAccessible(true);
            ReflectionUtils.setField(field, thePlayer, v);

        });

        saveClubs();

    }


    //update a clubs info

    public void updateClub(String key, Map<Object,Object> newInfo){

        Club theClub = findClubByKey(key);


        newInfo.remove("key");  //dont allow the key to change
        newInfo.remove("players"); //dont allow updating players

        newInfo.forEach((k, v) -> {

            // Field field = ReflectionUtils.findField(k)

            Field field = ReflectionUtils.findField(Club.class, k.toString());
            field.setAccessible(true);
            ReflectionUtils.setField(field, theClub, v);

        });

        saveClubs();
    }


    public void addPlayertoClub(String key, Player player){

        Club club = findClubByKey(key);

        club.addPlayer(player);

        saveClubs();

    }

    //DELETE methods


    //delete a specific club
    public Club deleteClub(String key){

        Club cl = findClubByKey(key);
        clubs.remove(cl);


        if (findClubByKey(key) == null){
            saveClubs();
            return  cl;
        }

        return null;

    }

    //delete a specific player from a club
    public Player deletePlayerFromClub(String key, String id){
        Club club = findClubByKey(key);

        Player pl = findPlayerInClub(key, id);

        club.deletePlayerFromList(pl);

        saveClubs();


        if (findPlayerInClub(key, id) == null){
            return pl;
        }

        return null;

    }





    //OTHER methods
    private Club findClubByKey(String key){
        for (Club i : clubs) {
            if (i.getKey().equals(key)){
                return i;
            }
        }
        return null;

    }

    private Player findPlayerInClub(String key, String id){

        Club club = findClubByKey(key);

        if(club.getPlayers().isEmpty()){ return null; }

        for (Player p : club.getPlayers()) {

            if(p.getId().equals(id)){
                return p;
            }

        }
        return null;
    }

    private void startData(){


/*        Club c1 = new Club("chelsea", "Chelsea", "EN", "CHE");
        Club c2 = new Club("tottenham", "Tottenham", "EN", "TOT");
        Club c3 = new Club("arsenal", "Arsenal", "EN", "ARS");

        Club c4 = new Club("realmadrid", "Real Madrid", "ESP", "RM");
        Club c5 = new Club("barcelonafc", "Barcelona FC", "ESP", "BFC");

        Club c6 = new Club("dortmund", "Dortmund", "DE", "DRM");

        Player p1 = new Player("0","Gareth Bale","WAL","playing", 10);
        Player p2 = new Player("1","Nacho","ESP","injured", 100);
        Player p3 = new Player("2","Harry Kane","EN","playing", 1000);


        c4.addPlayer(p1);
        c4.addPlayer(p2);

        c2.addPlayer(p3);


        clubs.add(c1);
        clubs.add(c2);
        clubs.add(c3);
        clubs.add(c4);
        clubs.add(c5);
        clubs.add(c6);*/

        try {
            fetchClubsFromFile("clubs.json");

            System.out.println("CLUBS: " + clubs);
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }


        //saveClubs();
    }

    private Type REVIEW_TYPE = new TypeToken<List<Club>>() {}.getType();

    private  void fetchClubsFromFile(String filename) throws FileNotFoundException{
        Gson gson = new Gson();
        JsonReader reader = new JsonReader(new FileReader(filename));
        clubs = gson.fromJson(reader, REVIEW_TYPE);
    }

    private void saveClubs(){
        try {
            saveClubsAsJsonFile("clubs.json");
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private void saveClubsAsJsonFile(String filename) throws IOException {


        GsonBuilder gsBuilder = new GsonBuilder();

        Gson gson = gsBuilder.create();
        String jsObj = gson.toJson(clubs);


        //overwrite every time, append = false
        try (FileWriter file = new FileWriter(filename, false)){
            file.write(jsObj);
            System.out.println("saved to file: clubs");

        }

    }
}
