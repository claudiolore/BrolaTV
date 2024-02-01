package org.generation.italy.netfliz.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class MainController {
    
	@GetMapping			//----- /Contenuti
	public String benvenuto() {
		return "home";
	}

    @GetMapping("/contatti")
    public String contatti() {
        return "contatti";
    }
    
}
