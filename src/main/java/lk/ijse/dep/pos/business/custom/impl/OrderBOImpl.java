package lk.ijse.dep.pos.business.custom.impl;

import lk.ijse.dep.pos.business.custom.OrderBO;
import lk.ijse.dep.pos.dao.*;
import lk.ijse.dep.pos.dto.OrderDTO;
import lk.ijse.dep.pos.dto.OrderDTO2;
import lk.ijse.dep.pos.dto.OrderDetailDTO;
import lk.ijse.dep.pos.entity.CustomEntity;
import lk.ijse.dep.pos.entity.Item;
import lk.ijse.dep.pos.entity.Order;
import lk.ijse.dep.pos.entity.OrderDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Transactional
@Component
public class OrderBOImpl implements OrderBO {

    @Autowired
    private OrderDAO orderDAO;
    @Autowired
    private OrderDetailDAO orderDetailDAO;
    @Autowired
    private ItemDAO itemDAO;
    @Autowired
    private QueryDAO queryDAO;
    @Autowired
    private CustomerDAO customerDAO;

    @Transactional(readOnly = true)
    @Override
    public int getLastOrderId()  {
        return orderDAO.getLastOrderId();
    }

    @Override
    public void placeOrder(OrderDTO order)  {
        int oId = order.getId();
        orderDAO.save(new Order(oId, new java.sql.Date(new Date().getTime()),
                customerDAO.findById(order.getCustomerId()).get()));
        for (OrderDetailDTO orderDetail : order.getOrderDetails()) {
            orderDetailDAO.save(new OrderDetail(oId, orderDetail.getCode(),
                    orderDetail.getQty(), orderDetail.getUnitPrice()));
            Item item = itemDAO.findById(orderDetail.getCode()).get();
            item.setQtyOnHand(item.getQtyOnHand() - orderDetail.getQty());
            itemDAO.save(item);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<OrderDTO2> getOrderInfo(String query)  {
        List<CustomEntity> ordersInfo = queryDAO.getOrdersInfo(query + "%");
        List<OrderDTO2> dtos = new ArrayList<>();
        for (CustomEntity info : ordersInfo) {
            dtos.add(new OrderDTO2(info.getOrderId(),
                    new java.sql.Date(info.getOrderDate().getTime()), info.getCustomerId(), info.getCustomerName(), info.getOrderTotal()));
        }
        return dtos;
    }
}
