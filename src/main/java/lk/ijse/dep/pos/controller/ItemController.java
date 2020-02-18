package lk.ijse.dep.pos.controller;

import lk.ijse.dep.pos.business.custom.ItemBO;
import lk.ijse.dep.pos.dto.ItemDTO;
import lk.ijse.dep.pos.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/items")
public class ItemController {

    @Autowired
    private ItemBO itemBO;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ItemDTO>> getAllItems() {
        List<ItemDTO> allItems = itemBO.findAllItems();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("X-Count",allItems.size() + "");
        return new ResponseEntity<>(allItems,httpHeaders, HttpStatus.OK);
    }

    @GetMapping(value = "/{code}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ItemDTO getItem(@PathVariable String code) {
        try {
            return itemBO.findItem(code);
        } catch (NullPointerException e) {
            throw new NotFoundException(e);
        }
    }

//    @ExceptionHandler({NotFoundException.class, EntityNotFoundException.class})
//    public ResponseEntity handleNotFoundException(Exception e) {
//        return new ResponseEntity(HttpStatus.NOT_FOUND);
//    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String saveItem(@RequestBody ItemDTO item) {
        itemBO.saveItem(item);
        return "\"" + item.getCode() + "\"";
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{code}")
    public void deleteItem(@PathVariable String code) {
        itemBO.deleteItem(code);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{code}")
    public void updateItem(@PathVariable String code,
                               @RequestBody ItemDTO item) {
        item.setCode(code);
        itemBO.updateItem(item);
    }

}
