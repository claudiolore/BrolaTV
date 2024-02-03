package org.generation.italy.netfliz.repository;

import org.generation.italy.netfliz.model.Attore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface AttoriRepository extends JpaRepository<Attore, Integer>{
	List<Attore> findByOrderByNome();
	
	List<Attore> findByNome(String nome);
	List<Attore> findByCognome(String cognome);
	List<Attore> findByNazionalita(String nazionalita);
	List<Attore> findByAnnoDiNascita(Integer annoDiNascita);
    
	List<Attore> findByNomeAndCognome(String nome, String cognome);
	List<Attore> findByNomeAndNazionalita(String nome, String nazionalita);
	List<Attore> findByNomeAndAnnoDiNascita(String nome, Integer annoDiNascita);
	List<Attore> findByCognomeAndNazionalita(String cognome, String nazionalita);
	List<Attore> findByCognomeAndAnnoDiNascita(String Cognome, Integer annoDiNascita);
	List<Attore> findByNazionalitaAndAnnoDiNascita(String nazionalita, Integer annoDiNascita);

	List<Attore> findByNomeAndCognomeAndNazionalita(String nome, String cognome, String nazionalita);
	List<Attore> findByNomeAndCognomeAndAnnoDiNascita(String nome, String cognome, Integer annoDiNascita);
	List<Attore> findByCognomeAndNazionalitaAndAnnoDiNascita(String nome, String cognome, Integer annoDiNascita);
	List<Attore> findByNomeAndNazionalitaAndAnnoDiNascita(String nome, String cognome, Integer annoDiNascita);
	
	List<Attore> findByNomeAndCognomeAndNazionalitaAndAnnoDiNascita(String nome, String cognome, String nazionalita, Integer annoDiNascita);

}
