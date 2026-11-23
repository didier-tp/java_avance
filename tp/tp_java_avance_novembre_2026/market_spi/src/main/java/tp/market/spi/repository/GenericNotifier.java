package tp.market.spi.repository;

public  interface GenericNotifier<T> {
	public void notifyNewState(T entity);
	public void notifyNewEvent(T entity,String eventHint);//eventHint may be a lifecyle event like "created" or "deleted" or else
	//...
}
