package lk.ijse.dep.pos.controller;

import lk.ijse.dep.pos.business.custom.CustomerBO;
import lk.ijse.dep.pos.dto.CustomerDTO;
import lk.ijse.dep.pos.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RequestMapping("/api/v1/customers")
@CrossOrigin
@RestController
public class CustomerController {

    @Autowired
    private CustomerBO customerBO;

    @GetMapping
    public ResponseEntity<List<CustomerDTO>> getAllCustomers() {
        List<CustomerDTO> allCustomers = customerBO.findAllCustomers();


        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("X-Count",allCustomers.size() + "");
        return new ResponseEntity<>(allCustomers,httpHeaders,HttpStatus.OK);
    }

/*    @GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerDTO> getCustomer(@PathVariable String id){
               CustomerDTO customer = customerBO.findCustomer(id);
        if (customer == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else{
            return new ResponseEntity<>(customer, HttpStatus.OK);
        }
    }*/

/*    @GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerDTO> getCustomer(@PathVariable String id){
        try {
            CustomerDTO customer = customerBO.findCustomer(id);
            return new ResponseEntity<>(customer, HttpStatus.OK);
        }catch (NullPointerException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }*/

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CustomerDTO getCustomer(@PathVariable String id) {
        try {
            return customerBO.findCustomer(id);
        } catch (NullPointerException e) {
            throw new NotFoundException(e);
        }
    }

//    @ExceptionHandler({NotFoundException.class, EntityNotFoundException.class})
//    public ResponseEntity handleNotFoundException(Exception e) {
//        return new ResponseEntity(HttpStatus.NOT_FOUND);
//    }

    @GetMapping(params = "q=last")
    public String getLastCustomerId() {
        return customerBO.getLastCustomerId();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String saveCustomer(@RequestBody CustomerDTO customer) {
        customerBO.saveCustomer(customer);
        return "\"" + customer.getId() + "\"";
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable String id) {
        customerBO.deleteCustomer(id);
    }

/*    @PutMapping("/{id}")
    public ResponseEntity updateCustomer(@PathVariable String id,
                               @RequestBody CustomerDTO customer){
        if (id.equals(customer.getId())){
            customerBO.updateCustomer(customer);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }*/

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    public void updateCustomer(@PathVariable String id,
                               @RequestBody CustomerDTO customer) {
        customer.setId(id);
        customerBO.updateCustomer(customer);
    }

    @GetMapping(params = "category=id")
    public List<String> getAllCustomerIds() {
        return customerBO.getAllCustomerIDs();
    }
}
