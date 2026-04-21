package tp.market.persistence.my_entity;


import lombok.Getter;
import lombok.Setter;

//à compléter en Tp avec @Entity , @Table , @Id , @Column , ...
@Getter
@Setter
public class MyStockEntity {
    private String isin ;//code (ex: FR0000120321) (ISIN: (International Securities Identification Number)
    private String name; //ex: L'Oréal S.A


    private String symbol;//ex: OR.PA


    private double currentQuote; //ex: 348.25 le 7avril2026 à 17h30


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
