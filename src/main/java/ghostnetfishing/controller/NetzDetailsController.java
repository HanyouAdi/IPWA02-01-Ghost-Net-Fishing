package ghostnetfishing.controller;

import java.io.Serializable;
import java.util.Arrays;
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
public class NetzDetailsController implements Serializable { //zu NetzDetailsController hinzugefügt, damit Bergung von dort aus möglich ist, siehe User Story 3: Als Nutzer möchte ich die Bergung eines Geisternetzes übernehmen können, damit ich mich um die Bergung kümmern kann.

    private static final long serialVersionUID = 1L;

    private GeisternetzDAO geisternetzDAO = new GeisternetzDAO();

    private Person person;
    private String aktion;

    private List<Geisternetz> geisternetze;
    private List<Geisternetz> ausgewaehlteGeisternetze;

    @PostConstruct
    public void init() {
        Object personObjekt = FacesContext.getCurrentInstance()
                .getExternalContext()
                .getFlash()
                .get("person");

        Object aktionObjekt = FacesContext.getCurrentInstance()
                .getExternalContext()
                .getFlash()
                .get("aktion");

        if (personObjekt instanceof Person) {
            person = (Person) personObjekt;
        } else {
            person = new Person();
        }

        if (aktionObjekt instanceof String) {
            aktion = (String) aktionObjekt;
        }

        ladeGeisternetze();
    }

    private void ladeGeisternetze() {
        if ("bergung".equals(aktion)) {
            geisternetze = geisternetzDAO.findeNachStatus(Status.GEMELDET);
            return;
        }

        if ("geborgen".equals(aktion)) {
            geisternetze = geisternetzDAO.findeNachStatus(Status.IN_BERGUNG);
            return;
        }

        if ("verschollen".equals(aktion)) {
            geisternetze = geisternetzDAO.findeNachStatusListe(
                    Arrays.asList(Status.GEMELDET, Status.IN_BERGUNG)
            );
            return;
        }

        geisternetze = List.of();
    }

    public void aktionSpeichern() {
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
            if ("bergung".equals(aktion)) {
                netz.setBergendePerson(person);
                netz.setStatus(Status.IN_BERGUNG);
            }

            if ("geborgen".equals(aktion)) {
                netz.setStatus(Status.GEBORGEN);
            }

            if ("verschollen".equals(aktion)) {
                netz.setVerschollenGemeldetVon(person);
                netz.setStatus(Status.VERSCHOLLEN);
            }

            geisternetzDAO.aktualisieren(netz);
        }

        FacesContext.getCurrentInstance().addMessage(
                null,
                new FacesMessage(
                        FacesMessage.SEVERITY_INFO,
                        "Änderung gespeichert",
                        "Die ausgewählten Geisternetze wurden erfolgreich aktualisiert."
                )
        );

        ladeGeisternetze();
        ausgewaehlteGeisternetze = null;
    }

    public String getSeitentitel() {
        if ("bergung".equals(aktion)) {
            return "Bergung übernehmen";
        }

        if ("geborgen".equals(aktion)) {
            return "Geisternetz als geborgen melden";
        }

        if ("verschollen".equals(aktion)) {
            return "Geisternetz als verschollen melden";
        }

        return "Geisternetze";
    }

    public String getTabellenTitel() {
        if ("bergung".equals(aktion)) {
            return "Gemeldete Geisternetze";
        }

        if ("geborgen".equals(aktion)) {
            return "Geisternetze in Bergung";
        }

        if ("verschollen".equals(aktion)) {
            return "Gemeldete oder in Bergung befindliche Geisternetze";
        }

        return "Geisternetze";
    }

    public String getButtonText() {
        if ("bergung".equals(aktion)) {
            return "Bergung übernehmen";
        }

        if ("geborgen".equals(aktion)) {
            return "Als geborgen speichern";
        }

        if ("verschollen".equals(aktion)) {
            return "Als verschollen speichern";
        }

        return "Speichern";
    }

    public Person getPerson() {
        return person;
    }

    public String getAktion() {
        return aktion;
    }

    public List<Geisternetz> getGeisternetze() {
        return geisternetze;
    }

    public List<Geisternetz> getAusgewaehlteGeisternetze() {
        return ausgewaehlteGeisternetze;
    }

    public void setAusgewaehlteGeisternetze(List<Geisternetz> ausgewaehlteGeisternetze) {
        this.ausgewaehlteGeisternetze = ausgewaehlteGeisternetze;
    }
}