package com.example.Football;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;

import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import jdk.nashorn.internal.parser.JSONParser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Leagues {

    private List<League> leagues;

    public Leagues() {
        this.leagues = new ArrayList<>();

        startData();



    }



    //GET methods

 /*   public List<League> getLeagues() {
        return leagues;
    }*/

 //Get all leagues or leagues with name or countrycode
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

            l = getLeaguesInCountry(country);
 /*           for (League league: leagues) {
                if (league.getCountry().toUpperCase().equals(country.toUpperCase())){
                    l.add(league);
                }
            }*/
        }

        return l;
    }

    //get the league with key(id)
    public  League getLeagueByKey(String key){

        return findLeagueByKey(key);

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
        saveLeagues();
    }

    //add a club to a league

    //TODO Add a club by adding an ID and then finding the club in the list
    //TODO FRÅGA OM DETTA
    public void addClubToLeague(String legueKey, Club club){

        for (League item : leagues) {
            if (item.getKey().equals(legueKey))
                item.addClubToLeague(club);
            // clubList.add(club);
        }

     /*   League l = findLeagueByKey(legueKey);
        l.addClubToLeague(club);*/
        saveLeagues();

    }

    //TODO maybe add multiple clubs to a league


    //PUT & PATCH Methods

    //PUT a league
    public void replaceLeagueBykey(String key, League newLeagueInfo){

        League oldLegueInfo = findLeagueByKey(key);
        leagues.remove(oldLegueInfo);

        newLeagueInfo.setKey(oldLegueInfo.getKey());

        leagues.add(newLeagueInfo);

        saveLeagues();

    }

    public  void replaceClubInLeague(String leagueKey, String clubKey, Club newClubInfo){

        Club oldClub = findClubInLeagueList(leagueKey, clubKey);
        League league = findLeagueByKey(leagueKey);
        league.deleteClubFromList(oldClub);

        newClubInfo.setKey(oldClub.getKey());

        league.addClubToLeague(newClubInfo);

        saveLeagues();
    }

    //PATCH

    //update a league with key, but ignore clubs and key

    public void updateleague(String key, Map<Object,Object> update){

        League oldLeague = findLeagueByKey(key);

        update.remove("key");
        update.remove("clubs");

        update.forEach((k, v) -> {

            // Field field = ReflectionUtils.findField(k)

            Field field = ReflectionUtils.findField(League.class, k.toString());
            field.setAccessible(true);
            ReflectionUtils.setField(field, oldLeague, v);

        });

        saveLeagues();
    }


    //DELETE methods

    //delete a league by key(id)
    public League deleteLeague(String key){

        League l = findLeagueByKey(key);
        leagues.remove(l);

        if (findLeagueByKey(key) == null){
            saveLeagues();
            return  l;
        }

        return null;

    }

    public void deleteClubList(String key){
        League l = findLeagueByKey(key);

        l.clearClubList();

        saveLeagues();

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
            saveLeagues();
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

    //get all the leagues with the same country code
    public ArrayList<League> getLeaguesInCountry(String country){

        ArrayList<League> leaguesByCountry = new ArrayList<>();

        for (League l: leagues) {
            if (l.getCountry().toUpperCase().equals(country.toUpperCase())){
                leaguesByCountry.add(l);
            }
        }
        return  leaguesByCountry;
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

    private void startData(){

/*        League l1 = new League("laliga", "La Liga", "ESP");
        League l2 = new League("premierleague", "Premier League", "EN");
        League l3 = new League("bundesliga", "Bundesliga", "DE");

        Club c1 = new Club("chelsea", "Chelsea", "EN", "CHE");
        Club c2 = new Club("tottenham", "Tottenham", "EN", "TOT");


        l2.addClubToLeague(c1);
        l2.addClubToLeague(c2);

        leagues.add(l1);
        leagues.add(l2);
        leagues.add(l3);*/

        try {
            fetchLeaguesFromFile("leagues.json");

            System.out.println(leagues);
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }

        System.out.println(leagues.size());

/*

        try {
            saveAsJsonFile("test.json");
        }
        catch (IOException e){
            e.printStackTrace();
        }
*/

    }


    private Type REVIEW_TYPE = new TypeToken<List<League>>() {}.getType();

    private void fetchLeaguesFromFile(String filename) throws FileNotFoundException{

        Gson gson = new Gson();
        JsonReader reader = new JsonReader(new FileReader(filename));
        leagues = gson.fromJson(reader, REVIEW_TYPE);  //League.class
    }

    private void saveLeagues(){
        try {
            saveAsJsonFile("leagues.json");
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    //writing test
    @SuppressWarnings("unchecked")
    private  void saveAsJsonFile(String filename) throws IOException {

/*   //FUNKAR
      ArrayList<JSONObject> jsonLeage = new ArrayList<>();

        for (League l: leagues) {

            JSONObject jsObj = new JSONObject();
            jsObj.put("key", l.getKey());
            jsObj.put("name", l.getName());
            jsObj.put("country", l.getCountry());
            jsObj.put("active", l.getActive());

            JSONArray clubs = new JSONArray();
            for (Club item: l.getClubs()) {

                JSONObject clubVars = new JSONObject();

                clubVars.put("key", item.getKey());
                clubVars.put("name", item.getName());
                clubVars.put("country", item.getCountry());
                clubVars.put("code", item.getCode());

                clubs.add(clubVars);

            }
            jsObj.put("clubs", clubs);
           // jsObj.put("clubs", clubs);

            jsonLeage.add(jsObj);
        }*/

        GsonBuilder gsBuilder = new GsonBuilder();

        Gson gson = gsBuilder.create();
        String jsObj = gson.toJson(leagues);


        //overwrite every time, append = false
        try (FileWriter file = new FileWriter(filename, false)){
            file.write(jsObj);
            System.out.println("saved to file: leagues");

/*            for (JSONObject obj:  jsonLeage) {  //jsonLeage
                file.write(obj.toJSONString());

                System.out.println("Copied to file");
                System.out.println("JSON Obj: " + obj);

            }*/

        }
    }



    //extra tries


    //reading
    // private void fetchLeaguesFromFile(){

  /*      try {

            JSONObject jsObj = (JSONObject) fetchingLeagues("test.json");
        }
        catch (Exception e){
            e.printStackTrace();
        }*/


/*
        JSONArray jsArray = new JSONArray();

        String lineFromFile;

        try(BufferedReader bReader = new BufferedReader(new FileReader("text.json"))) {

            while ((lineFromFile = bReader.readLine()) != null ){

                if (lineFromFile != null && !lineFromFile.isEmpty()) {
                    JSONObject jsObj = new JSONObject();
                    League l = new League();

                    String[] split = lineFromFile.split("\\s+");
                    jsObj.put("key", split[0]);
                    l.setKey(split[0]);
                }
            }


        } catch ( Exception e){
            e.printStackTrace();
        }*/





    // }

 /*   private Object fetchingLeagues(String filename) throws Exception{
        FileReader reader = new FileReader(filename);
        JSONParser jsonParser = new JSONParser();
       // return (JSONObject)jsonParser.parse(reader);

    }*/


 //saving

    //ObjectMapper oM = new ObjectMapper();

    // oM.readValues(new File("test.json"), League.class) = leagues;

    // ArrayList<String> blahblah = new ArrayList<>();

 /*       for (League l: leagues) {
            String test = gson.toJson(l, League.class);
        }*/
    //  ObjectMapper oM = new ObjectMapper();





}
