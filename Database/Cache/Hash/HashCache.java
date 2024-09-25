package Database.Cache.Hash;

import Src.Domain.ServiceOrder.ServiceOrder;
import Src.Domain.ServiceOrder.ServiceOrderInterface;
import Utils.Logger;

public class HashCache {
    private final float constA = 0.6180339887f; 
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

        int primaryHash = primaryHash(order.getCode());
        int k = 0;
        int index = primaryHash;

        if (this.size >= this.capacity) {
            this.table[index] = REMOVED;

            this.size--;
        }

        while (table[index] != null && table[index] != REMOVED) {
            k++;
            index = hash(order.getCode(), ++k);
        }

        table[index] = order;
        this.size++;

        return order;
    }

    public ServiceOrderInterface find(int key) {
        int primaryHash = primaryHash(key);
        int k = 0;
        int index = primaryHash;

        while (table[index] != null) {
            if (table[index] != REMOVED && table[index].getCode() == key) {

                return table[index];
            }

            k++;
            index = hash(key, ++k);

            if (index == primaryHash) {
                return null;
            }
        }

        return null;
    }

    public boolean delete(int key) {
        int primaryHash = primaryHash(key);
        int k = 0;
        int index = primaryHash;

        while (table[index] != null) {
            if (table[index] != REMOVED && table[index].getCode() == key) {
                table[index] = REMOVED;
                this.size--;

                this.printElements();

                return true;
            }

            k++;
            index = hash(key, ++k);

            if (index == primaryHash) {
                return false;
            }
        }

        return false;
    }

    private int hash(int key, int k) {
        return (primaryHash(key) + k * secondHash(key)) % capacity;
    }

    private int primaryHash(int key) {
        float temp = key * constA;
        temp = temp - (long) temp;
        return (int) (capacity * temp);
    }

    private int secondHash(int key) {
        return 1 + (key % (capacity - 1));
    }

    public void printElements() {
        for (int i = 0; i < capacity; i++) {
            if (this.table[i] != null) {
                this.logger.info(i + " - " + (table[i].getCode()));
            }
        }
    }
}