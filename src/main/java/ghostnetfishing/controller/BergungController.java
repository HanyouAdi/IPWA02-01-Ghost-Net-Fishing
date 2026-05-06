package ghostnetfishing.controller;

import java.io.Serializable;
import java.util.List;

import ghostnetfishing.dao.GeisternetzDAO;
import ghostnetfishing.model.Geisternetz;
import ghostnetfishing.model.Person;
import ghostnetfishing.model.Status;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

@Named
@ViewScoped
public class BergungController implements Serializable {

    private static final long serialVersionUID = 1L;

    private GeisternetzDAO geisternetzDAO = new GeisternetzDAO();

    private Person bergendePerson;

    private List<Geisternetz> gemeldeteGeisternetze;
    private List<Geisternetz> ausgewaehlteGeisternetze;

    public BergungController() {
        bergendePerson = new Person();
    }

    @PostConstruct
    public void init() {
        gemeldeteGeisternetze = geisternetzDAO.findeNachStatus(Status.GEMELDET);
    }

    public void bergungUebernehmen() {

        if (ausgewaehlteGeisternetze == null || ausgewaehlteGeisternetze.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(
                            FacesMessage.SEVERITY_WARN,
                            "Keine Auswahl",
                            "Bitte wählen Sie mindestens ein Geisternetz aus."
                    )
            );
            return;
        }

        for (Geisternetz netz : ausgewaehlteGeisternetze) {
            netz.setBergendePerson(bergendePerson);
            netz.setStatus(Status.IN_BERGUNG);

            geisternetzDAO.aktualisieren(netz);
        }

        FacesContext.getCurrentInstance().addMessage(
                null,
                new FacesMessage(
                        FacesMessage.SEVERITY_INFO,
                        "Bergung übernommen",
                        "Die ausgewählten Geisternetze wurden der bergenden Person zugeordnet."
                )
        );

        // Tabelle neu laden, damit übernommene Netze nicht mehr angezeigt werden
        gemeldeteGeisternetze = geisternetzDAO.findeNachStatus(Status.GEMELDET);

        // Eingaben zurücksetzen
        ausgewaehlteGeisternetze = null;
        bergendePerson = new Person();
    }

    public Person getBergendePerson() {
        return bergendePerson;
    }

    public void setBergendePerson(Person bergendePerson) {
        this.bergendePerson = bergendePerson;
    }

    public List<Geisternetz> getGemeldeteGeisternetze() {
        return gemeldeteGeisternetze;
    }

    public void setGemeldeteGeisternetze(List<Geisternetz> gemeldeteGeisternetze) {
        this.gemeldeteGeisternetze = gemeldeteGeisternetze;
    }

    public List<Geisternetz> getAusgewaehlteGeisternetze() {
        return ausgewaehlteGeisternetze;
    }

    public void setAusgewaehlteGeisternetze(List<Geisternetz> ausgewaehlteGeisternetze) {
        this.ausgewaehlteGeisternetze = ausgewaehlteGeisternetze;
    }
}