package isu.stud.projectwork.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class InfoCurrency {

    private String market;
    private String pair;
    private String urlA;
    private String urlB;
    private double priceA;
    private double priceB;
    private double coef;


}
