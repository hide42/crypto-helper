package isu.stud.projectwork.repository;

import com.mongodb.client.result.UpdateResult;
import isu.stud.projectwork.model.CryptoCurrency;
import isu.stud.projectwork.model.InfoCurrency;
import isu.stud.projectwork.parser.InitParser;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class CryptoCustomImpl implements CryptoCustom {

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public UpdateResult updatePrices(CryptoCurrency cryptoCurrency) {
        Query searchQuery = new Query(Criteria.where("symbol").is(cryptoCurrency.getSymbol()));
        return mongoTemplate.upsert(searchQuery, Update.update("prices", getPrices(cryptoCurrency.getUrl())), CryptoCurrency.class);
    }

    public static List<InfoCurrency> getPrices(String url){
        System.out.println(url);
        List<InfoCurrency> list = new ArrayList();
        try {
            Document doc = Jsoup.connect("https://coinmarketcap.com"+url+"#markets").get();
            Elements currencyBody = doc.select("#markets-table > tbody");
            Element currencyTable = currencyBody.first();//
            Elements market = currencyTable.select("td:nth-child(2)>a");
            Elements pair = currencyTable.select("td:nth-child(3)");
            Elements price = currencyTable.select("td:nth-child(5)");
            Elements volume = currencyTable.select("td:nth-child(6)");
            for (int i = 0; i < market.size()-2; i++) {
                list.add(InfoCurrency.builder()
                        .market(market.get(i).text())
                        .pair(pair.get(i).text())
                        .price(Double.parseDouble(price.get(i).text().replace("$","").replace("*","")))
                        .volume(Double.parseDouble(volume.get(i).text().replace("%","")))
                        .url(pair.get(i).select("a").first().attr("href"))
                        .build());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
}
