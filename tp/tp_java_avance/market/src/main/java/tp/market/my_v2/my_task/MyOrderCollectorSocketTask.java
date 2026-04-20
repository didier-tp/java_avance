package tp.market.my_v2.my_task;

import lombok.extern.slf4j.Slf4j;
import tp.market.message.*;
import tp.market.model.MyStockMarket;
import tp.market.model.Stock;
import tp.market.model.TradingOrder;
import tp.market.service.OrderBookService;
import tp.market.service.OrderBookServiceImpl;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class MyOrderCollectorSocketTask implements Runnable {
    private Socket socketClient;


    public MyOrderCollectorSocketTask(Socket socketClient){
        this.socketClient=socketClient;
    }
    @Override
    public void run() {
       // à coder librement en Tp
        System.out.println("MyOrderCollectorSocketTask.run");
    }
}
