package ua.nure.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.nure.dao.ComplainDao;
import ua.nure.model.Complain;
import ua.nure.service.ComplainService;

import java.util.List;

@Service("complainService")
@Transactional
public class ComplainServiceImpl implements ComplainService{

    @Autowired
    private ComplainDao dao;


    @Override
    public void createComplain(Complain complain) {
        dao.createComplain(complain);
    }

    @Override
    public void deleteComplainByUserId(long id) {
        dao.deleteComplainByUserId(id);
    }

    @Override
    public List<Complain> findAllComplains() {
        return dao.findAllComplains();
    }
}
