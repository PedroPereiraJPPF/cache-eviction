package Database.Data.Hash;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import Src.Domain.ServiceOrder.ServiceOrderInterface;

public class HashDatabase implements Serializable {
    private final float constA = 0.6180339887f; 
    private int capacity;
    private int size;
    private CustomLinkedList[] table;
    
    public HashDatabase() {
        this.capacity = 20;
        this.size = 0;
        this.table = new CustomLinkedList[capacity];
    }

    public ServiceOrderInterface insert(ServiceOrderInterface order) {
        if ((this.size + 1) >= this.capacity) {
            this.resize();
        }

        int key = hash(order.getCode());

        if (table[key] == null) {
            table[key] = new CustomLinkedList(order);

            this.size++;

            return order;
        }

        return table[key].insert(order).value;
    }

    public ServiceOrderInterface search(int key) {
        int hash = this.hash(key);

        if (table[hash] == null) {
            return null;
        }

        Node node = table[hash].search(key);

        return node == null ? null : node.value;
    }

    public boolean delete(int key) {
        int hash = this.hash(key);

        if (table[hash] == null) {
            return false;
        }

        boolean result = table[hash].delete(key);

        if (table[hash].getHead() == null) {
            table[hash] = null;
            this.size--;
        }

        return result;
    }

    public List<ServiceOrderInterface> list() {
        List<ServiceOrderInterface> orders = new LinkedList<>();

        for (int i = 0; i < this.capacity; i++) {
            if (table[i] != null) {
                Node current = table[i].getHead();

                while (current != null) {
                    orders.add(current.value);
                    current = current.next;
                }
            }
        }

        return orders;
    }

    public int countElements() {
        return this.size;
    }

    // usando o metodo da mutiplicacao. pois ele tem um bom espalhamento e é menos dependente do tamanho da tabela
    private int hash(int key) {
        float temp = key * constA;
        temp = temp - (long) temp;
        return (int) (capacity * temp);
    }

    private void resize() {
        int newCapacity = this.capacity * 2;
        CustomLinkedList[] newTable = new CustomLinkedList[newCapacity];

        for (int i = 0; i < this.capacity; i++) {
            if (table[i] != null) {
                Node current = table[i].getHead();

                while (current != null) {
                    int key = hash(current.value.getCode());

                    if (newTable[key] == null) {
                        newTable[key] = new CustomLinkedList(current.value);
                    } else {
                        newTable[key].insert(current.value);
                    }

                    current = current.next;
                }
            }
        }
    }

    public long getSize() {
        return this.size;
    }

    public long getCapacity() {
        return this.capacity;
    }
}
