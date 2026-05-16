package ghostnetfishing.controller;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.primefaces.event.FlowEvent;

import ghostnetfishing.dao.GeisternetzDAO;
import ghostnetfishing.dao.PersonDAO;
import ghostnetfishing.model.Geisternetz;
import ghostnetfishing.model.Person;
import ghostnetfishing.model.Status;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.ValidatorException;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

@Named
@ViewScoped
public class GeisternetzMeldenController implements Serializable {
	
	private static final long serialVersionUID = 1L;

    private Geisternetz geisternetz;
    private Person meldendePerson;

    private boolean anonymMelden;

    private GeisternetzDAO geisternetzDAO = new GeisternetzDAO();
    private PersonDAO personDAO = new PersonDAO();

    public GeisternetzMeldenController() {
        geisternetz = new Geisternetz();
        meldendePerson = new Person();

        geisternetz.setStatus(Status.GEMELDET);
        geisternetz.setGemeldetAm(LocalDateTime.now());
    }

    public String onFlowProcess(FlowEvent event) { // Methode, die aufgerufen wird, wenn der Benutzer zum nächsten Schritt im Wizard wechselt
        return event.getNewStep(); 
    }

    public String speichern() {

        geisternetz.setStatus(Status.GEMELDET);
        geisternetz.setGemeldetAm(LocalDateTime.now());
        
        Geisternetz vorhandenesGeisternetz =
                geisternetzDAO.findeAktivesGeisternetzNachKoordinaten(
                        geisternetz.getLat(),
                        geisternetz.getLon()
                );
        
        if (vorhandenesGeisternetz != null) {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(
                            FacesMessage.SEVERITY_WARN,
                            "Geisternetz bereits gemeldet",
                            "An diesen Koordinaten existiert bereits ein aktives Geisternetz mit der ID "
                                    + vorhandenesGeisternetz.getId()
                                    + ". Die Meldung wurde nicht erneut gespeichert."
                    )
            );

            return null;
        }


        if (anonymMelden) {
            geisternetz.setMeldendePerson(null);
        } else {
            meldendePerson.setTelefonnummer(
                    bereinigeTelefonnummer(meldendePerson.getTelefonnummer())
            );
            
            Person vorhandeneOderNeuePerson = personDAO.findeOderSpeichere(meldendePerson);
            geisternetz.setMeldendePerson(vorhandeneOderNeuePerson);

            // geisternetz.setMeldendePerson(meldendePerson); Methode vor Änderung, das vorhandene User geändert, anstatt immer eine Person erstellt wird
        }

        geisternetzDAO.speichern(geisternetz);

        FacesContext.getCurrentInstance().addMessage(
                null,
                new FacesMessage(
                        FacesMessage.SEVERITY_INFO,
                        "Geisternetz gemeldet",
                        "Das Geisternetz wurde erfolgreich im System gespeichert."
                )
        );

        geisternetz = new Geisternetz();
        meldendePerson = new Person();
        anonymMelden = false;

        geisternetz.setStatus(Status.GEMELDET);
        geisternetz.setGemeldetAm(LocalDateTime.now());

        return null;
    }

    public void validateTelefonnummer(FacesContext context, UIComponent component, Object value)
            throws ValidatorException {

        if (anonymMelden) {
            return;
        }

        if (value == null || value.toString().trim().isEmpty()) {
            throw new ValidatorException(
                    new FacesMessage(
                            FacesMessage.SEVERITY_ERROR,
                            "Ungültige Telefonnummer",
                            "Bitte geben Sie eine Telefonnummer im internationalen Format ein, z. B. +491701234567, oder wählen Sie anonyme Meldung."
                    )
            );
        }

        String telefonnummer = value.toString().trim();

        String bereinigteTelefonnummer = telefonnummer.replaceAll("[\\s()-]", "");

        String e164Pattern = "^\\+[1-9][0-9]{1,14}$";

        if (!bereinigteTelefonnummer.matches(e164Pattern)) {
            throw new ValidatorException(
                    new FacesMessage(
                            FacesMessage.SEVERITY_ERROR,
                            "Ungültige Telefonnummer",
                            "Bitte verwenden Sie ein internationales Format, z. B. +491701234567 oder +49 170 1234567."
                    )
            );
        }
    }

    private String bereinigeTelefonnummer(String telefonnummer) {
        if (telefonnummer == null) {
            return null;
        }

        return telefonnummer.trim().replaceAll("[\\s()-]", ""); // Entfernt Leerzeichen, Bindestriche und Klammern
    }

    public Geisternetz getGeisternetz() {
        return geisternetz;
    }

    public void setGeisternetz(Geisternetz geisternetz) {
        this.geisternetz = geisternetz;
    }

    public Person getMeldendePerson() {
        return meldendePerson;
    }

    public void setMeldendePerson(Person meldendePerson) {
        this.meldendePerson = meldendePerson;
    }

    public boolean isAnonymMelden() {
        return anonymMelden;
    }

    public void setAnonymMelden(boolean anonymMelden) {
        this.anonymMelden = anonymMelden;

        if (anonymMelden) {
            this.meldendePerson.setName("Anonym");
            this.meldendePerson.setTelefonnummer(null);
        }
    }
}
