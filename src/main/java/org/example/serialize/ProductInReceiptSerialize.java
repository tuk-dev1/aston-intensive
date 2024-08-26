package org.example.serialize;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductInReceiptSerialize {
    private ProductSerialize product;
    private Integer count;
}
