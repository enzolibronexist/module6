package repository;

import com.bpi.module6.model.Product;

public interface ProductRepository {
	
	Product getProductById(Long id);
	
	Product getProductByName(String name);
    
	Product saveProduct(Product p);
     
    void deleteProduct(Product p);

}
