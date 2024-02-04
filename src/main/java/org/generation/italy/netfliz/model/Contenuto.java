package org.generation.italy.netfliz.model;

import java.util.List;

import org.generation.italy.netfliz.model.Contenuto;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

@Entity
public class Contenuto implements Comparable<Contenuto> {
	@Id		//è una chiave primaria
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
		private	int id;
	
	@Column(nullable = false, length = 60)
		private String titolo;
	@Column(nullable = false, length = 60)
		private String tipologia;
	@Column(length = 60)
		private String genere;
	@Column(nullable = false)
		private Integer anno;
	@Column(nullable = false)	
		private Integer durata;
	
		private String copertina;
	
	@ManyToOne(optional = false)
	private Regista regista;
	
	@ManyToMany(mappedBy = "elencoContenuti", cascade = CascadeType.ALL)
	private List<Attore> elencoAttori;
//----------------------------------------------------------------------------------------------------------------	
	public Contenuto() {
		super();
	}

	public Contenuto(int id, String titolo, String tipologia, String genere, Integer anno, Integer durata) {
		super();

		this.titolo = titolo;
		this.tipologia = tipologia;
		this.genere = genere;
		this.anno = anno;
		this.durata = durata;
	}
	//----------------------------------------------------------------------------------------------------------------	
	public Integer getId() {
		return id;
	}

	public String getTitolo() {
		return titolo;
	}

	public String getTipologia() {
		return tipologia;
	}

	public String getGenere() {
		return genere;
	}

	public Integer getAnno() {
		return anno;
	}

	public Integer getDurata() {
		return durata;
	}
	
	
	public List<Attore> getElencoAttori() {
		return elencoAttori;
	}
	
	
	public Regista getRegista() {
		return regista;
	}
	
	public String getCopertina() {
		return copertina;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public void setTipologia(String tipologia) {
		this.tipologia = tipologia;
	}

	public void setGenere(String genere) {
		this.genere = genere;
	}

	public void setAnno(Integer anno) {
		this.anno = anno;
	}

	public void setDurata(Integer durata) {
		this.durata = durata;
	}

	public void setRegista(Regista regista) {
		this.regista = regista;
	}

	public void setElencoAttori(List<Attore> elencoAttori) {
		this.elencoAttori = elencoAttori;
	}
	
	public void setCopertina(String copertina) {
		this.copertina = copertina;
	}

	//----------------------------------------------------------------------------------------------------------------	
	@Override
	public String toString() {
		return "Contenuto [id = " + id 
				+ ", titolo = " + titolo 
				+ ", tipologia = " + tipologia 
				+ ", genere = " + genere
				+ ", anno = " + anno 
				+ ", durata = " + durata + "]";
	}
	
	@Override
	public int compareTo(Contenuto contenuto) {		
		if (this.titolo.compareTo(contenuto.getTitolo()) != 0)	//se i titoli non sono uguali
			return this.titolo.compareTo(contenuto.getTitolo());
		else {								//se i nomi sono uguali ordino per anno
			if (this.anno>contenuto.anno)
				return 1;
			else if	(this.anno<contenuto.anno)
				return -1;
			else
				return 0;
		}
	}
	
}
