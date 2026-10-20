package tp.market.adapter;

import tp.market.persistence.jpa.MyJpaUtil;
import tp.market.spi.repository.PersistenceSetting;

public class MyJpaPersistenceSetting implements PersistenceSetting {
    @Override
    public void activateAutoDDL(boolean autoDDL) {
        MyJpaUtil.setHbm2ddlAuto(true);
    }
}
