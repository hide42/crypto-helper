package isu.stud.projectwork.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class InfoCurrency {

    private String market;
    private String pair;
    private String url;
    private double price;
    private double volume;


}
