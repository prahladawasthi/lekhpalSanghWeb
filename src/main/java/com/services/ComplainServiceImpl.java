package com.services;

import com.model.Complain;
import com.repository.ComplainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("complainService")
public class ComplainServiceImpl implements ComplainService {

    private MongoTemplate mongoTemplate;
    private ComplainRepository complainRepository;

    @Autowired
    public ComplainServiceImpl(ComplainRepository complainRepository, MongoTemplate mongoTemplate) {
        this.complainRepository = complainRepository;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void saveComplain(Complain complain) {
        mongoTemplate.save(complain);
    }

    @Override
    public Complain deleteComplainById(String id) {
        return mongoTemplate.findAndRemove(new Query().addCriteria(Criteria.where("_id").is(id)), Complain.class);
    }

    @Override
    public List<Complain> findAllComplains() {
        return complainRepository.findAll();

    }

    @Override
    public Complain findByID(String id) {
        return mongoTemplate.findById(id, Complain.class);
    }

}
