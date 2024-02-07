package org.generation.italy.netfliz.controller;


import java.util.ArrayList;

import java.util.Collections;
import java.util.Optional;

import java.util.List;
import org.generation.italy.netfliz.model.Attore;
import org.generation.italy.netfliz.model.Contenuto;
import org.generation.italy.netfliz.repository.AttoriRepository;
import org.generation.italy.netfliz.repository.ContenutiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequestMapping("/Contenuti")
public class ContenutiController {

	@Autowired
	ContenutiRepository contenutiRepository;
	
	@Autowired
	AttoriRepository attoriRepository;
//--------------------------------------------------------------------------------------------------
	@GetMapping	("/elenco")		//----- /Contenuti/elencoProdotti
		public String elencoContenuti(
		Model model,
		@RequestParam(required = false) String titolo, 
		@RequestParam(required = false) String tipologia,
		@RequestParam(required = false) Integer anno,
		@RequestParam(required = false) String ordinamento,
		@RequestParam(required = false) Integer annoInizio,
		@RequestParam(required = false) Integer annoFine,
		@RequestParam(required = false) String genere) throws Exception{
		
	 
		ArrayList<Contenuto> elencoContenuti= null;
		if	   (titolo==null && genere==null && tipologia==null && anno==null) 
			elencoContenuti = (ArrayList<Contenuto>) contenutiRepository.findAll();
		else if(titolo!=null && genere==null && tipologia==null && anno==null)
			elencoContenuti = (ArrayList<Contenuto>) contenutiRepository.findByTitoloLike("%"+titolo+"%"); //Ricerca tramite parola chiave
		else if(titolo==null && genere!=null && tipologia==null && anno==null)
			elencoContenuti = (ArrayList<Contenuto>) contenutiRepository.findByGenere(genere);
		else if(titolo==null && genere==null && tipologia!=null && anno==null)
			elencoContenuti = (ArrayList<Contenuto>) contenutiRepository.findByTipologia(tipologia);
		else if(titolo==null && genere==null && tipologia==null && anno!=null)
			elencoContenuti = (ArrayList<Contenuto>) contenutiRepository.findByAnno(anno);
		
		else if(titolo!=null && genere!=null && tipologia==null && anno==null)
			elencoContenuti = (ArrayList<Contenuto>) contenutiRepository.findByGenereAndTitolo(genere, titolo);
		else if(titolo!=null && genere==null && tipologia!=null && anno==null)
			elencoContenuti = (ArrayList<Contenuto>) contenutiRepository.findByTitoloAndTipologia(titolo, tipologia);
		else if(titolo!=null && genere==null && tipologia==null && anno!=null)
			elencoContenuti = (ArrayList<Contenuto>) contenutiRepository.findByTitoloAndAnno(titolo, anno);
		
		else if(titolo==null && genere!=null && tipologia!=null && anno==null)
			elencoContenuti = (ArrayList<Contenuto>) contenutiRepository.findByGenereAndTipologia(genere, tipologia);
		else if(titolo==null && genere!=null && tipologia==null && anno!=null)
			elencoContenuti = (ArrayList<Contenuto>) contenutiRepository.findByGenereAndAnno(genere, anno);
		
		else if(titolo==null && genere==null && tipologia!=null && anno!=null)
			elencoContenuti = (ArrayList<Contenuto>) contenutiRepository.findByAnnoAndTipologia(anno, tipologia);
		
		else if(titolo!=null && genere!=null && tipologia!=null && anno!=null)
			elencoContenuti = (ArrayList<Contenuto>) contenutiRepository.findAll();
		
		else if(anno >= annoInizio && anno <= annoFine)
			elencoContenuti = (ArrayList<Contenuto>) contenutiRepository.findByAnnoBetween(annoInizio, annoFine); //ricerca tramite intervallo di anni
		else
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
		model.addAttribute("elenco", elencoContenuti);
		return "/contenuti/elenco";
	}	
//---------------------------------------------------------------------------------------------------------------
	@GetMapping("/dettaglio/{id}")
	public String dettaglioContenuto(Model model, 
										@PathVariable Integer id) {
		Optional<Contenuto> optContenuto=contenutiRepository.findById(id);
		if (optContenuto.isPresent()) {	//il contenuto è stato trovato
			
			model.addAttribute("contenuto", optContenuto.get());
			return "/contenuti/dettaglio";
		}
		else
			return "/nonTrovato";
}

//---------------------------------------------------------------------------------------------------------------
	@GetMapping("/nuovo")
	public String nuovoContenutoGet(Model model) {
		Contenuto c=new Contenuto();
		List<Attore> elencoAttori = attoriRepository.findAll();
		model.addAttribute("elencoAttori", elencoAttori);
		model.addAttribute("contenuto", c);
		
		return "/contenuti/nuovoContenuto";
	}

	@PostMapping("/nuovo")
	public String nuovoContenutoPost(@ModelAttribute("contenuto") Contenuto c) {
		
		contenutiRepository.save(c);
		return "redirect:/Contenuti/elenco";
	}
	
//---------------------------------------------------------------------------------------------------------------	
	
	@GetMapping("/modifica/{id}")			
	public String modificaContenutoGet(Model model, @PathVariable("id") Integer id) {
		
		Optional<Contenuto> optContenuto=contenutiRepository.findById(id);
		if (optContenuto.isPresent())		
		{
			Contenuto c= optContenuto.get();
		
			List<Attore> elencoAttori=attoriRepository.findByOrderByNome();
		
			model.addAttribute("elencoAttori", elencoAttori);
			model.addAttribute("contenuto", c);
			return "/contenuti/modifica";
		}
		else
			return "nontrovato";
	}
	
	@PostMapping("/modifica")		
	public String modificaContenutoPost(@ModelAttribute("contenuto") Contenuto c ) {	
		contenutiRepository.save(c);
		
		return "redirect:/Contenuti/elenco";		
	}
//---------------------------------------------------------------------------------------------------------------
	@GetMapping("/elimina/{id}")	
	public String eliminaContenuto(	@PathVariable Integer id) {
			Optional<Contenuto> optContenuto=contenutiRepository.findById(id);
			if (optContenuto.isPresent())		
			{
				contenutiRepository.deleteById(id);
				return "redirect:/Contenuti/elenco";	
			}
			else
				return "nontrovato";
		}


}
