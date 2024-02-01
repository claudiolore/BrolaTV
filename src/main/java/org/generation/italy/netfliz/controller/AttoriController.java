package org.generation.italy.netfliz.controller;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import org.generation.italy.netfliz.model.Attore;
import org.generation.italy.netfliz.model.Contenuto;
import org.generation.italy.netfliz.repository.AttoriRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/Attori")
public class AttoriController {
	@Autowired
	AttoriRepository attoriRepository; 
	
	@GetMapping("/elenco")
	@ResponseBody
	public String elencoAttori(	
		@RequestParam String nome,
		@RequestParam String cognome,
		@RequestParam String nazionalita,
		@RequestParam Integer annoDiNascita,
		@RequestParam String ordinamento) throws Exception{
			
		ArrayList<Attore> elencoAttori = null;
		if		(nome==null && cognome==null && nazionalita==null && annoDiNascita==null)
			elencoAttori = (ArrayList<Attore>) attoriRepository.findAll();
		else if	(nome!=null && cognome==null && nazionalita==null && annoDiNascita==0)
			elencoAttori = (ArrayList<Attore>) attoriRepository.findByNome(nome);
		else if (nome==null && cognome!=null && nazionalita==null && annoDiNascita==0)
			elencoAttori = (ArrayList<Attore>) attoriRepository.findByCognome(cognome);
		else if (nome==null && cognome==null && nazionalita!=null && annoDiNascita==0)
			elencoAttori = (ArrayList<Attore>) attoriRepository.findByNazionalita(nazionalita);
		else if (nome==null && cognome==null && nazionalita==null && annoDiNascita!=0 )
			elencoAttori = (ArrayList<Attore>) attoriRepository.findByAnnoDiNascita(annoDiNascita);
		
		if(ordinamento!=null) {
			if(ordinamento.equalsIgnoreCase("asc"))
				Collections.sort(elencoAttori);
			else if(ordinamento.equalsIgnoreCase("desc"))
				Collections.sort(elencoAttori, Collections.reverseOrder());
			else
				return "Ordinamento non valido";	
		}
		StringBuilder elencoAttoriBuilder = new StringBuilder();
		elencoAttoriBuilder.append("Registi trovati: " + elencoAttori.size());
		elencoAttoriBuilder.append("<br><br>");
		for(Attore a:elencoAttori) {
			elencoAttoriBuilder.append(a.toString() + "<br>");
			elencoAttoriBuilder.append("Contenuti :");
			for(Contenuto c: a.getElencoContenuti())
				elencoAttoriBuilder.append("--" + c.toString());
				elencoAttoriBuilder.append("<br>");
		}		
		return elencoAttoriBuilder.toString();
	}
//--------------------------------------------------------------------------------------------------------------	
	@GetMapping("{id}")
	@ResponseBody
	public String dettaglioAttore(@PathVariable Integer id) {
		Optional<Attore> optAttore = attoriRepository.findById(id);
		if (optAttore.isPresent()) {	//il prodotto è stato trovato
			Attore a = optAttore.get();
			StringBuilder elenco=new StringBuilder(a.toString()+"<br>");
			elenco.append("Contenuti: <br>");
			for(Contenuto c: a.getElencoContenuti())
				elenco.append("--"+c.toString()+"<br>");
			elenco.append("<br>");
			return elenco.toString();
		}
		else
			return "Prodotto non trovato!";
	}
	
	
}
