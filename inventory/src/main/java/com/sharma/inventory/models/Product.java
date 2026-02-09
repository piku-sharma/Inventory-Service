package com.sharma.inventory.models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "product")
public class Product {
    @Id
    private Long productId;
    private String productName;

    @OneToMany(mappedBy = "product")
    @JsonManagedReference
    private List<Batch> batches = new ArrayList<Batch>();

}
