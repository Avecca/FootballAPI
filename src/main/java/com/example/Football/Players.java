package com.example.Football;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import org.springframework.util.ReflectionUtils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Players {


    private List<Player> players;

    public Players() {
        this.players = new ArrayList<>();

        startData();
    }



    //Get methods

    public List<Player> getPlayers(String searchString, String country, String status){

        //Dont mix and match the answers

        if (searchString.isEmpty() && country.isEmpty() &&  status.isEmpty()){
            return players;
        }

        ArrayList<Player> playerList = new ArrayList<>();

        if (!searchString.isEmpty()){
            for (Player player : players) {
                if (player.getName().toLowerCase().contains(searchString.toLowerCase())){
                    playerList.add(player);

                }
            }

            return playerList;
        }

        if (!country.isEmpty()){
            for (Player player: players) {
                if (player.getCountry().toUpperCase().equals(country.toUpperCase())){
                    playerList.add(player);
                }

            }
            return playerList;
        }

        if (!status.isEmpty()){
            for (Player player: players) {
                if (player.getStatus().toLowerCase().equals(country.toLowerCase())){
                    playerList.add(player);
                }
            }

        }

        return playerList;

    }

    public Player getPlayerById(String id){

        return findPlayerById(id);
    }

    public int getSlalaryByPlayerId(String id){

        Player player = findPlayerById(id);

        return player.getSalary();
    }



    //POST methods

    public Player addPlayer(Player player){

        boolean idCheck = false;

        for (Player pl: players) {

            if (pl.getId().equals(player.getId())){
                idCheck = true;
                break;
            }

        }

        if (idCheck)
        {
            int max = Integer.parseInt(players.get(0).getId());

            for (Player pl : players) {
                if (Integer.parseInt(pl.getId()) > max){
                    max = Integer.parseInt(pl.getId());
                }

            }
            String maxString = Integer.toString(max + 1);
            player.setId(maxString);

        }


        players.add(player);
        savePlayers();

        return  player;
    }



    //PUT and PATCH methods

    public void updatePlayer(String id, Map<Object,Object> newInfo){

        Player oldPlayer = findPlayerById(id);

        newInfo.remove("id");

        newInfo.forEach((k, v) -> {

            // Field field = ReflectionUtils.findField(k)

            Field field = ReflectionUtils.findField(Player.class, k.toString());
            field.setAccessible(true);
            ReflectionUtils.setField(field, oldPlayer, v);

        });

        savePlayers();

    }

    //DELETE methods

    public Player deletePlayer(String id){
        Player pl = findPlayerById(id);

        players.remove(pl);
        savePlayers();

        if (findPlayerById(id) == null) {
            return  pl;

        }

        return null;
    }



    //class shared methods

    private Player findPlayerById(String id){

        for (Player player: players) {
            if (player.getId().equals(id))
                return player;

        }
        return null;
    }


    private void startData(){
/*
        Player p1 = new Player("0","Gareth Bale","WAL","playing", 10);
        Player p2 = new Player("1","Nacho","ESP","injured", 100);
        Player p3 = new Player("2","Harry Kane","EN","playing",1000);
        Player p4 = new Player("3","Lionel Messi","ARG","injured", 10);
        Player p5 = new Player("4","Fredrik Ljungberg","SWE","inactive", 10);
        Player p6 = new Player("5","Sergio Garcia","ESP","playing", 10);
        Player p7 = new Player("6","Marco Reus","DE","playing", 100);
        Player p8 = new Player("7","Mesut Özil","DE","injured", 99);
        Player p9 = new Player("8","Eden Hazard","BE","playing", 10);

        players.add(p1);
        players.add(p2);
        players.add(p3);
        players.add(p4);
        players.add(p5);
        players.add(p6);
        players.add(p7);
        players.add(p8);
        players.add(p9);*/

        //savePlayers();

        try {
            fetchPlayersFromFile("players.json");

            System.out.println("PLAYERS: " + players);
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }


    }
    private Type REVIEW_TYPE = new TypeToken<List<Player>>() {}.getType();

    private  void fetchPlayersFromFile(String filename) throws FileNotFoundException {
        Gson gson = new Gson();
        JsonReader reader = new JsonReader(new FileReader(filename));
        players = gson.fromJson(reader, REVIEW_TYPE);
    }
    private void savePlayers(){
        try {
            savePlayersAsJsonFile("players.json");
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private void savePlayersAsJsonFile(String filename) throws IOException {


        GsonBuilder gsBuilder = new GsonBuilder();

        Gson gson = gsBuilder.create();
        String jsObj = gson.toJson(players);


        //overwrite every time, append = false
        try (FileWriter file = new FileWriter(filename, false)){
            file.write(jsObj);
            System.out.println("saved to file: players");

        }

    }





}
