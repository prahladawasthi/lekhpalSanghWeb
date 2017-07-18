package com.services;

import com.model.Tahsil;

import java.util.List;

public interface TahsilService {

    void saveTahsil(Tahsil tahsil);

    Tahsil deleteTahsilById(String id);

    List<Tahsil> findAllTahsils();

    Tahsil findByID(String id);

    boolean isTahsilExist(Tahsil tahsil);
}
