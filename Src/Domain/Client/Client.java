package Src.Domain.Client;

import java.util.List;

import Src.Domain.Client.Interface.ClientInterface;
import Src.Domain.Server.Server;
import Src.Domain.Server.Interface.ServerInterface;
import Src.Domain.ServiceOrder.ServiceOrderInterface;

public class Client implements ClientInterface {
    private ServerInterface server;

    public Client() {
        this.connectServer();
    }

    @Override
    public ServerInterface connectServer() {
        this.server = new Server();

        return this.server;
    }

    @Override
    public ServiceOrderInterface storeServiceOrder(ServiceOrderInterface serviceOrder) {
        return this.server.storeServiceOrder(serviceOrder);
    }

    @Override
    public boolean deleteServiceOrder(int orderId) {
        this.server.deleteServiceOrder(orderId);

        return true;
    }

    @Override
    public boolean deleteServiceOrder(ServiceOrderInterface serviceOrder) {
        this.server.deleteServiceOrder(serviceOrder);

        return true;
    }

    @Override
    public ServiceOrderInterface getServiceOrder(int orderId) {
        return this.server.getServiceOrder(orderId);
    }

    @Override
    public ServiceOrderInterface getServiceOrder(ServiceOrderInterface serviceOrder) {
        return this.server.getServiceOrder(serviceOrder);
    }

    @Override
    public ServiceOrderInterface updateServiceOrder(ServiceOrderInterface serviceOrder) {
        return this.server.updateServiceOrder(serviceOrder);
    }

    @Override
    public List<ServiceOrderInterface> listServiceOrders() {
        return this.server.listServiceOrders();
    }

    @Override
    public int countServiceOrders() {
        return this.server.listServiceOrders().size();
    }
}
