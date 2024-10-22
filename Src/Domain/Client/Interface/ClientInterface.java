package Src.Domain.Client.Interface;

import java.util.List;

import Src.Domain.Server.Interface.ServerInterface;
import Src.Domain.Server.Message.Message;
import Src.Domain.ServiceOrder.ServiceOrderInterface;

/**
 * ClientInterface
 */
public interface ClientInterface {
    public ServerInterface connectServer();
    public ServiceOrderInterface storeServiceOrder(Message message);
    public boolean deleteServiceOrder(Message message);
    public ServiceOrderInterface getServiceOrder(Message orderId);
    public ServiceOrderInterface updateServiceOrder(Message message);
    public List<ServiceOrderInterface> listServiceOrders();
    public int countServiceOrders();
}