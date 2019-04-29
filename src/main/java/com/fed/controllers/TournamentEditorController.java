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

/*
*  TODO: Add a timestamp or equivalent handling to the UI
*  TODO: Add a file storage in Tournament (Array)
*  TODO: Add logic to controller to store files
*  TODO: Add GSON
*  TODO: Add masterpoint handling
* */
