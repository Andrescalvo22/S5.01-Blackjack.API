package com.blackjack.repository;

import com.blackjack.model.Hand;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HandRepository extends ReactiveMongoRepository<Hand, String> {
}

