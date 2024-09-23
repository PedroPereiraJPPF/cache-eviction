package Src.Domain.Server.Interface;

import java.util.List;

import Src.Domain.ServiceOrder.ServiceOrderInterface;

public interface ServerInterface {
    public List<ServiceOrderInterface> listServiceOrders();
    public ServiceOrderInterface getServiceOrder(int code);
    public ServiceOrderInterface getServiceOrder(ServiceOrderInterface serviceOrder);
    public ServiceOrderInterface storeServiceOrder(ServiceOrderInterface serviceOrder);
    public void deleteServiceOrder(int code);
    public void deleteServiceOrder(ServiceOrderInterface serviceOrder);
    public ServiceOrderInterface updateServiceOrder(ServiceOrderInterface serviceOrder);
    public int countServiceOrders();
}
