package com.mycompany.controller;

import com.mycompany.model.ItemModel;
import com.mycompany.repository.ItemRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.net.URI;

@RestController
@RequestMapping("/api/v1")
public class ItemController {

    private ItemRepository itemRepository;

    public ItemController(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @GetMapping("/items")
    public List<ItemModel> getAllItems() {
        return itemRepository.findAll();
    }

    @GetMapping("/items/{id}")
    public ItemModel getItem(@PathVariable String id) {
        return itemRepository.findById(id).orElseThrow(() -> new RuntimeException("Cannot Find Item By ID: " + id));
    }

    @PostMapping("/items")
    public ResponseEntity<String> saveItem(@RequestBody ItemModel item) {
        ItemModel savedItem = itemRepository.insert(item);

        // 1. Toma la URL actual (http://.../api/v1/items)
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()

                // 2. Le añade "/{id}" al final
                .path("/{id}")
                // 3. Reemplaza "{id}" con el ID real del item guardado
                .buildAndExpand(savedItem.getId())
                // 4. Convierte todo en un objeto URI (una URL bien formada)
                .toUri();

        //http://localhost:8081/api/v1/items/611b7bcfef59e87f2e0e0d60
        return ResponseEntity.created(uri).build();

    }

    @PutMapping("/items/{id}")
    public ResponseEntity<ItemModel> updateItem(@PathVariable String id, @RequestBody ItemModel item) {
        ItemModel imFromDB = itemRepository.findById(id).orElseThrow(() -> new RuntimeException("Cannot Find Item By ID: " + id));
        BeanUtils.copyProperties(item, imFromDB);
        imFromDB = itemRepository.save(imFromDB);
        return new ResponseEntity<>(imFromDB, HttpStatus.OK);
    }

    @PatchMapping("/items/{id}")
    public ResponseEntity<ItemModel> updateItemPrice(@PathVariable String id, @RequestBody ItemModel item) {
        ItemModel imFromDB = itemRepository.findById(id).orElseThrow(() -> new RuntimeException("Cannot Find Item By ID: " + id));
        imFromDB.setPrice(item.getPrice());
        imFromDB = itemRepository.save(imFromDB);
        return new ResponseEntity<>(imFromDB, HttpStatus.OK);
    }

    @DeleteMapping("/items/{id}")
    public ResponseEntity<String> deleteItem(@PathVariable String id) {
        itemRepository.deleteById(id);
        ResponseEntity<String> re = new ResponseEntity<>(id, HttpStatus.OK);
        return re;
    }

}
