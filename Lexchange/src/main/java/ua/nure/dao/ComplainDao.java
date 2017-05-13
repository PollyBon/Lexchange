package ua.nure.dao;

import ua.nure.model.Complain;

import java.util.List;

public interface ComplainDao {

    void createComplain(Complain complain);

    void deleteComplainByUserId(long id);

    List findAllComplains();
}