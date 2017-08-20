package com.services;

import com.model.Tahsil;
import com.repository.TahsilRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("tahsilService")
public class TahsilServiceImpl implements TahsilService {

    private MongoTemplate mongoTemplate;
    private TahsilRepository tahsilRepository;

    @Autowired
    public TahsilServiceImpl(TahsilRepository tahsilRepository, MongoTemplate mongoTemplate) {
        this.tahsilRepository = tahsilRepository;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void saveTahsil(Tahsil tahsil) {
        mongoTemplate.save(tahsil);
    }

    @Override
    public Tahsil deleteTahsilById(String id) {
        return mongoTemplate.findAndRemove(new Query().addCriteria(Criteria.where("_id").is(id)), Tahsil.class);
    }

    @Override
    @Cacheable("allTahsil")
    public List<Tahsil> findAllTahsils() {
        return tahsilRepository.findAll();

    }

    @Override
    public Tahsil findByID(String id) {
        return mongoTemplate.findById(id, Tahsil.class);
    }

    @Override
    public boolean isTahsilExist(Tahsil tahsil) {
        return ((mongoTemplate.find(
                Query.query(new Criteria().andOperator(Criteria.where("tahsilName").regex(tahsil.getTahsilName(), "i"),
                        //Criteria.where("mandalID").regex(tahsil.getMandalID(), "i"),
                        Criteria.where("districtID").regex(tahsil.getDistrictID(), "i"))),
                Tahsil.class)).size() > 0);
    }

}
