package isu.stud.projectwork.parser;



import isu.stud.projectwork.model.CryptoCurrency;
import isu.stud.projectwork.repository.CryptoCurrencyRepository;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

import org.jsoup.*;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InitParser {

    @Autowired
    CryptoCurrencyRepository cryptoCurrencyRepository;


    public InitParser() {
    }

    @Transactional
    public void saveInit(CryptoCurrency cryptoCurrency){
        cryptoCurrencyRepository.save(cryptoCurrency);
    }

    @Transactional
    public void update(CryptoCurrency cryptoCurrency){cryptoCurrencyRepository.updatePrices(cryptoCurrency);}

    public void initSymbols(){
        try {
            Document doc = Jsoup.connect("https://coinmarketcap.com/all/views/all/").get();
            Elements currencyBody = doc.select("#currencies-all > tbody");
            Element currencyTable = currencyBody.first();
            Elements symbols = currencyTable.select("td:nth-child(2)>a");
            Elements prices = currencyTable.select("td:nth-child(5)");
            Elements percent1Hour = currencyTable.select("td:nth-child(8)");
            Elements percent24Hour = currencyTable.select("td:nth-child(9)");
            Elements percent7Days = currencyTable.select("td:nth-child(10)");
            for (int i = 0; i < symbols.size()-3; i++) {
                CryptoCurrency currency = CryptoCurrency.builder()
                        .symbol(symbols.get(i).text())
                        .price(Double.parseDouble(prices.get(i).text().replace("$","")))
                        .p1hour(Double.parseDouble(percent1Hour.get(i).text().replace("%","")))
                        .p24hour(Double.parseDouble(percent24Hour.get(i).text().replace("%","")))
                        .p7days(Double.parseDouble(percent7Days.get(i).text().replace("%","")))
                        .build();
                System.out.println(currency.getSymbol());
                saveInit(currency);
                update(currency);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}