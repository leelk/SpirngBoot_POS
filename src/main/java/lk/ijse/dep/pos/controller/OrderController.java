package lk.ijse.dep.pos.controller;

import lk.ijse.dep.pos.business.custom.OrderBO;
import lk.ijse.dep.pos.dto.OrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/orders")
public class OrderController {

    @Autowired
    private OrderBO orderBO;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Integer saveOrder(@RequestBody OrderDTO orderDTO){
        orderBO.placeOrder(orderDTO);
        return orderDTO.getId();
    }

}
