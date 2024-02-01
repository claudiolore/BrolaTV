package org.generation.italy.netfliz.repository;

import org.generation.italy.netfliz.model.Attore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface AttoriRepository extends JpaRepository<Attore, Integer>{

	List<Attore> findByNome(String nome);
	List<Attore> findByCognome(String cognome);
	List<Attore> findByNazionalita(String nazionalita);
	List<Attore> findByAnnoDiNascita(int annoDiNascita);
	
}
