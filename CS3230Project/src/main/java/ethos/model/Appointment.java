package main.java.ethos.model;

import java.time.LocalDateTime;
import java.time.LocalTime;

// TODO: Auto-generated Javadoc
/**
 * The Appointment class.
 */
public class Appointment {

    /** The doctor F name. */
    private String doctorFName;
    
    /** The doctor L name. */
    private String doctorLName;
    
    /** The appt date time. */
    private LocalDateTime apptDateTime;
    
    /** The doctor id. */
    private int doctorId;
    
    /** The patient id. */
    private int patientId;
    
    /** The appointment reason. */
    private String appointmentReason;

    /**
     * Instantiates a new appointment.
     *
     * @param doctorFName the doctor F name
     * @param doctorLName the doctor L name
     * @param doctorId the doctor id
     * @param patientId the patient id
     * @param apptDateTime the appt date time
     * @param appointmentReason the appointment reason
     */
    public Appointment(String doctorFName, String doctorLName, int doctorId, int patientId, LocalDateTime apptDateTime,
            String appointmentReason) {
        this.doctorFName = doctorFName;
        this.doctorLName = doctorLName;
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.setApptDateTime(apptDateTime);
        this.appointmentReason = appointmentReason;
    }
    
    /**
     * Instantiates a new appointment.
     *
     * @param doctorId the doctor id
     * @param patientId the patient id
     * @param apptDateTime the appt date time
     * @param appointmentReason the appointment reason
     */
    public Appointment(int doctorId, int patientId, LocalDateTime apptDateTime, String appointmentReason) {
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.setApptDateTime(apptDateTime);
        this.appointmentReason = appointmentReason;
    }

    /**
     * Gets the doctor first name.
     *
     * @return the first name
     */
    public String getDoctorFName() {
        return doctorFName;
    }

    /**
     * Sets the doctor first name.
     *
     * @param doctorFName the first name
     */
    public void setDoctorFName(String doctorFName) {
        this.doctorFName = doctorFName;
    }

    /**
     * Gets the doctor last name.
     *
     * @return the last name
     */
    public String getDoctorLName() {
        return doctorLName;
    }

    /**
     * Sets the doctor last name.
     *
     * @param doctorLName the new last name
     */
    public void setDoctorLName(String doctorLName) {
        this.doctorLName = doctorLName;
    }

    /**
     * Gets the doctor id.
     *
     * @return int
     */
    public int getDoctorId() {
        return this.doctorId;
    }

    /**
     * Sets the id for the doctor.
     *
     * @param newId the new ID
     */
    public void setDoctorId(int newId) {
        this.doctorId = newId;
    }

    /**
     * Gets the appointment reason.
     *
     * @return String
     */
    public String getAppointmentReason() {
        return this.appointmentReason;
    }

    /**
     * Sets the reason for the appointment.
     *
     * @param newReason the new reason
     */
    public void setAppointmentReason(String newReason) {
        this.appointmentReason = newReason;
    }

    /**
     * Gets the appt date time.
     *
     * @return the appt date time
     */
    public LocalDateTime getApptDateTime() {
        return apptDateTime;
    }

    /**
     * Gets the appt time.
     *
     * @return the appt time
     */
    public LocalTime getApptTime() {
        return apptDateTime.toLocalTime();
    }

    /**
     * Sets the appt date time.
     *
     * @param apptDateTime the new appt date time
     */
    public void setApptDateTime(LocalDateTime apptDateTime) {
        this.apptDateTime = apptDateTime;
    }

    /**
     * Gets the patient id.
     *
     * @return the patient id
     */
    public int getPatientId() {
        return patientId;
    }

    /**
     * Sets the patient id.
     *
     * @param patientId the new patient id
     */
    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

}