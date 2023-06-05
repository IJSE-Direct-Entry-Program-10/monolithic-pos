package lk.ijse.dep10.pos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO2 {
    private Integer orderId;
    private LocalDateTime orderDate;
    private Integer customerId;
    private String customerName;
    private BigDecimal orderTotal;
}
