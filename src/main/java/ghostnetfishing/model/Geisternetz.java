package ghostnetfishing.model;

import java.util.Date;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

//@Entity
public class Geisternetz {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private Double lat; //latitude / Breitengrad
	private Double lon; //logitude / Längengrad
	private Date lokalisiertAm;
	private int geschaetzteGroesse;

	//ENUM Klasse, weil würde man String nehmen, könnten falsche Werte eingegeben werden, diese müssten validiert werden. Das spart man sich damit.
	@Enumerated(EnumType.STRING) // Sorgt dafür, dass der ENUM Wert als String abgelegt wird und nicht als Zahl (z.B. 0 = Gemeldet, 2 = Geborgen). Das kann zu falschen interpretationen führen, wenn die Reihenfolge geändert wird.
    private Status status;

    //@ManyToOne
    //@JoinColumn(name = "meldende_person_id")
    private Person meldendePerson;

    //@ManyToOne
    //@JoinColumn(name = "bergende_person_id")
    private Person bergendePerson;

    //@ManyToOne
    //@JoinColumn(name = "verschollen_gemeldet_von_id")
    private Person verschollenGemeldetVon;
	
	public Geisternetz() {

	}
	
	public Geisternetz(Double lat, Double lon, Date lokalisiertAm, int geschaetzteGroesse) {
		this.lat = lat;
		this.lon = lon;
		this.lokalisiertAm = lokalisiertAm;
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
	
	public void setLokalisiertAm(Date lokalisiertAm) {
		this.lokalisiertAm = lokalisiertAm;
	}

	public Date getLokalisiertAm() {
		return lokalisiertAm;
	}
	
	public void setGeschaetzteGroesse(int geschaetzteGroesse) {
		this.geschaetzteGroesse = geschaetzteGroesse;
	}
	
	public int getGeschaetzteGroesse() {
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
