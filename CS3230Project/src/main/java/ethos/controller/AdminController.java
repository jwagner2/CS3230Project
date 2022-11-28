package main.java.ethos.controller;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Map;

import main.java.ethos.dal.AdminDal;
import main.java.ethos.dal.VisitDal;

public class AdminController {

    public void searchForVisitsBetween(LocalDate startDate, LocalDate endDate) {
        VisitDal vDal = new VisitDal();
        vDal.searchForVisitsBetween(startDate, endDate);
    }

    public Map<String, Object> submitAdminQuery(String queryString) {
        AdminDal aDal = new AdminDal();
        try {
            return aDal.submitQuery(queryString.strip());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
