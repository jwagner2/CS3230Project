package main.java.ethos.model;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * The Appointment class
 */
public class Appointment {

    private String doctorFName;
    private String doctorLName;
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
    private int doctorId;
    private String appointmentReason;

    public Appointment(String doctorFName, String doctorLName, int doctorId, LocalDate apptDate, LocalTime apptTime, String appointmentReason) {
        this.doctorFName = doctorFName;
        this.doctorLName = doctorLName;
        this.doctorId = doctorId;
        this.appointmentDate = apptDate;
        this.appointmentTime = apptTime;
        this.appointmentReason = appointmentReason;
    }

    /** 
     * Gets the doctor first name
     * @return the first name
     */
    public String getDoctorFName() {
        return doctorFName;
    }

    
    /** 
     * Sets the doctor first name
     * @param doctorFName the first name
     */
    public void setDoctorFName(String doctorFName) {
        this.doctorFName = doctorFName;
    }

    
    /** 
     * Gets the doctor last name
     * @return the last name
     */
    public String getDoctorLName() {
        return doctorLName;
    }

    
    /** 
     * Sets the doctor last name
     * @param doctorLName the new last name
     */
    public void setDoctorLName(String doctorLName) {
        this.doctorLName = doctorLName;
    }

    
    /** 
     * @return LocalDate
     */
    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    
    /** 
     * @param appointmentDate
     */
    public void setAppointmentDate(LocalDate appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    
    /** 
     * @return LocalTime
     */
    public LocalTime getAppointmentTime() {
        return appointmentTime;
    }

    /**
     * Sets the reason for the appointment
     * @param newReason the new reason
     */
    public void setAppointmentTime(LocalTime appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    
    /** 
     * @return int
     */
    public int getDoctorId() {
        return this.doctorId;
    }

    /**
     * Sets the id for the doctor
     * @param newId the new ID
     */
    public void setDoctorId(int newId) {
        this.doctorId = newId;
    }


    
    /** 
     * @return String
     */
    public String getAppointmentReason() {
        return this.appointmentReason;
    }

    /**
     * Sets the reason for the appointment
     * @param newReason the new reason
     */
    public void setAppointmentReason(String newReason) {
        this.appointmentReason = newReason;
    }
}