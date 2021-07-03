package uz.pdp.appcardtransfer.payload;

import lombok.Data;

@Data
public class TransferDto {
    private Integer fromCardId;
    private Integer toCardId;
    private Double amount;
}
