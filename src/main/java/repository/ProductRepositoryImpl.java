package repository;

import com.bpi.module6.model.Product;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class ProductRepositoryImpl implements ProductRepository {
	
	private EntityManager em;
	
	public ProductRepositoryImpl(EntityManager em) {
		this.em = em;
	}

	@Override
	public Product getProductById(Long id) {
        return em.find(Product.class, id);
	}

	@Override
	public Product getProductByName(String name) {
        TypedQuery<Product> q = em.createQuery("SELECT p FROM Product p WHERE p.name = :name", Product.class);
        q.setParameter("name", name);
        return q.getSingleResult();
	}

	@Override
	public Product saveProduct(Product p) {
        if (p.getId() == null) {
            em.persist(p);
        } else {
            p = em.merge(p);
        }
        return p;
	}

	@Override
	public void deleteProduct(Product p) {
        if (em.contains(p)) {
            em.remove(p);
        } else {
            em.merge(p);
        }
	}

}
