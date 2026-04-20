package tp.market.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="stock")
@Getter
@Setter
@NoArgsConstructor
public class StockEntity {
    private String isin ;//code (ex: FR0000120321) (ISIN: (International Securities Identification Number)
    private String name; //ex: L'Oréal S.A

    @Id //no auto_incr
    @Column(name="symbol")
    private String symbol;//ex: OR.PA

    @Column(name="current_quote")
    private double currentQuote; //ex: 348.25 le 7avril2026 à 17h30

    @Column(name="number")
    private Long number;

    public StockEntity( String name, String symbol, String isin,  double currentQuote, Long number ) {
        this.isin = isin;
        this.name = name;
        this.symbol = symbol;
        this.currentQuote = currentQuote;
        this.number = number;
    }

    public StockEntity( String name, String symbol, String isin,  double currentQuote) {
        this(name,symbol,isin,currentQuote,null);
    }

    @Override
    public String toString() {
        return "StockEntity{" +
                "isin='" + isin + '\'' +
                ", name='" + name + '\'' +
                ", symbol='" + symbol + '\'' +
                ", currentQuote=" + currentQuote +
                ", number=" + number +
                '}';
    }
}
