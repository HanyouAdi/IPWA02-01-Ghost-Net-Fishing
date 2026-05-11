# Report Ghost Nets

## Projektbeschreibung

Die Anwendung wurde im Rahmen einer Fallstudie für den Kurs IPWA01-01, Aufgabe 3 Ghost Net Fishing entwickelt und dient als erster funktionaler Prototyp.
Verwendete Daten, Personen und Telefonnummern sind fiktiv.

Report Ghost Nets ist eine prototypische Webanwendung zur Meldung und Koordinierung von Geisternetzen im Meer. Die Anwendung ermöglicht es Nutzenden, Geisternetze mit Positionsdaten (GPS) zu melden, eine Bergung zu übernehmen sowie Geisternetze als geborgen oder verschollen zu kennzeichnen.

## Funktionen

Die Anwendung unterstützt folgende Kernfunktionen:

- Meldung eines Geisternetzes mit Breitengrad, Längengrad und geschätzter Größe - im GPS Exchange Format (GPX)
- optionale Erfassung der meldenden Person
- Erfassung einer bergenden Person
- Übernahme der Bergung eines oder mehrerer gemeldeter Geisternetze
- Kennzeichnung übernommener Geisternetze als geborgen
- Kennzeichnung gemeldeter oder zur Bergung vorgesehener Geisternetze als verschollen
- Wiederverwendung bereits vorhandener Personen anhand der Telefonnummer
- tabellarische Darstellung und Mehrfachauswahl von Geisternetzen
- Prüfung auf doppelte aktive Geisternetzmeldungen anhand gleicher Koordinaten

## Statusmodell

Ein Geisternetz kann folgende Status besitzen:

| Status                    | Bedeutung                                                                 |
|                           |                                                                           |
| `GEMELDET`                | Das Geisternetz wurde gemeldet und ist noch keiner Bergung zugeordnet.    |
| `BERGUNG_BEVORSTEHEND`    | Eine Person hat die Bergung übernommen.                                   |
| `GEBORGEN`                | Das Geisternetz wurde erfolgreich geborgen.                               |
| `VERSCHOLLEN`             | Das Geisternetz ist nicht mehr auffindbar.                                |

## Verwendete Technologien

- Java
- Jakarta Faces / JavaServer Faces
- PrimeFaces
- Jakarta Persistence API / Hibernate
- MySQL / MariaDB über XAMPP
- Bootstrap
- XHTML
- CSS
- Maven
- Apache Tomcat

## Projektstruktur

IPWA02-01-ghost-net-fishing/
├── database/
│   └── ghostnet_db.sql
├── src/main/java/ghostnetfishing/
│   ├── controller/
│   │   ├── GeisternetzMeldenController.java
│   │   ├── IndexController.java
│   │   ├── NetzDetailsController.java
│   │   └── PersonErfassenController.java
│   ├── dao/
│   │   ├── GeisternetzDAO.java
│   │   └── PersonDAO.java
│   └── model/
│       ├── Geisternetz.java
│       ├── Person.java
│       └── Status.java
├── src/main/webapp/
│   ├── resources/
│   │   ├── bootstrap/
│   │   ├── css/styles.css
│   │   └── img/
│   │       ├── favicon/favicon-npo.ico
│   │       └── logo-npo.png
│   ├── WEB-INF/
│   │   ├── beans.xml
│   │   ├── faces-config.xml
│   │   └── web.xml
│   ├── index.xhtml
│   ├── netz-details.xhtml
│   ├── netz-melden.xhtml
│   └── person-erfassen.xhtml
├── target/
├── pom.xml
└── README.md

## Einrichtung
Repository klonen:
git clone https://github.com/HanyouAdi/IPWA02-01-Ghost-Net-Fishing.git
Projekt in Eclipse importieren:
File → Import → Existing Maven Projects
Datenbank in XAMPP starten:
Apache starten
MySQL starten auf Port 3308
Datenbank-Dump importieren:
phpMyAdmin → Datenbank auswählen → Importieren → SQL-Datei auswählen
Projekt auf Tomcat starten.

## Vorhandene Test-Personen:
Meldende Personen:
Dori                +49 391 1234 0004
Captain Hook        +44 20 1234 0014

Bergende Personen:
Käpt’n Blaubär      +39 06 1234 0010
Vaiana              +41 81 123 0006
Hein Blöd           +91 11 1234 0012

Verschollen Meldene Personen:
Bernd das Brot      +91 11 1234 0011
Pippi Langstrumpf   +46 8 123 0007
