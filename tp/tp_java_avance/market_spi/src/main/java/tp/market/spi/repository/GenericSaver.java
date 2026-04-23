package tp.market.spi.repository;

public interface GenericSaver<T,ID> {
	public T saveNew(T obj);
	public T saveOrUpdate(T obj);
	public void  updateExisting(T obj);
	public void deleteFromId(ID id);
}
