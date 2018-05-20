package isu.stud.projectwork.repository;


import isu.stud.projectwork.model.CryptoCurrency;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CryptoCurrencyRepository extends MongoRepository<CryptoCurrency,String>,CryptoCustom {
    public List<CryptoCurrency> findAll();
}