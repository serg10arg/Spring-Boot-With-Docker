package com.mycompany.controller;

import com.mycompany.model.ItemCategoryModel;
import com.mycompany.repository.ItemCategoryRepository;
import com.mycompany.repository.ItemRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ItemCategoryController {

    private ItemCategoryRepository itemCategoryRepository;
    private ItemRepository itemRepository;

    public ItemCategoryController(ItemCategoryRepository itemCategoryRepository, ItemRepository itemRepository) {
        this.itemCategoryRepository = itemCategoryRepository;
        this.itemRepository = itemRepository;
    }

    @GetMapping("/categories")
    public List<ItemCategoryModel> getAllItemCategory() {
        return itemCategoryRepository.findAll();
    }

    @GetMapping("/categories/{id}")
    public ItemCategoryModel getItemCategory(@PathVariable String id) {
        return itemCategoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Cannot Find Item Category By ID: " + id));
    }

    @PostMapping("/categories")
    public ResponseEntity<String> saveItemCategory(@RequestBody ItemCategoryModel categoryModel) {
        ItemCategoryModel savedItem = itemCategoryRepository.insert(categoryModel);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedItem.getId())
                .toUri();

        //http://localhost:8081/api/v1/categories/611b7bcfef59e87f2e0e0d60
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/categories/{id}")
    public ResponseEntity<ItemCategoryModel> updateItemCategory(@PathVariable String id, @RequestBody ItemCategoryModel item) {
        ItemCategoryModel imFromDB = itemCategoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Cannot Find Item Category By ID: " + id));
        BeanUtils.copyProperties(item, imFromDB);
        imFromDB = itemCategoryRepository.save(imFromDB);
        return new ResponseEntity<>(imFromDB, HttpStatus.OK);
    }

    @DeleteMapping("/categories/{id}")
    public ResponseEntity<String> deleteItemCategory(@PathVariable String id) {
        itemRepository.deleteAllByCategoryId(id);
        itemCategoryRepository.deleteById(id);
        ResponseEntity<String> re = new ResponseEntity<>(id, HttpStatus.OK);
        return re;
    }
}
