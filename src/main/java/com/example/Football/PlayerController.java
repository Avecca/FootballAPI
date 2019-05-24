package com.example.Football;


import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class PlayerController {


    private Players players = new Players();



    @RequestMapping(value = "/players", method = RequestMethod.GET)
    public List<Player> getPlayers(@RequestParam(value = "searchString", defaultValue =  "") String searchString,
                                   @RequestParam(value = "country", defaultValue = "") String country,
                                   @RequestParam(value = "status", defaultValue = "") String status){



        return players.getPlayers(searchString, country, status);
    }

    @RequestMapping(value = "/players/{id}", method = RequestMethod.GET)
    public Player getPlayer(@PathVariable("id") String id ){
        Player player = players.getPlayerById(id);

        return player;
    }

    @RequestMapping(value = "/players/{id}/salary", method = RequestMethod.GET)
    public int getLeague(@PathVariable("id") String id ){


        return players.getSlalaryByPlayerId(id);
    }



    @RequestMapping(value = "/players", method = RequestMethod.POST)
    public Player postPlayer(@RequestBody Player player){



        return players.addPlayer(player);
    }


    @RequestMapping(value = "/players/{id}", method = RequestMethod.DELETE)
    public Player deletePlayer(@PathVariable("id")String id){


        return players.deletePlayer(id);
    }


    @RequestMapping(value = "/players/{id}", method = RequestMethod.PATCH, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Player updatePlayer(@PathVariable("id")String id, @RequestBody Map<Object, Object> newInfo){

        players.updatePlayer(id, newInfo);
        Player player = players.getPlayerById(id);

        return player;
    }


}
