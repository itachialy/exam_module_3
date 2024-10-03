package org.example.demo.service;

import org.example.demo.model.MatBang;

import java.sql.Date;
import java.util.List;

public interface IMatBangService {
    List<MatBang> findAll();
    void addMatBang(MatBang matBang);
    void deleteMatBang(String maMb);
    List<MatBang> filterMatBang (String maMb, Date startDate, Date endDate, int tang) ;

}
