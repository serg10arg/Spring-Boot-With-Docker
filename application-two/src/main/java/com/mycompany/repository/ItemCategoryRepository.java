package com.mycompany.repository;

import com.mycompany.model.ItemCategoryModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ItemCategoryRepository extends MongoRepository<ItemCategoryModel, String> {

}
