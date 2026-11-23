package tp.market.core.model;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

@Getter
@Setter
@Slf4j
public class StockOrderBook {
    private String stockId;
    private double currentQuote;
    private List<TradingOrder> asks; //pending and partial_filled asks as LinkedList (best first)
    private List<TradingOrder> bids; //pending and partial_filled bids as LinkedList (best first)
    private List<TradingOrder> processedOrders; // (asks or bids)  : filled , rejected
    private List<StockExchange> exchanges; //for new quote computing
    private List<StockExchange> oldExchanges;

    public synchronized void  manageFilledOrders(){
        //NB: collect and removeAll to avoid ConcurrentModificationException (removing while iterating)
        List<TradingOrder> ordersToRemove = new ArrayList<>();
        for(TradingOrder bid : bids){
            if(bid.getStatus() == TradingOrder.OrderStatus.FILLED){
                ordersToRemove.add(bid);
                this.processedOrders.add(bid);
            }
        }
        this.bids.removeAll(ordersToRemove);

        ordersToRemove.clear();
        for(TradingOrder ask : asks){
            if(ask.getStatus() == TradingOrder.OrderStatus.FILLED){
                ordersToRemove.add(ask);
                this.processedOrders.add(ask);
            }
        }
        this.asks.removeAll(ordersToRemove);
    }

    //return true if changed (called by OrderBookServiceImpl.updateSomeQuotes())
    public synchronized boolean reAjustCurrentQuote(){
        //compute new quote with VERY SIMPLE ALGORITHM (it's just a TP , not real euroNext) !!!!
        //ici en TP: moyenne (pondérée par quantité) des dernières transactions effectuées
        double totalProduct=0;
        double totalQuantity=0;
        for(StockExchange exchange : this.exchanges){
            totalQuantity += exchange.getQuantity();
            totalProduct += (exchange.getQuantity() * exchange.getPrice());
            oldExchanges.add(exchange);
        }
        if(totalQuantity>0) {
            this.currentQuote=totalProduct/totalQuantity;
            this.exchanges.clear(); //after adding in oldExchanges
            return true;//changed
        }
      else return false;
    }

    public StockOrderBook(String stockId, double currentQuote) {
        this.stockId = stockId;
        this.currentQuote = currentQuote;
        this.asks = new LinkedList<>();
        this.bids = new LinkedList<>();
        this.processedOrders = new LinkedList<>();
        this.exchanges = new LinkedList<>();
        this.oldExchanges = new LinkedList<>();
    }

    public void addBid(TradingOrder order){
        //add best bid first (lower cost)
        boolean added=false;
        synchronized (this.bids) {
            ListIterator<TradingOrder> itr = this.bids.listIterator();
            while (itr.hasNext()) {
                TradingOrder o = itr.next();
                if (order.getPrice() < o.getPrice()) {
                    itr.previous();
                    itr.add(order); added=true; break; //ajout avant offre existante moins bien
                }
            }
            if (!added) itr.add(order);//ajout à la fin si moins bonne offre
            order.setStatus(TradingOrder.OrderStatus.PENDING);
        }
    }

    public void addAsk(TradingOrder order){
        //add best asks first (higher price)
        boolean added=false;
        synchronized (this.asks) {
            ListIterator<TradingOrder> itr = this.asks.listIterator();
            while (itr.hasNext()) {
                TradingOrder o = itr.next();
                if (order.getPrice() > o.getPrice()) {
                    itr.previous();
                    itr.add(order);  added=true; break; //ajout avant demande existante moins bien
                }
            }
            if (!added) itr.add(order);//ajout à la fin si moins bonne demande
            order.setStatus(TradingOrder.OrderStatus.PENDING);
        }
    }

    //compute and return the quantity that can be exchanged (if buyOrder.price >= saleOrder.price )
    public long purchasableQuantity (TradingOrder buyOrder,TradingOrder saleOrder){
        if( ( buyOrder.getStatus()!= TradingOrder.OrderStatus.PENDING && buyOrder.getStatus()!= TradingOrder.OrderStatus.PARTIAL_FILLED)
                || ( saleOrder.getStatus()!= TradingOrder.OrderStatus.PENDING && saleOrder.getStatus()!= TradingOrder.OrderStatus.PARTIAL_FILLED)) return 0;
        if(buyOrder.getPrice()<saleOrder.getPrice()) return 0;
        /*else*/
        if(saleOrder.getRemainingQuantity()>=buyOrder.getRemainingQuantity()) return buyOrder.getRemainingQuantity();
           else return saleOrder.getRemainingQuantity();
    }

    //do all exchange actions (new StockExchange , update status and quantity of orders , no immedialitly update lists )
    public synchronized void doExchange(TradingOrder buyOrder,TradingOrder saleOrder,long quantity){
        //add new StockExchange:
        this.exchanges.add(new StockExchange(this.stockId,saleOrder.getPrice(), quantity ,buyOrder.getTraderId(), saleOrder.getTraderId()));
        //update status and quantity of orders:
        buyOrder.manageExchangedQuantity(quantity);
        saleOrder.manageExchangedQuantity(quantity);
        //update lists will be deffered (by calling manageFilledOrders() after all loops ):
    }

    public void processPossibleStockOrders(){
        //confronter offres et demandes et effectuer les transactions possibles:
        synchronized (this){
            for(TradingOrder ask : this.getAsks()){
                for(TradingOrder bid : this.getBids()){
                    long qty = this.purchasableQuantity(ask,bid); //first try at best price
                    if(qty>0) {
                        this.doExchange(ask,bid,qty);
                        if(ask.getRemainingQuantity()==0) break;
                    }
                    //if remain_ask_qty > 0 , next iteration of current loop
                }
            }
            this.manageFilledOrders();//updates lists contents if some filled orders
        }
    }

    @Override
    public String toString() {
        return "StockOrderBook{" +
                "stockId='" + stockId + '\'' +
                ", currentQuote=" + currentQuote +
                '}';
    }

    public synchronized void addNewOrder(TradingOrder order){
        if(order.getOrderType()== TradingOrder.OrderType.SALE) {
            addBid(order);
            if(this.bids.size()<=3) log.trace("new bids = " + this.bids); else log.trace("for stockId=" + order.getStockId() + " number of bids=" + this.bids.size());
        }
        else{
            addAsk(order);
            if(this.asks.size()<=3) log.trace("new asks = " + this.asks); else log.trace("for stockId=" + order.getStockId() + " number of asks=" + this.asks.size());
        }
    }
}

/*
meilleure offre = best bid (le prix proposé à la vente le plus bas)
meilleure demande = best ask (le prix proposé à l'achat le plus haut)
écart entre les deux = "spread" / fourchette .
Plus cet écart est prononcé , moins l'action est stable
plus le volume échangé est important , plus l'action est dite "liquide" .
 */