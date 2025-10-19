package com.mycompany.repository;

import com.mycompany.model.ItemModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ItemRepository extends MongoRepository<ItemModel, String> {
    void deleteAllByCategoryId(String categoryId);
}
