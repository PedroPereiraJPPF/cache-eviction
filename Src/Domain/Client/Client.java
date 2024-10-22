package Src.Domain.Client;

import java.text.ParseException;
import java.util.List;

import Src.Domain.Client.Interface.ClientInterface;
import Src.Domain.Server.Server;
import Src.Domain.Server.Interface.ServerInterface;
import Src.Domain.Server.Message.Message;
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
    public ServiceOrderInterface storeServiceOrder(Message message) {
        return this.server.storeServiceOrder(message);
    }

    @Override
    public boolean deleteServiceOrder(Message message) {
        this.server.deleteServiceOrder(message);

        return true;
    }

    @Override
    public ServiceOrderInterface getServiceOrder(Message message) {
        return this.server.getServiceOrder(message);
    }

    @Override
    public ServiceOrderInterface updateServiceOrder(Message message) {
        try {
            return this.server.updateServiceOrder(message);
        } catch (ParseException e) {
            return null;
        }
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
