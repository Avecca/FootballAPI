package com.example.Football;


import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class ClubController {

    private Clubs clubs = new Clubs();


    @RequestMapping(value = "/clubs", method = RequestMethod.GET)
    public List<Club> getClubs(@RequestParam(value = "searchString", defaultValue =  "") String searchString
            , @RequestParam(value = "country", defaultValue = "") String country){

        return clubs.getClubs(searchString, country);
    }

    @RequestMapping(value = "/clubs/{key}", method = RequestMethod.GET)
    public Club getClub(@PathVariable("key") String key ){

        Club club = clubs.getClubByKey(key);

        return club;
    }

    @RequestMapping(value = "/clubs/{key}/players", method = RequestMethod.GET)
    public List<Player> getPlayersInClub(@PathVariable("key") String key,
                                         @RequestParam(value = "country", defaultValue = "") String country,
                                         @RequestParam(value = "status", defaultValue = "") String status){

        return  clubs.getPlayersInClub(key, country, status);

    }

    @RequestMapping(value = "/clubs/{key}/players/{id}", method = RequestMethod.GET)
    public Player getPlayerFromClub(@PathVariable("key") String key, @PathVariable("id") String id){

        return  clubs.getPlayerInClub(key,id);
    }


    @RequestMapping(value = "/clubs", method = RequestMethod.POST)
    public Club postClub(@RequestBody Club club){

        clubs.addClub(club);

        return club;
    }


    @RequestMapping(value = "/clubs/{key}/players", method = RequestMethod.POST)
    public Player postPlayerToClub(@PathVariable("key") String key, @RequestBody Player player){

        clubs.addPlayertoClub(key, player);
        return player;
    }



    @RequestMapping(value = "/clubs/{key}", method = RequestMethod.PATCH, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Club updateClub(@PathVariable("key")String key, @RequestBody Map<Object, Object> newInfo){

        clubs.updateClub(key, newInfo);
        Club club = clubs.getClubByKey(key);

        return club;
    }

    @RequestMapping(value = "/clubs/{key}/players/{id}", method = RequestMethod.PATCH, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Player updatePlayerInClub(@PathVariable("key")String key, @PathVariable("id")String id, @RequestBody Map<Object, Object> newInfo){

        clubs.updatePlayerInClub(key,id, newInfo);
        Player player = clubs.getPlayerInClub(key, id);

        return player;
    }




    @RequestMapping(value = "/clubs/{key}", method = RequestMethod.DELETE)
    public Club deleteClub(@PathVariable("key")String key){


        return clubs.deleteClub(key);
    }

    @RequestMapping(value = "/clubs/{key}/players/{id}", method = RequestMethod.DELETE)
    public Player deletePlayerFromClub(@PathVariable("key") String key, @PathVariable("id") String id){

        return clubs.deletePlayerFromClub(key, id);
    }



}
