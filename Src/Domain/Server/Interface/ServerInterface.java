package Src.Domain.Server.Interface;

import java.text.ParseException;
import java.util.List;

import Src.Domain.Server.Message.Message;
import Src.Domain.ServiceOrder.ServiceOrderInterface;

public interface ServerInterface {
    public List<ServiceOrderInterface> listServiceOrders();
    public ServiceOrderInterface getServiceOrder(Message message);
    public ServiceOrderInterface getServiceOrder(int code);
    public ServiceOrderInterface getServiceOrder(ServiceOrderInterface serviceOrder);
    public ServiceOrderInterface storeServiceOrder(Message message);
    public ServiceOrderInterface storeServiceOrder(ServiceOrderInterface serviceOrder);
    public void deleteServiceOrder(Message message);
    public void deleteServiceOrder(int code);
    public void deleteServiceOrder(ServiceOrderInterface serviceOrder);
    public ServiceOrderInterface updateServiceOrder(Message Message) throws ParseException;
    public ServiceOrderInterface updateServiceOrder(ServiceOrderInterface serviceOrder);
    public int countServiceOrders();
}
