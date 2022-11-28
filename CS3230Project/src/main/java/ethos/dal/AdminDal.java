package main.java.ethos.dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDal {

    private String reportQuery = "select v.appt_datetime, v.patient_id, pa.patient_name, dr.doctor_name, nu.nurse_name, lab.labs_ordered, lab.lab_results, v.diagnosis"
            + " from ("
            + "    select v.visit_id, v.doctor_id, v.appt_datetime, v.nurse_id, v.diagnosis,a.patient_id"
            + "    from visit v"
            + "    inner join appointment a"
            + "    on v.appt_datetime = a.appt_datetime and v.doctor_id = a.doctor_id"
            + "    where v.appt_datetime < ? and v.appt_datetime > ?"
            + ") as v"
            + "inner join ("
            + "    select concat(p.fname, \" \", p.lname) as patient_name, pa.patient_id"
            + "    from person p, patient pa"
            + "    where p.pid = pa.pid"
            + ") as pa on v.patient_id = pa.patient_id"
            + "inner join("
            + "    select concat(p.fname, \" \", p.lname) as doctor_name, dr.doctor_id"
            + "    from person p, doctor dr"
            + "    where p.pid = dr.pid"
            + ") as dr on v.doctor_id = dr.doctor_id"
            + "inner join("
            + "    select concat(p.fname, \" \", p.lname) as nurse_name, nu.nurse_id"
            + "    from person p, nurse nu"
            + "    where p.pid = nu.pid"
            + ") as nu on v.nurse_id = nu.nurse_id"
            + "inner join("
            + "    select GROUP_CONCAT(l.name SEPARATOR ', ') as labs_ordered, GROUP_CONCAT(lo.isAbnormal SEPARATOR ', ') as lab_results, lo.visit_id"
            + "    from lab_test_type l, lab_order lo, visit v"
            + "    where lo.visit_id = v.visit_id and l.test_id = lo.test_id"
            + "    Group by lo.visit_id"
            + ") as lab on v.visit_id = lab.visit_id"
            + "order by appt_datetime ASC, SUBSTRING_INDEX(patient_name,' ', -1) ASC";

    public ResultSet submitQuery(String queryString) throws SQLException {
        try (Connection connection = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
                PreparedStatement stmt = connection.prepareStatement(queryString)) {

            ResultSet rs = stmt.executeQuery();
            return rs;
        }
    }
}
