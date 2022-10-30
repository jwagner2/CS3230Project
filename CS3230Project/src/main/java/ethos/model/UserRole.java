package main.java.ethos.model;

public enum UserRole {
    NURSE("Nurse"),
    ADMIN("Admin");

    /** The label. */
    public final String label;

    /**
     * Instantiates a new page type.
     *
     * @param label the label
     */
    private UserRole(String label) {
        this.label = label;
    }
}
