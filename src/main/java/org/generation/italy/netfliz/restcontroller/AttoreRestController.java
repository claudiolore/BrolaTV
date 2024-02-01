package org.generation.italy.netfliz.restcontroller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import org.generation.italy.netfliz.model.Attore;
import org.generation.italy.netfliz.repository.AttoriRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/Attori")
public class AttoreRestController {
	@Autowired
	AttoriRepository attoriRepository; 
	
	@GetMapping("/elenco")
	public ArrayList<Attore> elencoAttori(	
		@RequestParam String nome,
		@RequestParam String cognome,
		@RequestParam String nazionalita,
		@RequestParam Integer annoDiNascita,
		@RequestParam String ordinamento) throws Exception{
			
		ArrayList<Attore> elencoAttori = null;
		if		(nome==null && cognome==null && nazionalita==null && annoDiNascita==null)
			elencoAttori = (ArrayList<Attore>) attoriRepository.findAll();
		else if	(nome!=null && cognome==null && nazionalita==null && annoDiNascita==null)
			elencoAttori = (ArrayList<Attore>) attoriRepository.findByNome(nome);
		else if (nome==null && cognome!=null && nazionalita==null && annoDiNascita==null)
			elencoAttori = (ArrayList<Attore>) attoriRepository.findByCognome(cognome);
		else if (nome==null && cognome==null && nazionalita!=null && annoDiNascita==null)
			elencoAttori = (ArrayList<Attore>) attoriRepository.findByNazionalita(nazionalita);
		else if (nome==null && cognome==null && nazionalita==null && annoDiNascita!=null)
			elencoAttori = (ArrayList<Attore>) attoriRepository.findByAnnoDiNascita(annoDiNascita);
		
		if(ordinamento!=null) {
			if(ordinamento.equalsIgnoreCase("asc"))
				Collections.sort(elencoAttori);
			else if(ordinamento.equalsIgnoreCase("desc"))
				Collections.sort(elencoAttori, Collections.reverseOrder());	
		}
		return elencoAttori;
	}
//--------------------------------------------------------------------------------------------------------------	
	@GetMapping("{id}")
	public Attore dettaglioAttore(@PathVariable Integer id) {
		Optional<Attore> optAttore=attoriRepository.findById(id);
		if (optAttore.isPresent()) 	//il prodotto è stato trovato
			return optAttore.get();
		else
			return null;
	}
//---------------------------------------------------------------------------------------------------------------		
	@PostMapping
	public Attore inserisciAttore (@RequestBody Attore attore) {
		return attoriRepository.save(attore);
	}
//---------------------------------------------------------------------------------------------------------------		
	@PutMapping("{id}")
	public Attore aggiornaAttore(@PathVariable Integer id, @RequestBody Attore attore) {
		Optional<Attore> results = attoriRepository.findById(id);
		if(results.isPresent()) {
			Attore a = results.get();
			if(attore.getAnnoDiNascita()!=null)
				a.setAnnoDiNascita(attore.getAnnoDiNascita());
			if(attore.getCognome()!=null)	
				a.setCognome(attore.getCognome());
			if(attore.getElencoContenuti()!=null)	
				a.setElencoContenuti(attore.getElencoContenuti());
			if(attore.getNazionalita()!=null)	
				a.setNazionalita(attore.getNazionalita());
			if(attore.getNome()!=null)	
				a.setNome(attore.getNome());
			
			return attoriRepository.save(a);	
		}
		else
			return null;
	}	
//---------------------------------------------------------------------------------------------------------------		
	@DeleteMapping("{id}")
	public void eliminaAttore(@PathVariable Integer id) {
		attoriRepository.deleteById(id);
	}
}