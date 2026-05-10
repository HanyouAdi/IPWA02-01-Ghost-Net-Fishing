package ghostnetfishing.model;

public enum Status {
	GEMELDET("Gemeldet"),
	BERGUNG_BEVORSTEHEND("Bergung bevorstehend"),
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
