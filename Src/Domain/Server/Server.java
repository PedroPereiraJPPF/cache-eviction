package Src.Domain.Server;

import java.util.List;
import Database.Cache.Hash.HashCache;
import Database.Data.Hash.HashDatabase;
import Src.Domain.Server.Interface.ServerInterface;
import Src.Domain.Server.Message.CompressedObject;
import Src.Domain.Server.Message.CompressionManager;
import Src.Domain.Server.Message.Message;
import Src.Domain.ServiceOrder.ServiceOrder;
import Src.Domain.ServiceOrder.ServiceOrderInterface;
import java.text.ParseException;

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

        CompressedObject data = message.getData();

        value.setCode(Integer.valueOf(CompressionManager.decodeParameter(data.getValues()[0], data.getFrequencyTable())));

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
        CompressedObject data = message.getData();

        ServiceOrderInterface serviceOrder = new ServiceOrder();
        serviceOrder.setName(CompressionManager.decodeParameter(data.getValues()[1], data.getFrequencyTable()));
        serviceOrder.setDescription(CompressionManager.decodeParameter(data.getValues()[2], data.getFrequencyTable()));

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
        CompressedObject data = message.getData();

        int code = Integer.valueOf(CompressionManager.decodeParameter(data.getValues()[0], data.getFrequencyTable()));

        this.deleteServiceOrder(code);
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
        CompressedObject data = message.getData();

        int code = Integer.valueOf(CompressionManager.decodeParameter(data.getValues()[0], data.getFrequencyTable()));

        ServiceOrderInterface serviceOrder = this.getServiceOrder(code);

        if (serviceOrder == null)
            return null;

        serviceOrder.setCode(code);
        serviceOrder.setName(CompressionManager.decodeParameter(data.getValues()[1], data.getFrequencyTable()));
        serviceOrder.setDescription(CompressionManager.decodeParameter(data.getValues()[2], data.getFrequencyTable()));

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
