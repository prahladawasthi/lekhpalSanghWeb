package com.services;

import com.model.Mandal;

import java.util.List;

public interface MandalService {

    void saveMandal(Mandal mandal);

    Mandal deleteMandalById(String id);

    List<Mandal> findAllMandals();

    Mandal findByID(String id);

    boolean isMandalExist(Mandal mandal);
}
