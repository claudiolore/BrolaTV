package org.generation.italy.netfliz.repository;


import org.generation.italy.netfliz.model.Regista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegistaRepository extends JpaRepository<Regista, Integer>{

	List<Regista> findByNome(String nome);
	List<Regista> findByCognome(String cognome);
	List<Regista> findByNazionalita(String nazionalita);
	
	List<Regista> findByNomeAndCognome(String nome, String cognome);
	List<Regista> findByNomeAndNazionalita(String nome, String nazionalita);
	List<Regista> findByCognomeAndNazionalita(String cognome, String nazionalita);
	
	List<Regista> findByNomeAndCognomeAndNazionalita(String nome, String cognome, String nazionalita);
	
	@Query(value = " select  regista.* "
			 +" from regista left join contenuto"
			 + " on regista.id=contenuto.regista_id "
		     +" where contenuto.id is null",   nativeQuery = true )
	List<Regista> findRegistiSenzaContenuti();

	


}
