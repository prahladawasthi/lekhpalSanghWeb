package com.services;

import com.model.Complain;

import java.util.List;

public interface ComplainService {

    void saveComplain(Complain complain);

    Complain deleteComplainById(String id);

    List<Complain> findAllComplains();

    Complain findByID(String id);

}