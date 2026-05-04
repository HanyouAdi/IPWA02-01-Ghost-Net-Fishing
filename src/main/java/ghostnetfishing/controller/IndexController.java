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
            return "person-erfassen?faces-redirect=true";
        }

        if ("geborgen".equals(ausgewaehlteAktion)) {
            return "person-erfassen?faces-redirect=true";
        }

        if ("verschollen".equals(ausgewaehlteAktion)) {
            return "person-erfassen?faces-redirect=true";
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
