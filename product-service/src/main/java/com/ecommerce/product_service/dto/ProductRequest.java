package com.ecommerce.product_service.dto;

import java.math.BigDecimal;

public record ProductRequest (String name, String description, BigDecimal price){

    public static class Builder {
        private String name;
        private String description;
        private BigDecimal price;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder price(BigDecimal price) {
            this.price = price;
            return this;
        }

        public ProductRequest build() {
            return new ProductRequest(name, description, price);
        }
    }
}
