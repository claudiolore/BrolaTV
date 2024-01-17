package org.generation.italy.netfliz.restcontroller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.generation.italy.netfliz.model.Contenuto;
import org.generation.italy.netfliz.repository.ContenutiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/Contenuti")
public class ContenutoRestController {
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
		StringBuilder elenco = new StringBuilder();
		elenco.append("Contenuti trovati: " + elencoContenuti.size());
		elenco.append("<br><br>");
		for (Contenuto c:elencoContenuti)
			elenco.append(c.toString()+ "<br>");		
		return elenco.toString();
	}	
//---------------------------------------------------------------------------------------------------------------	
	@GetMapping("{id}")
	@ResponseBody
	public String dettaglioContenuto(@PathVariable Integer id) {
		Optional<Contenuto> optContenuto = contenutiRepository.findById(id); 
		if(optContenuto.isPresent())
			return optContenuto.get().toString();
		else
			return "Contenuto non dispobile";
		
	}
}
