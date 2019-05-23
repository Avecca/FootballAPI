package com.example.Football;

import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Leagues {

    private List<League> leagues;

    public Leagues() {
        this.leagues = new ArrayList<>();

        startData();
    }


    private void startData(){

        League l1 = new League("laliga", "La Liga", "ESP");
        League l2 = new League("premierleague", "Premier League", "EN");
        League l3 = new League("bundesliga", "Bundesliga", "DE");

        Club c1 = new Club("chelsea", "Chelsea", "EN", "CHE");
        Club c2 = new Club("tottenham", "Tottenham", "EN", "TOT");

        l2.addClubToLeague(c1);
        l2.addClubToLeague(c2);

        leagues.add(l1);
        leagues.add(l2);
        leagues.add(l3);



    }


    //GET methods

 /*   public List<League> getLeagues() {
        return leagues;
    }*/

 //Get all leagues or leagues with name
    public List<League> getLeagues(String searchString, String country) {

        if (searchString.isEmpty() && country.isEmpty()){
            return leagues;
        }

        ArrayList<League> l = new ArrayList<>();

        if ( !searchString.isEmpty() && !country.isEmpty()) {
            for (League league : leagues) {
                if (league.getName().toLowerCase().contains(searchString.toLowerCase()) && league.getCountry().toUpperCase().equals(country.toUpperCase())) {
                    l.add(league);
                }
            }
        } else if ( !searchString.isEmpty()) {
            for (League league : leagues) {
                if (league.getName().toLowerCase().contains(searchString.toLowerCase())) {
                    l.add(league);
                }
            }
        } else  if (!country.isEmpty()) {
            for (League league: leagues) {
                if (league.getCountry().toUpperCase().equals(country.toUpperCase())){
                    l.add(league);
                }

            }
        }


        //TODO ADD Countries too?

        return l;
    }

    //get the league with key(id)
    public  League getLeagueByKey(String key){

        return findLeagueByKey(key);

    }

    //get all the leagues with the same country code TODO
    public List<League> getLeaguesInCountry(String country){

        ArrayList<League> leaguesByCountry = new ArrayList<>();

        for (League l: leagues) {
            if (l.getCountry().toUpperCase() == country.toUpperCase()){
                leaguesByCountry.add(l);
            }

        }
        return  leaguesByCountry;
    }


    //Get all the clubs in a league
    public List<Club> getClubsInLeague(String leagueKey){


        League l = findLeagueByKey(leagueKey);


        return l.getClubs();


    }

    //get a specific club in a specific league
    public Club getClubInLeagueWithKey(String leagueKey, String clubKey){

        return findClubInLeagueList(leagueKey, clubKey);

    }


    //POST methods

    //add a league
    public void addLeague(League league){
        leagues.add(league);
    }


    //add a club to a league
    public void addClubToLeague(String legueKey, Club club){

        for (League item : leagues) {
            if (item.getKey().equals(legueKey))
                item.addClubToLeague(club);
            // clubList.add(club);
        }

     /*   League l = findLeagueByKey(legueKey);
        l.addClubToLeague(club);*/


    }

    //TODO add multiple clubs to a league



    //PUT & PATCH Methods


    //PUT a league
    public void replaceLeagueBykey(String key, League newLeagueInfo){

        League oldLegueInfo = findLeagueByKey(key);
        leagues.remove(oldLegueInfo);

        newLeagueInfo.setKey(oldLegueInfo.getKey());

        leagues.add(newLeagueInfo);

    }

    public  void replaceClubInLeague(String leagueKey, String clubKey, Club newClubInfo){

        Club oldClub = findClubInLeagueList(leagueKey, clubKey);
        League league = findLeagueByKey(leagueKey);
        league.deleteClubFromList(oldClub);

        newClubInfo.setKey(oldClub.getKey());

        league.addClubToLeague(newClubInfo);
    }


    //PATCH

    public void updateleague(String key, Map<Object,Object> update){

        League oldLeague = findLeagueByKey(key);

        update.remove("key");

        update.forEach((k, v) -> {

            // Field field = ReflectionUtils.findField(k)

            Field field = ReflectionUtils.findField(League.class, k.toString());
            field.setAccessible(true);
            ReflectionUtils.setField(field, oldLeague, v);

        });




    }





    //DELETE methods

    //delete a league by key(id)
    public League deleteLeague(String key){

        League l = findLeagueByKey(key);
        leagues.remove(l);


        if (findLeagueByKey(key) == null){
            return  l;
        }

        return null;

    }

    public void deleteClubList(String key){
        League l = findLeagueByKey(key);

        l.clearClubList();


  /*      for (Club c: l.getClubs()) {
            l.deleteClubFromList(c);
        }*/

    }


    //Delete specific club from the clublist in league
    public Club deleteClubFromLeagueWithKeys(String leagueKey, String clubKey){
        League league = findLeagueByKey(leagueKey);

        Club c = findClubInLeagueList(leagueKey, clubKey);

        league.deleteClubFromList(c);

        if (findClubInLeagueList(leagueKey, clubKey) == null){
            return c;
        }

        return null;

    }



    //shared methods


    private League findLeagueByKey(String key){
        for (League i : leagues) {
            if (i.getKey().equals(key)){
                return i;
            }
        }
        return null;

    }

    private Club findClubInLeagueList(String leagueKey, String clubKey){

        League l = findLeagueByKey(leagueKey);

        if (l.getClubs().isEmpty()){
            return null;

        }

        for (Club c : l.getClubs()) {

            if (c.getKey().equals(clubKey)){
                return c;
            }

        }

        return  null;

    }





}
