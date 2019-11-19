package com.llibaiv.data;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.llibaiv.model.Product;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {

	@Query("{_id: { $in: ?0 } })")
    List<Product> findByIds(List<String> ids);	
}