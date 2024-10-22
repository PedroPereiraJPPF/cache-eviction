package Src.Domain.Server;

import java.util.List;
import java.util.Date;
import Database.Cache.Hash.HashCache;
import Database.Data.Hash.HashDatabase;
import Src.Domain.Server.Interface.ServerInterface;
import Src.Domain.Server.Message.CompressionManager;
import Src.Domain.Server.Message.Message;
import Src.Domain.ServiceOrder.ServiceOrder;
import Src.Domain.ServiceOrder.ServiceOrderInterface;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Server implements ServerInterface {
    private HashDatabase database;
    private HashCache cache;

    public Server() {
        this.database = new HashDatabase();
        this.cache = new HashCache();

        // for (int i = 1; i <= 70; i++) {
        //     ServiceOrder serviceOrder = new ServiceOrder();
        //     serviceOrder.setName("Ordem de Serviço " + i);
        //     serviceOrder.setDescription("Descrição da Ordem de Serviço " + i);

        //     this.storeServiceOrder(serviceOrder);
        // }

        // for (int i = 1; i <= 20; i++) {
        //     this.getServiceOrder(i);
        // }
    }

    @Override
    public List<ServiceOrderInterface> listServiceOrders() {
        return this.database.list();
    }

    @Override
    public ServiceOrderInterface getServiceOrder(Message message) {
        ServiceOrderInterface value = new ServiceOrder();
        value.setCode(Integer.valueOf(CompressionManager.decodeParameter(message.getCode())));

        return this.getServiceOrder(value);
    }

    @Override
    public ServiceOrderInterface getServiceOrder(int code) {
        ServiceOrderInterface value = this.cache.find(code);

        if (value != null)
            return value;

        value = this.database.search(code);

        if (value == null) 
            return null;

        this.cache.insert(value);

        this.cache.printElements();

        return value;
    }

    @Override
    public ServiceOrderInterface getServiceOrder(ServiceOrderInterface serviceOrder) {
        return this.getServiceOrder(serviceOrder.getCode());
    }

    @Override
    public ServiceOrderInterface storeServiceOrder(Message message) {
        ServiceOrderInterface serviceOrder = new ServiceOrder();
        serviceOrder.setName(CompressionManager.decodeParameter(message.getName()));
        serviceOrder.setDescription(CompressionManager.decodeParameter(message.getDescription()));

        return this.database.insert(serviceOrder);
    }

    @Override
    public ServiceOrderInterface storeServiceOrder(ServiceOrderInterface serviceOrder) {
        if (this.database.search(serviceOrder.getCode()) != null) {
            return null;
        }

        return this.database.insert(serviceOrder);
    }

    @Override
    public void deleteServiceOrder(Message message) {
        this.deleteServiceOrder(Integer.valueOf(CompressionManager.decodeParameter(message.getCode())));
    }

    @Override
    public void deleteServiceOrder(int code) {
        this.deleteServiceOrder(new ServiceOrder(code));
    }

    @Override
    public void deleteServiceOrder(ServiceOrderInterface serviceOrder) {
        this.database.delete(serviceOrder.getCode());

        this.cache.delete(serviceOrder.getCode());
    }

    @Override
    public ServiceOrderInterface updateServiceOrder(Message message) throws ParseException {
        ServiceOrderInterface serviceOrder = new ServiceOrder();

        serviceOrder.setCode(Integer.valueOf(CompressionManager.decodeParameter(message.getCode())));
        serviceOrder.setName(CompressionManager.decodeParameter(message.getName()));
        serviceOrder.setDescription(CompressionManager.decodeParameter(message.getDescription()));
        
        String decodedRequestTime = CompressionManager.decodeParameter(message.getRequestTime());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date requestTime = dateFormat.parse(decodedRequestTime);
        serviceOrder.setRequestTime(requestTime);

        return serviceOrder;
    }

    @Override
    public ServiceOrderInterface updateServiceOrder(ServiceOrderInterface data) {
        ServiceOrderInterface serviceOrder = this.database.search(data.getCode());

        if (serviceOrder == null)
            return null;
            
        serviceOrder.setName(data.getName());
        serviceOrder.setDescription(data.getDescription());
        serviceOrder.setRequestTime(data.getRequestTime());

        return serviceOrder;
    }
    
    @Override
    public int countServiceOrders() {
        return this.database.countElements();    
    }
}
