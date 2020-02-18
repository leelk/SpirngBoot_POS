package lk.ijse.dep.pos.business.custom.impl;

import lk.ijse.dep.pos.business.custom.ItemBO;
import lk.ijse.dep.pos.dao.ItemDAO;
import lk.ijse.dep.pos.dao.OrderDetailDAO;
import lk.ijse.dep.pos.dto.ItemDTO;
import lk.ijse.dep.pos.entity.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Component
public class ItemBOImpl implements ItemBO {

    @Autowired
    private OrderDetailDAO orderDetailDAO;
    @Autowired
    private ItemDAO itemDAO;

    @Override
    public void saveItem(ItemDTO item) {
        itemDAO.save(new Item(item.getCode(),
                item.getDescription(), item.getUnitPrice(), item.getQtyOnHand()));
    }

    @Override
    public void updateItem(ItemDTO item) {
        itemDAO.save(new Item(item.getCode(),
                item.getDescription(), item.getUnitPrice(), item.getQtyOnHand()));
    }

    @Override
    public void deleteItem(String itemCode) {
        if (orderDetailDAO.existsByItemCode(itemCode)) {
            throw new RuntimeException("Item already exists in an order, hence unable to delete");
        }
        itemDAO.deleteById(itemCode);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ItemDTO> findAllItems() {
        List<Item> allItems = itemDAO.findAll();
        List<ItemDTO> dtos = new ArrayList<>();
        for (Item item : allItems) {
            dtos.add(new ItemDTO(item.getCode(),
                    item.getDescription(),
                    item.getQtyOnHand(),
                    item.getUnitPrice()));
        }
        return dtos;
    }

    @Transactional(readOnly = true)
    @Override
    public String getLastItemCode() {
        String lastItemCode = itemDAO.getLastItemCode();
        return lastItemCode;
    }

    @Transactional(readOnly = true)
    @Override
    public ItemDTO findItem(String itemCode) {
        Item item = itemDAO.findById(itemCode).get();
        return new ItemDTO(item.getCode(),
                item.getDescription(),
                item.getQtyOnHand(),
                item.getUnitPrice());
    }

    @Transactional(readOnly = true)
    @Override
    public List<String> getAllItemCodes() {
        List<Item> allItems = itemDAO.findAll();
        List<String> codes = new ArrayList<>();
        for (Item item : allItems) {
            codes.add(item.getCode());
        }
        return codes;
    }
}
