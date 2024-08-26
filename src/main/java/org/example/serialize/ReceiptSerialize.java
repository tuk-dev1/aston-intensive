package org.example.serialize;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ReceiptSerialize {
    private Integer id;
    private List<ProductInReceiptSerialize>  productList;
}
