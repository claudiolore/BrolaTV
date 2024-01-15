package org.generation.italy.netfliz.model;

import org.generation.italy.netfliz.model.Contenuto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Contenuto implements Comparable<Contenuto> {
	@Id		//è una chiave primaria
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
		private	int id;
	
	@Column(nullable = false, length = 50)
		private String titolo;
	
	@Column(nullable = false, length = 60)
		private String tipologia;
	
	@Column(length = 60)
		private String genere;
	
	@Column(nullable = false)
		private int anno;
	
	@Column(nullable = false)	
		private int durata;

	
	public Contenuto() {
		super();
	}

	public Contenuto(int id, String titolo, String tipologia, String genere, int anno, int durata) {
		super();

		this.titolo = titolo;
		this.tipologia = tipologia;
		this.genere = genere;
		this.anno = anno;
		this.durata = durata;
	}

	public int getId() {
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

	public int getAnno() {
		return anno;
	}

	public int getDurata() {
		return durata;
	}

	@Override
	public String toString() {
		return "Contenuto [id= " + id + ", titolo= " + titolo + ", tipologia= " + tipologia + ", genere= " + genere
				+ ", anno= " + anno + ", durata= " + durata + "]";
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
