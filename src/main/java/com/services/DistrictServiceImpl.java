package com.services;

import com.model.District;
import com.repository.DistrictRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("districtService")
public class DistrictServiceImpl implements DistrictService {

    private MongoTemplate mongoTemplate;
    private DistrictRepository districtRepository;

    @Autowired
    public DistrictServiceImpl(DistrictRepository districtRepository, MongoTemplate mongoTemplate) {
        this.districtRepository = districtRepository;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void saveDistrict(District district) {
        mongoTemplate.save(district);
    }

    @Override
    public District deleteDistrictById(String id) {
        return mongoTemplate.findAndRemove(new Query().addCriteria(Criteria.where("_id").is(id)), District.class);
    }

    @Override
    @Cacheable("allDistrict")
    public List<District> findAllDistricts() {
        return districtRepository.findAll();
    }

    @Override
    public District findByID(String id) {
        return mongoTemplate.findById(id, District.class);
    }

    @Override
    public boolean isDistrictExist(District district) {
        return ((mongoTemplate.find(
                Query.query(new Criteria().andOperator(Criteria.where("mandalID").regex(district.getMandalID(), "i"), Criteria.where("districtName").regex(district.getDistrictName(), "i"))),
                District.class)).size() > 0);
    }
}
