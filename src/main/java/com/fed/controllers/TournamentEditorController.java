package com.fed.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TournamentEditorController {

    @GetMapping("/tournamenteditor")
    public String tournamenteditor(){
        return "tournamenteditor";
    }
}
