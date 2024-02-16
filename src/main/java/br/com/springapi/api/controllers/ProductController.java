package br.com.springapi.api.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.springapi.api.models.Product;
import br.com.springapi.api.repositories.ProductRepository;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired // serve para conseguir reutilizar o product repository
    private ProductRepository productRepository;

    @GetMapping
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable Integer id) { // utilizar parametros para busca
        Optional<Product> product = productRepository.findById(id); // como o id pode não existir vamos usar o Optional

        if (!product.isPresent()) {
            // return ResponseHandler.generate("product not found", HttpStatus.NOT_FOUND);
            return ResponseEntity.notFound().build();
        }

        return new ResponseEntity<Product>(product.get(), HttpStatus.OK);
    }

}