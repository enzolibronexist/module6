package com.bpi.module6.model;

import java.math.BigDecimal;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "products")
public class Product {
	
//  prior hibernate 6.5+
//	@Id
//	@Column(name = "product_id")
//	@GeneratedValue(strategy = GenerationType.UUID)
//	@UuidGenerator	
//	private UUID id;
	
//    for Hibernate 6.5+
//    @Id
//    @GeneratedValue(strategy = GenerationType.UUID)
//    @Column(name = "product_id", updatable = false, nullable = false)
//    private UUID id;
	
	@Id
	@Column(name = "product_id")
	@GeneratedValue
	private Long id;
	
	@Column(name = "product_name", nullable = false, unique = true, columnDefinition = "VARCHAR(100)")
	private String productName;
	
	@Column(name = "product_price", precision = 10, scale = 2, nullable = false)
	private BigDecimal price;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
}
