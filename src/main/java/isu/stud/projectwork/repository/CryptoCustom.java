package isu.stud.projectwork.repository;

import com.mongodb.WriteResult;
import com.mongodb.client.result.UpdateResult;
import isu.stud.projectwork.model.CryptoCurrency;

public interface CryptoCustom {
    public UpdateResult updatePrices(CryptoCurrency cryptoCurrency);
}
