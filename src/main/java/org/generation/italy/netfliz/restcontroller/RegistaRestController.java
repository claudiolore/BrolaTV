package org.generation.italy.netfliz.restcontroller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.generation.italy.netfliz.model.Regista;
import org.generation.italy.netfliz.repository.RegistaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/Registi")
public class RegistaRestController {
	@Autowired
	RegistaRepository registaRepository;
	

	@GetMapping			//----- /Registi
	public String benvenuto() {
		return "Benvenuto su registi!";
	}
//-----------------------------------------------------------------------------------------------------
	
	@GetMapping("/elenco")
	public List<Regista> elencoRegisti(
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
			return null;
		
		
		if(ordinamento!=null) {
			if(ordinamento.equalsIgnoreCase("asc")) 
				Collections.sort(elencoRegisti);
			else if(ordinamento.equalsIgnoreCase("desc"))
				Collections.sort(elencoRegisti, Collections.reverseOrder());	
		}	
		return elencoRegisti;
		
	}
//--------------------------------------------------------------------------------------------------------------	
	@GetMapping("/senzaContenuto")
	public List<Regista> registiSenzaContenuto(){
		List<Regista> elencoRegisti = registaRepository.findRegistiSenzaContenuti();
		return elencoRegisti;
	}
//---------------------------------------------------------------------------------------------------------------	
	@PostMapping("/addRegista")
	public void addRegista(	@RequestParam Object nuovoRegista) {
		
	}
//----------------------------------------------------------------------------------------------------------------	
	@GetMapping("{id}")
	public Regista dettaglioRegista(@PathVariable Integer id) {
		Optional<Regista> optRegista = registaRepository.findById(id);
		if(optRegista.isPresent())
			return optRegista.get();
		else
			return null;
	}
}
