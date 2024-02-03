package org.generation.italy.netfliz.controller;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/Attori")
public class AttoriController {
	
	@Autowired
	AttoriRepository attoriRepository; 
	@Autowired
	ContenutiRepository contenutiRepository;	
//---------------------------------------------------------------------------------------------------------------	
	@GetMapping("/elenco")
	public String elencoAttori(	Model model,
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
		model.addAttribute("elenco", elencoAttori);
		return "/attori/elenco";
	}	
//---------------------------------------------------------------------------------------------------------------
	@GetMapping("/dettaglio/{id}")
	public String dettaglioAttore(Model model, 
										@PathVariable Integer id) {
		Optional<Attore> optAttore=attoriRepository.findById(id);
		if (optAttore.isPresent()) {	
			model.addAttribute("attore", optAttore.get());
			return "/attori/dettaglio";
		}
		else
			return "/nonTrovato";
}

//---------------------------------------------------------------------------------------------------------------
	@GetMapping("/nuovo")
	public String nuovoAttoreGet(Model model) {
		Attore a=new Attore();
		List<Contenuto> elencoContenuti = contenutiRepository.findAll();
		model.addAttribute("elencoContenuti", elencoContenuti);
		model.addAttribute("attore", a);
		
		return "/attori/nuovo//---------------------------------------------------------------------------------------------------------------";
	}

	@PostMapping("/nuovo")
	public String nuovoAttorePost(@ModelAttribute("attore") Attore a) {
		attoriRepository.save(a);
		
		return "redirect:/Attori/elenco";
	}
	
//---------------------------------------------------------------------------------------------------------------	
	
	@GetMapping("/modifica/{id}")			
	public String modificaAttoreoGet(Model model, 
										@PathVariable("id") Integer id) {
		
		Optional<Attore> optAttore=attoriRepository.findById(id);
		if (optAttore.isPresent())		
		{
			Attore a= optAttore.get();
		
			List<Contenuto> elencoContenuti=contenutiRepository.findByOrderByTitolo();
		
			model.addAttribute("elencoContenuti", elencoContenuti);
			model.addAttribute("attore", a);
			return "/attori/modifica";
		}
		else
			return "/nontrovato";
	}
	
	@PostMapping("/modifica")		
	public String modificaAttorePost(@ModelAttribute("attore") Attore a ) {	
		attoriRepository.save(a);
		
		return "redirect:/Attori/elenco";		
	}
//---------------------------------------------------------------------------------------------------------------
	@GetMapping("/elimina/{id}")	
	public String eliminaAttore(	@PathVariable Integer id) {
			Optional<Attore> optAttore=attoriRepository.findById(id);
			if (optAttore.isPresent())		
			{
				contenutiRepository.deleteById(id);
				return "redirect:/Contenuti/elenco";	
			}
			else
				return "/nontrovato";
		}


}
