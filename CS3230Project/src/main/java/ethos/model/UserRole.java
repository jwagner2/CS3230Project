package main.java.ethos.model;

// TODO: Auto-generated Javadoc
/**
 * The Enum UserRole.
 */
public enum UserRole {
    
    /** The nurse. */
    NURSE("Nurse"),
    
    /** The admin. */
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
