package com.services;

import com.model.District;

import java.util.List;

public interface DistrictService {

    void saveDistrict(District district);

    District deleteDistrictById(String id);

    List<District> findAllDistricts();

    District findByID(String id);

    boolean isDistrictExist(District district);
}
