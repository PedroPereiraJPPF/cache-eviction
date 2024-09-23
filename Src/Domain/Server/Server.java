package Src.Domain.Server;

import java.util.List;

import Database.Cache.Cache;
import Database.Data.Database;
import Src.Domain.Server.Interface.ServerInterface;
import Src.Domain.ServiceOrder.ServiceOrder;
import Src.Domain.ServiceOrder.ServiceOrderInterface;

public class Server implements ServerInterface {
    private Database database;
    private Cache cache;

    public Server() {
        this.database = new Database();
        this.cache = new Cache();

        for (int i = 1; i <= 60; i++) {
            ServiceOrder serviceOrder = new ServiceOrder();
            serviceOrder.setCode(i);
            serviceOrder.setName("Ordem de Serviço " + i);
            serviceOrder.setDescription("Descrição da Ordem de Serviço " + i);

            this.storeServiceOrder(serviceOrder);
        }
    }

    @Override
    public List<ServiceOrderInterface> listServiceOrders() {
        return this.database.list();
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

        return value;
    }

    @Override
    public ServiceOrderInterface getServiceOrder(ServiceOrderInterface serviceOrder) {
        return this.getServiceOrder(serviceOrder.getCode());
    }

    @Override
    public ServiceOrderInterface storeServiceOrder(ServiceOrderInterface serviceOrder) {
        return this.database.insert(serviceOrder.getCode(), serviceOrder);
    }

    @Override
    public void deleteServiceOrder(int code) {
        this.deleteServiceOrder(new ServiceOrder(code));
    }

    @Override
    public void deleteServiceOrder(ServiceOrderInterface serviceOrder) {
        this.database.delete(serviceOrder.getCode());

        this.cache.delete(serviceOrder);
    }

    @Override
    public ServiceOrderInterface updateServiceOrder(ServiceOrderInterface data) {
        ServiceOrderInterface serviceOrder = this.getServiceOrder(data);

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
