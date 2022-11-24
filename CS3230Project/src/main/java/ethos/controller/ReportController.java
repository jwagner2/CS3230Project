package main.java.ethos.controller;

import java.time.LocalDate;

import main.java.ethos.dal.VisitDal;

public class ReportController {
    
    public void searchForVisitsBetween(LocalDate startDate, LocalDate endDate) {
        VisitDal vDal = new VisitDal();
        vDal.searchForVisitsBetween(startDate, endDate);
    }
}
