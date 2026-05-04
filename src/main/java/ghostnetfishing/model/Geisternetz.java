package ghostnetfishing.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
public class Geisternetz {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private Double lat; //latitude / Breitengrad
	private Double lon; //logitude / Längengrad
	private LocalDateTime gemeldetAm; // Zeitpunkt, an dem das Geisternetz gemeldet wurde;
	@Column(nullable = false)
	private Integer geschaetzteGroesse; // Integer statt int, damit null Werte möglich sind, falls die Größe nicht geschätzt werden kann oder nicht angegeben wurde.

	//ENUM Klasse, weil würde man String nehmen, könnten falsche Werte eingegeben werden, diese müssten validiert werden. Das spart man sich damit.
	@Enumerated(EnumType.STRING) // Sorgt dafür, dass der ENUM Wert als String abgelegt wird und nicht als Zahl (z.B. 0 = Gemeldet, 2 = Geborgen). Das kann zu falschen interpretationen führen, wenn die Reihenfolge geändert wird.
    private Status status;

	// Ein Geisternetz kann von einer Person gemeldet werden, aber eine Person kann auch mehrere Geisternetze melden. Daher N:1
    @ManyToOne(cascade = CascadeType.PERSIST) //CascadeType.PERSIST = Beim Speichern des Geisternetzes wird eine neue zugehörige Person automatisch mitgespeichert.
    @JoinColumn(name = "meldende_person_id") 
    private Person meldendePerson;

    @ManyToOne
    @JoinColumn(name = "bergende_person_id")
    private Person bergendePerson;

    @ManyToOne
    @JoinColumn(name = "verschollen_gemeldet_von_id")
    private Person verschollenGemeldetVon;
	
	public Geisternetz() {

	}
	
	public Geisternetz(Double lat, Double lon, LocalDateTime gemeldetAm, Integer geschaetzteGroesse) {
		this.lat = lat;
		this.lon = lon;
		this.gemeldetAm = gemeldetAm;
		this.geschaetzteGroesse = geschaetzteGroesse;
	}
	
	public void setLat(Double lat) {
		this.lat = lat;
	}
	
	public Double getLat() {
		return this.lat;
	}
	
	public void setLon(Double lon) {
		this.lon = lon;
	}
	
	public Double getLon() {
		return this.lon;
	}
	
	public void setGemeldetAm(LocalDateTime gemeldetAm) {
		this.gemeldetAm = gemeldetAm;
	}

	public LocalDateTime getGemeldetAm() {
		return gemeldetAm;
	}
	
	public void setGeschaetzteGroesse(Integer geschaetzteGroesse) {
		this.geschaetzteGroesse = geschaetzteGroesse;
	}
	
	public Integer getGeschaetzteGroesse() {
		return this.geschaetzteGroesse;
	}
	
	public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
	
	public Person getMeldendePerson() {
	    return meldendePerson;
	}

	public void setMeldendePerson(Person meldendePerson) {
	    this.meldendePerson = meldendePerson;
	}

	public Person getBergendePerson() {
	    return bergendePerson;
	}

	public void setBergendePerson(Person bergendePerson) {
	    this.bergendePerson = bergendePerson;
	}

	public Person getVerschollenGemeldetVon() {
	    return verschollenGemeldetVon;
	}

	public void setVerschollenGemeldetVon(Person verschollenGemeldetVon) {
	    this.verschollenGemeldetVon = verschollenGemeldetVon;
	}
}
