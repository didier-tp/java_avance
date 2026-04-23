package tp.market.adapter;

import tp.market.persistence.jpa.MyJpaUtil;
import tp.market.spi.repository.GlobalTransaction;

public class MyJpaUtilGlobalTransaction implements GlobalTransaction {
    @Override
    public void begin() {
        MyJpaUtil.beginGlobalJpaTransaction();
    }

    @Override
    public void rollback() {
        MyJpaUtil.rollbackGlobalJpaTransaction();
    }

    @Override
    public void commit() {
        MyJpaUtil.commitGlobalJpaTransaction();
    }
}
