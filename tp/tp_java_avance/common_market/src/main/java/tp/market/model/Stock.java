package tp.market.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/*
Stock (action)
 */
@Getter @Setter @NoArgsConstructor @ToString
public class Stock implements Serializable {
    private String isin ;//code (ex: FR0000120321) (ISIN: (International Securities Identification Number)
    private String name; //ex: L'Oréal S.A
    private String symbol; //ex: OR.PA
    private double currentQuote; //ex: 348.25 le 7avril2026 à 17h30
    private Long number; //stock number/quantity (nombre d'actions en circulation) ex: 533728223

    public Stock( String name, String symbol, String isin,  double currentQuote, Long number ) {
        this.isin = isin;
        this.name = name;
        this.symbol = symbol;
        this.currentQuote = currentQuote;
        this.number = number;
    }

    public Stock( String name, String symbol, String isin,  double currentQuote) {
        this(name,symbol,isin,currentQuote,null);
    }
}
