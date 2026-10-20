package tp.market.persistence.my_entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

//à compléter en Tp avec @Entity , @Table , @Id , @Column , ...
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="my_stock")
public class MyStockEntity {

    @Column(length=24)
    private String isin ;//code (ex: FR0000120321) (ISIN: (International Securities Identification Number)

    @Column(length=128)//255 par defaut
    private String name; //ex: L'Oréal S.A

    @Id
    private String symbol;//ex: OR.PA

    @Column(name="current_quote")
    private double currentQuote; //ex: 348.25 le 7avril2026 à 17h30

    @OneToMany(mappedBy = "stock")
    private List<MyStockExchangeEntity> exchanges;

    private Long number;

    public MyStockEntity(String name, String symbol, String isin, double currentQuote, Long number ) {
        this.isin = isin;
        this.name = name;
        this.symbol = symbol;
        this.currentQuote = currentQuote;
        this.number = number;
    }

    public MyStockEntity(String name, String symbol, String isin, double currentQuote) {
        this(name,symbol,isin,currentQuote,null);
    }

    @Override
    public String toString() {
        return "MyStockEntity{" +
                "isin='" + isin + '\'' +
                ", name='" + name + '\'' +
                ", symbol='" + symbol + '\'' +
                ", currentQuote=" + currentQuote +
                ", number=" + number +
                '}';
    }
}
