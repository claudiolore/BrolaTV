package org.generation.italy.netfliz.controller;


import java.util.ArrayList;
import java.util.Collections;

import org.generation.italy.netfliz.model.Contenuto;
import org.generation.italy.netfliz.repository.ContenutiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/Contenuti")
public class ContenutiController {

	@Autowired
	ContenutiRepository contenutiRepository;
	
	@GetMapping			//----- /Contenuti
	@ResponseBody
	public String benvenuto() {
		return "Benvenuto su Netfliz!";
	}
	
	@GetMapping	("/elenco")		//----- /Contenuti/elencoProdotti
	@ResponseBody
	public String elencoContenuti(
		@RequestParam(required = false) String titolo, 
		@RequestParam(required = false) String tipologia,
		@RequestParam(required = false) int anno,
		@RequestParam(required = false) String ordinamento,
		@RequestParam(required = false) int annoInizio,
		@RequestParam(required = false) int annoFine,
		@RequestParam(required = false) String genere) {
		
	
		ArrayList<Contenuto> elencoContenuti= null;
		if	   (titolo==null && genere==null && tipologia==null && anno==0) 
			elencoContenuti = (ArrayList<Contenuto>) contenutiRepository.findAll();

		if (ordinamento!=null)
		{
			if (ordinamento.equals("asc"))
				Collections.sort(elencoContenuti);		//ordinamento predefinito (tramite nome) in maniera crescente
			else if (ordinamento.equals("desc"))
				Collections.sort(elencoContenuti,Collections.reverseOrder());	//ordinamento predefinito (tramite nome) in maniera decrescente
			else
				return "Ordinamento non valido";
		}
		StringBuilder elenco = new StringBuilder();
		elenco.append("Contenuti trovati: " + elencoContenuti.size());
		elenco.append("<br><br>");
		for (Contenuto c:elencoContenuti)
			elenco.append(c.toString()+ "<br>");		
		return elenco.toString();
	}
	
	
	@GetMapping ("/marco")
	@ResponseBody
	public String ciaoMarco() {
		return "Benvenuto Marco!";
	}
	
}
