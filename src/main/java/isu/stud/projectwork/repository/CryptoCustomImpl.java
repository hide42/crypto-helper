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

    private static List<InfoCurrency> getPrices(String url){
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
            //****** тут и нужно магию

            for (int i = 0; i < market.size()-3; i++) {
                for (int j = i+1; j < market.size()-2; j++) {
                    //if volume > poroga
                    //filters
                    if(pair.get(i).text().equals(pair.get(j).text())) {
                        double priceA=Double.parseDouble(price.get(i).text().replace("$", "").replace("*", ""));
                        double priceB=Double.parseDouble(price.get(j).text().replace("$", "").replace("*", ""));
                        list.add(
                                InfoCurrency.builder()
                                        .market(market.get(i).text()+" / "+market.get(j).text())
                                        .pair(pair.get(i).text())
                                        .priceA(priceA)
                                        .priceB(priceB)
                                        .urlA(pair.get(i).select("a").first().attr("href"))
                                        .urlB(pair.get(j).select("a").first().attr("href"))
                                        .coef(((priceB-priceA)/priceA)*100)
                                        .build());
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

}
