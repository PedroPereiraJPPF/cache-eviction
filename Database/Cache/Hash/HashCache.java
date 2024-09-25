package Database.Cache.Hash;

import Src.Domain.ServiceOrder.ServiceOrder;
import Src.Domain.ServiceOrder.ServiceOrderInterface;
import Utils.Logger;

public class HashCache {
    private int capacity;
    private int size;
    private ServiceOrderInterface[] table;
    private final ServiceOrderInterface REMOVED = new ServiceOrder(-1);
    private Logger logger = new Logger("Logs/ServerLogs.log"); 
    
    public HashCache() {
        this.capacity = 20;
        this.size = 0;
        this.table = new ServiceOrderInterface[capacity];
    }

    public ServiceOrderInterface insert(ServiceOrderInterface order) {
        if (this.find(order.getCode()) != null) {
            return null;
        }

        int k = 0;
        int primaryHash = hash(order.getCode(), k);
        int index = primaryHash;

        if (this.size >= this.capacity) {
            this.table[index] = REMOVED;

            this.size--;
        }

        while (table[index] != null && table[index] != REMOVED) {
            k++;
            index = hash(order.getCode(), k);

            if (index == primaryHash) { // quando um ciclo é detectado remove um outro elemento aleatório
                table[primaryHash] = order;

                return order;
            }
        }

        table[index] = order;
        this.size++;

        return order;
    }

    public ServiceOrderInterface find(int key) {
        int k = 0;
        int primaryHash = hash(key, k);
        int index = primaryHash;

        while (table[index] != null) {
            if (table[index] != REMOVED && table[index].getCode() == key) {

                return table[index];
            }

            k++;
            index = hash(key, k);

            if (index == primaryHash) {
                return null;
            }
        }

        return null;
    }

    public boolean delete(int key) {
        int k = 0;
        int primaryHash = hash(key, k);
        int index = primaryHash;

        while (table[index] != null) {
            if (table[index] != REMOVED && table[index].getCode() == key) {
                table[index] = REMOVED;
                this.size--;

                this.printElements();

                return true;
            }

            k++;
            index = hash(key, k);

            if (index == primaryHash) {
                return false;
            }
        }

        return false;
    }

    private int hash(int key, int k) {
        return Math.abs((key % this.capacity + k * (1 + key % (this.capacity - 1))) % this.capacity);
    }

    public void printElements() {
        for (int i = 0; i < capacity; i++) {
            if (this.table[i] != null) {
                this.logger.info(i + " - " + (table[i].getCode()));
            }
        }
    }
}