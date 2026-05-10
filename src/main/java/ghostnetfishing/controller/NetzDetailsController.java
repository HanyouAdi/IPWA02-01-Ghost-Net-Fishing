package ghostnetfishing.controller;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import ghostnetfishing.dao.GeisternetzDAO;
import ghostnetfishing.dao.PersonDAO;
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
    private PersonDAO personDAO = new PersonDAO();

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

        if ("geborgen".equals(aktion)) { // Wenn die Aktion "geborgen" ist, werden Geisternetze mit dem Status "Bergung bevorstehend" geladen, die der bergenden Person zugeordnet sind, siehe User Story 3: Als Nutzer möchte ich die Bergung eines Geisternetzes übernehmen können, damit ich mich um die Bergung kümmern kann.
            String telefonnummer = bereinigeTelefonnummer(person.getTelefonnummer());

            geisternetze = geisternetzDAO.findeNachStatusUndBergendePersonTelefonnummer(
                    Status.BERGUNG_BEVORSTEHEND,
                    telefonnummer
            );
            return;
        }

        if ("verschollen".equals(aktion)) {
            geisternetze = geisternetzDAO.findeNachStatusListe(
                    Arrays.asList(Status.GEMELDET, Status.BERGUNG_BEVORSTEHEND)
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
        
        Person vorhandeneOderNeuePerson = null;
        
        if ("bergung".equals(aktion) || "verschollen".equals(aktion)) {
            vorhandeneOderNeuePerson = personDAO.findeOderSpeichere(person);
        }

        for (Geisternetz netz : ausgewaehlteGeisternetze) {
            if ("bergung".equals(aktion)) {
                netz.setBergendePerson(vorhandeneOderNeuePerson);
                netz.setStatus(Status.BERGUNG_BEVORSTEHEND);
            }

            if ("geborgen".equals(aktion)) {
                netz.setStatus(Status.GEBORGEN);
            }

            if ("verschollen".equals(aktion)) {
                netz.setVerschollenGemeldetVon(vorhandeneOderNeuePerson);
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
    
    private String bereinigeTelefonnummer(String telefonnummer) { // Methode zur Bereinigung der Telefonnummer, um sicherzustellen, dass sie in einem einheitlichen Format gespeichert wird, z. B. durch Entfernen von Leerzeichen, Bindestrichen oder Klammern, damit die Telefonnummern in der Datenbank konsistent sind und leichter verglichen werden können.
        if (telefonnummer == null) {
            return null;
        }

        return telefonnummer.trim().replaceAll("[\\s()-]", "");
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
            return "Geisternetze mit bevorstehender Bergung";
        }

        if ("verschollen".equals(aktion)) {
            return "Gemeldete oder Bergung bevorstehende Geisternetze";
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