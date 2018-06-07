package isu.stud.projectwork.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@Document(collection = "cryptocurrency")
public class CryptoCurrency implements Serializable{
    @Id
    private String symbol;
    private double price;
    private double p1hour;
    private double p24hour;
    private double p7days;
    private String url;
    // Market : prices
    private List<InfoCurrency> prices;


}
