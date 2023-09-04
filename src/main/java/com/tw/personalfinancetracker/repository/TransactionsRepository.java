package com.tw.personalfinancetracker.repository;

import com.tw.personalfinancetracker.model.Transaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TransactionsRepository extends CrudRepository<Transaction, Long> {
    @Override
    List<Transaction> findAll();
}
