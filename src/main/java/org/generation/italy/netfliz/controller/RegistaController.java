package org.generation.italy.netfliz.controller;

import java.util.ArrayList;


import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.generation.italy.netfliz.model.Attore;
import org.generation.italy.netfliz.model.Contenuto;
import org.generation.italy.netfliz.model.Regista;
import org.generation.italy.netfliz.repository.ContenutiRepository;
import org.generation.italy.netfliz.repository.RegistaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/Registi")
public class RegistaController {

	@Autowired
	ContenutiRepository contenutiRepository;
	@Autowired
	RegistaRepository registaRepository;

//-----------------------------------------------------------------------------------------------------
	@GetMapping("/elenco")
	public String elencoRegisti(Model model,
		@RequestParam(required = false) String ordinamento,
		@RequestParam(required = false) String nome,
		@RequestParam(required = false) String cognome,
		@RequestParam(required = false) String nazionalita) throws Exception{
		
		ArrayList<Regista> elencoRegisti = null;
		
		if(nome==null && cognome==null && nazionalita==null)
			elencoRegisti = (ArrayList<Regista>) 
							registaRepository.findAll();
		else if(nome!=null && cognome==null && nazionalita==null)
			elencoRegisti = (ArrayList<Regista>) 
							registaRepository.findByNome(nome);
		else if(nome==null && cognome!=null && nazionalita==null)
			elencoRegisti = (ArrayList<Regista>) 
							registaRepository.findByCognome(cognome);
		else if(nome==null && cognome==null && nazionalita!=null)
			elencoRegisti = (ArrayList<Regista>) 
							registaRepository.findByNazionalita(nazionalita);
		
		else if(nome!=null && cognome!=null && nazionalita==null)
			elencoRegisti = (ArrayList<Regista>) 
							registaRepository.findByNomeAndCognome(nome, cognome);
		else if(nome!=null && cognome==null && nazionalita!=null)
			elencoRegisti = (ArrayList<Regista>) 
							registaRepository.findByNomeAndNazionalita(cognome, nazionalita);
		else if(nome==null && cognome!=null && nazionalita!=null)
			elencoRegisti = (ArrayList<Regista>) 
							registaRepository.findByCognomeAndNazionalita(cognome, nazionalita);
		
		else if(nome==null && cognome==null && nazionalita==null)
			elencoRegisti = (ArrayList<Regista>) 
							registaRepository.findByNomeAndCognomeAndNazionalita(nome, cognome, nazionalita);
		else	
			return "nonTrovato";
		
		
		if(ordinamento!=null) {
			if(ordinamento.equalsIgnoreCase("asc"))
				Collections.sort(elencoRegisti);
			else if(ordinamento.equalsIgnoreCase("desc"))
				Collections.sort(elencoRegisti, Collections.reverseOrder());
			else
				return "nonTrovato";
		}
		model.addAttribute("elenco", elencoRegisti);
		return "/registi/elenco";
	}
//--------------------------------------------------------------------------------------------------------------	
	@GetMapping("/dettaglio/{id}")
	public String dettaglioRegista(Model model, @PathVariable Integer id) {
		Optional<Regista> optRegista = registaRepository.findById(id);
		if (optRegista.isPresent()) {	//il prodotto è stato trovato
			model.addAttribute("regista", optRegista.get());
			return "/registi/dettaglio";
		}
		else
			return "nonTrovato";
	}
	
	//---------------------------------------------------------------------------------------------------------------
	@GetMapping("/nuovo")
	public String nuovoRegistaGet(Model model) {
		Regista r=new Regista();
		model.addAttribute("Regista", r);
		
		return "/registi/nuovoRegista";
	}

	@PostMapping("/nuovo")
	public String nuovoContenutoPost(@ModelAttribute("regista") Regista r) {
		registaRepository.save(r);
		
		return "redirect:/Contenuti/elenco";
	}
	
//---------------------------------------------------------------------------------------------------------------	
	
	@GetMapping("/modifica/{id}")			
	public String modificaRegistaGet(Model model, @PathVariable("id") Integer id) {
		
		Optional<Regista> optRegista=registaRepository.findById(id);
		if (optRegista.isPresent())		
		{
			Regista r= optRegista.get();
			List<Contenuto> elencoContenuti=contenutiRepository.findByOrderByTitolo();
		
			model.addAttribute("elencoContenuti", elencoContenuti);
			model.addAttribute("regista", r);
			return "/registi/modifica";
		}
		else
			return "nontrovato";
	}

	@PostMapping("/modifica")		
	public String modificaRegistaPost(@ModelAttribute("regista") Regista r ) {	
		registaRepository.save(r);
		
		return "redirect:/Registi/elenco";		
	}
//---------------------------------------------------------------------------------------------------------------
	@GetMapping("/elimina/{id}")	
	public String eliminaRegista(	@PathVariable Integer id) {
			Optional<Regista> optRegista=registaRepository.findById(id);
			if (optRegista.isPresent())		
			{
				registaRepository.deleteById(id);
				return "redirect:/Registi/elenco";	
			}
			else
				return "nontrovato";
		}


}

