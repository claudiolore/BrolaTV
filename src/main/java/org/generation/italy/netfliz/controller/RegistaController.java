package org.generation.italy.netfliz.controller;

import java.util.ArrayList;


import java.util.Collections;
import java.util.Optional;

import org.generation.italy.netfliz.model.Attore;
import org.generation.italy.netfliz.model.Contenuto;
import org.generation.italy.netfliz.model.Regista;
import org.generation.italy.netfliz.repository.RegistaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/Registi")
public class RegistaController {

	@Autowired
	RegistaRepository registaRepository;
//--------------------------------------------------------------------------------------------------
	
	@GetMapping			//----- /Registi
	@ResponseBody
	public String benvenuto() {
		return "Benvenuto su registi!";
	}
//-----------------------------------------------------------------------------------------------------
	
	@GetMapping("/elenco")
	@ResponseBody
	public String elencoRegisti(
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
			return "Inserimento non valido";
		
		
		if(ordinamento!=null) {
			if(ordinamento.equalsIgnoreCase("asc"))
				Collections.sort(elencoRegisti);
			else if(ordinamento.equalsIgnoreCase("desc"))
				Collections.sort(elencoRegisti, Collections.reverseOrder());
			else
				return "Ordinamento non valido";
		}
		StringBuilder elencoRegistiBuilder = new StringBuilder();
		elencoRegistiBuilder.append("Registi trovati: " + elencoRegisti.size());
		elencoRegistiBuilder.append("<br><br>");
		for(Regista r: elencoRegisti) {
			elencoRegistiBuilder.append("<br>" + r.toString() + "<br>");
			for(Contenuto c: r.getElencoContenuti()) {
				elencoRegistiBuilder.append("------------" + c.toString()+"<br>");
				for(Attore a: c.getElencoAttori() )
					elencoRegistiBuilder.append("-----------------------" + a.toString()+"<br>");
			}	
		}		
		return elencoRegistiBuilder.toString();
	}
//--------------------------------------------------------------------------------------------------------------	
	@GetMapping("{id}")
	@ResponseBody
	public String dettaglioRegista(@PathVariable Integer id) {
		Optional<Regista> optRegista = registaRepository.findById(id);
		if (optRegista.isPresent()) {	//il prodotto è stato trovato
			Regista r = optRegista.get();
			StringBuilder elenco=new StringBuilder(r.toString()+"<br>");
			elenco.append("Contenuti: <br>");
			for(Contenuto c: r.getElencoContenuti())
				elenco.append("--"+c.toString()+"<br>");
			elenco.append("<br>");
			return elenco.toString();
		}
		else
			return "Prodotto non trovato!";
	}
	
	
}
