package org.example.deserialize;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddProductToReceiptDeserialize {
    private Integer receiptId;
    private Integer productId;
    private Integer quantity;
}
