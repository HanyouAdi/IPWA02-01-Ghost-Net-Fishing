package ghostnetfishing.model;

public enum Status {
	GEMELDET("Gemeldet"),
	IN_BERGUNG("In Bergung"), // Bergung bevorstehend
    GEBORGEN("Geborgen"),
    VERSCHOLLEN("Verschollen");
	
	private final String bezeichnung;

    Status(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    public String getBezeichnung() {
        return bezeichnung;
    }
    
}
