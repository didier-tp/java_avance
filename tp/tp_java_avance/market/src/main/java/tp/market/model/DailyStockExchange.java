package tp.market.model;

//structure compatible avec https://www.alphavantage.co/query

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter @Getter @ToString @NoArgsConstructor
public class DailyStockExchange {
    private String symbol; //ex: OR.PA
    private String date; //ex: 2026-04-02
    private double open; //ex: 353.9000
    private double high; //359.7500
    private double low; //352.9000
    private double close; //ex: 358.2000
    private long volume; //ex: 325590
}
