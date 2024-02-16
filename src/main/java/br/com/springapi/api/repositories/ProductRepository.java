package br.com.springapi.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.springapi.api.models.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {

}
