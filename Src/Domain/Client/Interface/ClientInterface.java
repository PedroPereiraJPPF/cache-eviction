package Src.Domain.Client.Interface;

import java.util.List;

import Src.Domain.Server.Interface.ServerInterface;
import Src.Domain.ServiceOrder.ServiceOrderInterface;

/**
 * ClientInterface
 */
public interface ClientInterface {
    public ServerInterface connectServer();
    public ServiceOrderInterface storeServiceOrder(ServiceOrderInterface serviceOrder);
    public boolean deleteServiceOrder(int orderId);
    public boolean deleteServiceOrder(ServiceOrderInterface serviceOrder);
    public ServiceOrderInterface getServiceOrder(int orderId);
    public ServiceOrderInterface getServiceOrder(ServiceOrderInterface serviceOrder);
    public ServiceOrderInterface updateServiceOrder(ServiceOrderInterface serviceOrder);
    public List<ServiceOrderInterface> listServiceOrders();
    public int countServiceOrders();
}