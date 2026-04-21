package tp.market.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import tp.market.model.StockExchange;

import java.util.List;

@Entity
@Table(name="stock" , indexes = @Index(name="idx_isin" , columnList = "isin" , unique = true))
@Getter
@Setter
@NoArgsConstructor
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
/*
@Cache usages:
   READ_ONLY : only for data that never change
   READ_WRITE : with automatic cache lock during update/transaction
   NONSTRICT_READ_WRITE : rapid but risk of unconstitency durring a small period
   TRANSACTIONAL : with XA/two-phase-commit
 */
public class StockEntity {
    private String isin ;//code (ex: FR0000120321) (ISIN: (International Securities Identification Number)
    private String name; //ex: L'Oréal S.A

    @OneToMany(fetch = FetchType.LAZY , mappedBy = "stock" , cascade = { CascadeType.REMOVE})
    private List<StockExchangeEntity> stockExchanges;

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
