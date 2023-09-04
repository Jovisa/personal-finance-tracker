package com.tw.personalfinancetracker.repository;

import com.tw.personalfinancetracker.model.Transaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface TransactionsRepository extends CrudRepository<Transaction, Long> {

    @Override
    Transaction save(Transaction entity);

    @Override
    Optional<Transaction> findById(Long id);

    @Override
    boolean existsById(Long id);

    @Override
    List<Transaction> findAll();

    @Override
    void deleteById(Long aLong);

//    @Override
//    public void delete(Transaction entity)

    @Override
    void deleteAll();
}
