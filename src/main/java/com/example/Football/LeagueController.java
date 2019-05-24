package com.example.Football;


import org.springframework.http.MediaType;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;
import  org.springframework.web.bind.annotation.PatchMapping;

import java.awt.*;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

@RestController
public class LeagueController {


    private Leagues leagues = new Leagues();

    //http://localhost:8080/leagues?country=EN&searchString=lig
    @RequestMapping(value = "/leagues", method = RequestMethod.GET)
    public List<League> getLeagues(@RequestParam(value = "searchString", defaultValue =  "") String searchString
                                , @RequestParam(value = "country", defaultValue = "") String country){

        return leagues.getLeagues(searchString, country);
    }

    @RequestMapping(value = "/leagues/{key}", method = RequestMethod.GET)
    public League getLeague(@PathVariable("key") String key ){
        League league = leagues.getLeagueByKey(key);

        return league;
    }


    @RequestMapping(value = "/leagues/{key}/clubs", method = RequestMethod.GET)
    public List<Club> getLeaguesClubList(@PathVariable("key") String key){

        return  leagues.getClubsInLeague(key);

    }

    @RequestMapping(value = "/leagues/{key}/clubs/{clubkey}", method = RequestMethod.GET)
    public Club getClubFromLeague(@PathVariable("key") String leagueKey, @PathVariable("clubkey") String clubKey){


        return  leagues.getClubInLeagueWithKey(leagueKey, clubKey);

    }



    @RequestMapping(value = "/leagues", method = RequestMethod.POST)
    public League postLeague(@RequestBody League league){

        leagues.addLeague(league);

        return league;
    }


    @RequestMapping(value = "/leagues/{key}/clubs", method = RequestMethod.POST)
    public Club postClubToLeague(@PathVariable("key") String key, @RequestBody Club club){

        leagues.addClubToLeague(key,club);
        return club;
    }


    @RequestMapping(value = "/leagues/{key}", method =  RequestMethod.PUT)
    public League replaceLeague(@PathVariable("key")String key, @RequestBody League league){

        leagues.replaceLeagueBykey(key,league);

        return league;
    }

    @RequestMapping(value = "/leagues/{key}/clubs/{clubkey}", method =  RequestMethod.PUT)
    public Club replaceLeague(@PathVariable("key") String leagueKey, @PathVariable("clubkey") String clubKey, @RequestBody Club club){

        leagues.replaceClubInLeague(leagueKey, clubKey, club);

        return club;
    }


    @RequestMapping(value = "/leagues/{key}", method = RequestMethod.PATCH, consumes = MediaType.APPLICATION_JSON_VALUE)
    public League updateLeague(@PathVariable("key")String key, @RequestBody Map<Object, Object> update){


        leagues.updateleague(key, update);



        League league = leagues.getLeagueByKey(key);
   /*     update.remove("key");

        update.forEach((k, v) -> {

           // Field field = ReflectionUtils.findField(k)

            Field field = ReflectionUtils.findField(League.class, k.toString());
            field.setAccessible(true);
            ReflectionUtils.setField(field, league, v);

        });

        leagues.replaceLeagueBykey(key,league);*/

        return league;

    }




    @RequestMapping(value = "/leagues/{key}", method = RequestMethod.DELETE)
    public League deleteLeague(@PathVariable("key")String key){


        return leagues.deleteLeague(key);
    }

    @RequestMapping(value = "/leagues/{key}/clubs", method = RequestMethod.DELETE)
    public League deleteAllClubsFromLeague(@PathVariable("key")String key){

        leagues.deleteClubList(key);

        return leagues.getLeagueByKey(key);
    }



    @RequestMapping(value = "/leagues/{key}/clubs/{clubkey}", method = RequestMethod.DELETE)
    public Club deleteClubFromLeague(@PathVariable("key") String leagueKey, @PathVariable("clubkey") String clubKey){

        return leagues.deleteClubFromLeagueWithKeys(leagueKey, clubKey);
    }














}
