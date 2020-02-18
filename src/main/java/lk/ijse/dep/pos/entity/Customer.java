package lk.ijse.dep.pos.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQuery(query = "SELECT c FROM Customer c", name="Customer.namedQuery1")
@NamedNativeQuery(query="SELECT * FROM Customer WHERE id=?1", name = "Customer.namedNativeQuery",
        resultClass = Customer.class)
//@NamedNativeQuery(query = "SELECT * FROM Customer WHERE id=?#{[0]} and name=?1",
//        name="Customer.anotherQuery", resultClass = Customer.class)
public class Customer implements SuperEntity{

    @Id
    private String id;
    private String name;
    private String address;
    @OneToMany(mappedBy = "customer", cascade = {CascadeType.PERSIST,CascadeType.REFRESH, CascadeType.DETACH, CascadeType.MERGE})
    private List<Order> orders = new ArrayList<>();

    public Customer() {
    }

    public Customer(String id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void addOrder(Order order){
        order.setCustomer(this);
        this.orders.add(order);
    }

    public void removeOrder(Order order){
        if (order.getCustomer() != this){
            throw new RuntimeException("Invalid order");
        }
        order.setCustomer(null);
        this.orders.remove(order);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
