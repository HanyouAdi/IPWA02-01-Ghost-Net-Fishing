package ghostnetfishing.model;

public enum Status {
	GEMELDET("Gemeldet"),
	IN_BERGUNG("IN_BERGUNG"),
    GEBORGEN("Geborgen"),
    VERSCHOLLEN("Verschollen");
	
	private final String bezeichnung;

    Status(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    public String getBezeichnung() {
        return bezeichnung;
    }
    /*
    public boolean istAbgeschlossen() {
        return this == GEBORGEN || this == VERSCHOLLEN;
    }

    public boolean istNochZuBearbeiten() {
        return this == GEMELDET || this == BERGUNG_BEVORSTEHEND;
    }
    
    Für Abfrageen im Code:
    if (netz.getStatus().istAbgeschlossen()) {
    // Netz ist entweder geborgen oder verschollen
}
     */
}
