package ghostnetfishing.controller;

import java.io.Serializable;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

@Named
@ViewScoped
public class IndexController implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String ausgewaehlteAktion;
	
	public IndexController() {
	}
	
    public boolean isKeineAktionAusgewaehlt() {
        return ausgewaehlteAktion == null || ausgewaehlteAktion.isBlank();
    }

    public String weiterleiten() {
        if ("melden".equals(ausgewaehlteAktion)) {
            return "netz-melden?faces-redirect=true";
        }

        if ("bergung".equals(ausgewaehlteAktion)) {
            return "person-erfassen?faces-redirect=true&aktion=bergung"; // Weiterleitung zur Personenerfassung mit Angabe der Aktion "bergung" User Story 2
        }

        if ("geborgen".equals(ausgewaehlteAktion)) {
            return "person-erfassen?faces-redirect=true&aktion=geborgen";
        }

        if ("verschollen".equals(ausgewaehlteAktion)) {
            return "person-erfassen?faces-redirect=true&aktion=verschollen";
        }

        return null;
    }

    public String getAusgewaehlteAktion() {
        return ausgewaehlteAktion;
    }

    public void setAusgewaehlteAktion(String ausgewaehlteAktion) {
        this.ausgewaehlteAktion = ausgewaehlteAktion;
    }
    	
}
