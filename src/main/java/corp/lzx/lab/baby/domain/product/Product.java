package corp.lzx.lab.baby.domain.product;

import java.math.BigDecimal;

public record Product(Long id, String description, Integer quantity, BigDecimal price) {
}
