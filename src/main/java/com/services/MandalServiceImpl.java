package com.services;

import com.model.Mandal;
import com.repository.MandalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("mandalService")
public class MandalServiceImpl implements MandalService {

    private MongoTemplate mongoTemplate;
    private MandalRepository mandalRepository;

    @Autowired
    public MandalServiceImpl(MandalRepository mandalRepository, MongoTemplate mongoTemplate) {
        this.mandalRepository = mandalRepository;
        this.mongoTemplate = mongoTemplate;
    }


    @Override
    public void saveMandal(Mandal mandal) {
        mongoTemplate.save(mandal);
    }

    @Override
    public Mandal deleteMandalById(String id) {
        return mongoTemplate.findAndRemove(new Query().addCriteria(Criteria.where("_id").is(id)), Mandal.class);
    }

    @Override
    @Cacheable("allMandal")
    public List<Mandal> findAllMandals() {
        return mandalRepository.findAll();
    }

    @Override
    public Mandal findByID(String id) {
        return mongoTemplate.findById(id, Mandal.class);
    }

    @Override
    public boolean isMandalExist(Mandal mandal) {
        return ((mongoTemplate.find(
                Query.query(new Criteria().orOperator(Criteria.where("mandalName").regex(mandal.getMandalName(), "i"))),
                Mandal.class)).size() > 0);
    }
}
