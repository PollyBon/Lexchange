package ua.nure.service;

import ua.nure.model.Complain;

import java.util.List;

public interface ComplainService {

    void createComplain(Complain complain);

    void deleteComplainByUserId(long id);

    List<Complain> findAllComplains();
}
