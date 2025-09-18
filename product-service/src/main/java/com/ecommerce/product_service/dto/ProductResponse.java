package com.ecommerce.product_service.dto;

import java.math.BigDecimal;

public record ProductResponse(String id, String name, String description, BigDecimal price) {
    // Builder manual
    public static class Builder {
        private String id;
        private String name;
        private String description;
        private BigDecimal price;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

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

        public ProductResponse build() {
            return new ProductResponse(id, name, description, price);
        }
    }
}
