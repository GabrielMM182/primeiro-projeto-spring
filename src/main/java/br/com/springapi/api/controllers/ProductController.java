package br.com.springapi.api.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.springapi.api.models.Product;
import br.com.springapi.api.repositories.ProductRepository;
import br.com.springapi.api.utils.ResponseHandler;

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
    public ResponseEntity<Object> getProduct(@PathVariable Integer id) { // utilizar parametros para busca
        Optional<Product> product = productRepository.findById(id); // como o id pode não existir vamos usar o Optional

        if (!product.isPresent()) {
            return ResponseHandler.generate("Produto não encontrado", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Object>(product.get(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Object> createProduct(@RequestBody Product product) {
        Product newProduct = productRepository.save(product);
        return new ResponseEntity<Object>(newProduct, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable Integer id, @RequestBody Product product) {
        Optional<Product> oldProduct = productRepository.findById(id);

        if (!oldProduct.isPresent()) {
            return ResponseHandler.generate("Produto não encontrado", HttpStatus.NOT_FOUND);
        }

        Product updateProduct = oldProduct.get();
        updateProduct.setName(product.getName());
        updateProduct.setDescription(product.getDescription());
        updateProduct.setPrice(product.getPrice());

        productRepository.save(updateProduct);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Integer id) {
        Optional<Product> oldProduct = productRepository.findById(id);

        if (!oldProduct.isPresent()) {
            return ResponseHandler.generate("Produto não encontrado", HttpStatus.NOT_FOUND);
        }

        productRepository.delete(oldProduct.get());
        return ResponseEntity.noContent().build();
    }

}