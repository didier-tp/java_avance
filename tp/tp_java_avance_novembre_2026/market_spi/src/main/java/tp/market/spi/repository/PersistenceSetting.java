package tp.market.spi.repository;

public interface PersistenceSetting {
    //automatic Data Definition Language (ex: automatic CREATE TABLE)
    public void activateAutoDDL(boolean autoDDL);
}
