package com.example.Football;

import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Players {


    private List<Player> players;

    public Players() {
        this.players = new ArrayList<>();

        startData();
    }


    private void startData(){

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
        players.add(p9);
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

    }

    //DELETE methods

    public Player deletePlayer(String id){
        Player pl = findPlayerById(id);

        players.remove(pl);

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



}
