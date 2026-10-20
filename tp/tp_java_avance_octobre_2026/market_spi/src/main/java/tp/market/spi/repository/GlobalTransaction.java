package tp.market.spi.repository;

public interface GlobalTransaction {
    public void begin();
    public void rollback();
    public void commit();
}
