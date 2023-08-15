package by.prusco.ozonstatistics.entity;

public enum Authorities {
    USER("USER"),
    SUPERADMIN("SUPERADMIN"),
    UNSUBSCRIBEDUSER("UNSUBSCRIBEDUSER"),
    ADMIN("ADMIN");

    private final String name;

    Authorities(String name) {this.name = name;}
    public String getName() {return name;}
}
