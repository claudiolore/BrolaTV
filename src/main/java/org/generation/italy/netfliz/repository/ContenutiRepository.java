package org.generation.italy.netfliz.repository;

import org.generation.italy.netfliz.model.Contenuto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContenutiRepository extends JpaRepository<Contenuto, Integer> {
	
	List<Contenuto> findByTitolo	(String titolo);
	List<Contenuto> findByGenere	(String genere);
	List<Contenuto> findByAnno		(int anno);
	List<Contenuto> findByTipologia	(String tipologia);
	
	List<Contenuto>	findByGenereAndTitolo	(String titolo, String genere);
	List<Contenuto> findByGenereAndTipologia(String genere, String tipologia);
	List<Contenuto> findByGenereAndAnno		(String genere, int anno);
	List<Contenuto> findByTitoloAndTipologia(String titolo, String tipologia);
	List<Contenuto> findByTitoloAndAnno		(String titolo, int anno);
	List<Contenuto> findByAnnoAndTipologia	(int anno, String tipologia);
	
	List<Contenuto> findByGenereAndTitoloAndTipologia(String genere, String titolo, String tipologia);
	List<Contenuto> findByGenereAndTitoloAndAnno(String genere, String titolo, int anno);
	List<Contenuto> findByTitoloAndTipologiaAndAnno(String titolo, String tipologia, int anno);
	List<Contenuto> findByTipologiaAndGenereAndAnno(String tipologia, String genere, int anno);

	List<Contenuto> findByTitoloAndTipologiaAndGenereAndAnno(String titolo, String tipologia, String genere, int anno);
	
	List<Contenuto> findByTitoloLike(String titolo);
	
	List<Contenuto> findByAnnoBetween(int annoInizio, int annoFine);
}
