package com.shop.ecommerce.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "attribute_values", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"attribute_id", "value"})
})
public class AttributeValue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attribute_id", nullable = false)
    private Attribute attribute;

    @Column(name = "value", nullable = false)
    private String value;

    @Column(name = "sort_order")
    private Integer sortOrder = 0;

    // Constructors, Getters and Setters
    public AttributeValue() {}

    public AttributeValue(Attribute attribute, String value) {
        this.attribute = attribute;
        this.value = value;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Attribute getAttribute() { return attribute; }
    public void setAttribute(Attribute attribute) { this.attribute = attribute; }
    public String getValue() { return value; }
    public void setValue(String value) { this.value = value; }
    public Integer getSortOrder() { return sortOrder; }
    public void setSortOrder(Integer sortOrder) { this.sortOrder = sortOrder; }
}
