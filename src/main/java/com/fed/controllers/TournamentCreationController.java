package com.fed.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TournamentCreationController {

    @GetMapping("/tournamentcreation")
    public String tournamentcreation(){
        return "tournamentcreation";
    }
}
