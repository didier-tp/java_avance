package tp.market.spi.repository;

import java.util.List;
import java.util.Optional;

public  interface GenericLoader<T,ID> {
	public T findById(ID id);
	public Optional<T> loadById(ID id);
	default public Optional<T> loadById(ID id,String... wishedDetails)  {
		return loadById(id); //without in this default impl to override
	}
	public boolean existsWithThisId(ID id);
	public List<T> loadAll();
}
