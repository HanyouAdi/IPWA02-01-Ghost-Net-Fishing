package ghostnetfishing.controller;

import java.io.Serializable;
import java.util.Map;

import ghostnetfishing.model.Person;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.ValidatorException;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

@Named
@ViewScoped
public class PersonErfassenController implements Serializable {
	
	 private static final long serialVersionUID = 1L;

	    private Person person;
	    private String aktion;

	    @PostConstruct // Initialisierungsmethode, die nach der Konstruktion des Controllers aufgerufen wird, um die Person und die Aktion zu setzen
	    public void init() {
	        person = new Person();

	        Map<String, String> parameterMap = // Abrufen der Anfrageparameter, um die Aktion zu bestimmen, die der Nutzer ausführen möchte (z.B. "Bergung", "Geborgenmeldung", "Verschollenmeldung")
	                FacesContext.getCurrentInstance()
	                        .getExternalContext()
	                        .getRequestParameterMap();

	        aktion = parameterMap.get("aktion");

	        if (aktion == null || aktion.isBlank()) {
	            FacesContext.getCurrentInstance().addMessage(
	                    null,
	                    new FacesMessage(
	                            FacesMessage.SEVERITY_ERROR,
	                            "Keine Aktion gewählt",
	                            "Bitte wählen Sie zuerst eine Aktion auf der Startseite."
	                    )
	            );
	        }
	    }
	    
	    public void validateTelefonnummer(FacesContext context, UIComponent component, Object value)
	            throws ValidatorException {

	        if (value == null || value.toString().trim().isEmpty()) {
	            throw new ValidatorException(
	                    new FacesMessage(
	                            FacesMessage.SEVERITY_ERROR,
	                            "Ungültige Telefonnummer",
	                            "Bitte geben Sie eine Telefonnummer im internationalen Format ein, z. B. +491701234567."
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

	        return telefonnummer.trim().replaceAll("[\\s()-]", "");
	    }

	    public String weiterleitungZuNetzDetails() { // Methode zur Weiterleitung zu den Netzdetails, nachdem die Personendaten erfasst wurden, damit die Aktion (z.B. Bergung) in den Netzdetails weiterverarbeitet werden kann
	       
	    	 person.setTelefonnummer(
	    	            bereinigeTelefonnummer(person.getTelefonnummer())
	    	    );
	    	
	    	FacesContext.getCurrentInstance()
	                .getExternalContext()
	                .getFlash()
	                .put("person", person);

	        FacesContext.getCurrentInstance()
	                .getExternalContext()
	                .getFlash()
	                .put("aktion", aktion);

	        return "netz-details?faces-redirect=true";
	    }

	    public String getSeitentitel() { // Methode zur Bestimmung des Seitentitels basierend auf der Aktion, die der Nutzer ausführen möchte, um die Benutzeroberfläche entsprechend anzupassen
	        if ("bergung".equals(aktion)) {
	            return "Bergende Person Details erfassen";
	        }

	        if ("geborgen".equals(aktion)) {
	            return "Person Details für Geborgenmeldung erfassen";
	        }

	        if ("verschollen".equals(aktion)) {
	            return "Person Details für Verschollenmeldung erfassen";
	        }

	        return "Person erfassen";
	    }

	    public Person getPerson() {
	        return person;
	    }

	    public void setPerson(Person person) {
	        this.person = person;
	    }

	    public String getAktion() {
	        return aktion;
	    }

	    public void setAktion(String aktion) {
	        this.aktion = aktion;
	    }
}