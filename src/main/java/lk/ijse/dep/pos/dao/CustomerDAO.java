package lk.ijse.dep.pos.dao;

import lk.ijse.dep.pos.entity.CustomEntity;
import lk.ijse.dep.pos.entity.Customer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerDAO extends JpaRepository<Customer, String> {

    @Query(value = "SELECT id FROM Customer ORDER BY id DESC LIMIT 1", nativeQuery = true)
    String getLastCustomerId() ;

    Customer getCustomerById(String id);

    List<Customer> getCustomersByNameLike(String query);

    Customer getFirstCustomerByOrderByIdDesc();

    Customer queryCustomerByIdAndName(String id, String name);

    int countAllCustomersBy();

    List<Customer> namedQuery1();

    Customer namedNativeQuery(String id);

//    @Query(value = "SELECT * FROM Customer WHERE id=?#{[0]} and name=?#{[1]}", nativeQuery = true)
//    Customer anotherQuery(String id,  String name);

//    @Query(value = "SELECT * FROM Customer WHERE id=?#{#c.id}", nativeQuery = true)
//    @Query(value = "SELECT * FROM Customer WHERE id=?#{[0].id}", nativeQuery = true)
//    Customer namedQuery2(@Param("c") Customer customer);

//    @Query(value = "SELECT c FROM Customer c WHERE c.name LIKE ?#{[0] + '%'}")
//    @Query(value = "SELECT c FROM Customer c WHERE c.name LIKE CONCAT(?#{[0]},'%') ")
//    @Query(value = "SELECT c FROM Customer c WHERE c.name LIKE :#{#query + '%'}")
    @Query(value = "SELECT c FROM Customer c WHERE c.name LIKE CONCAT(:#{#query},'%')")
    List<Customer> depQuery(@Param("query") String c);

//    @Query(value = "SELECT o.date, c.name FROM Order o INNER JOIN Customer c ON o.customer = c WHERE o.id=?1")
    @Query(value = "SELECT NEW lk.ijse.dep.pos.entity.CustomEntity(o.date, c.name) FROM" +
            " Order o INNER JOIN o.customer c WHERE o.id=:#{#order_id}")
    CustomEntity depQuery2(@Param("order_id") int id);

}
