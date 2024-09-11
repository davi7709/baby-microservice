package corp.lzx.lab.baby.domain.person;

import java.math.BigDecimal;

public record Person(Long id, String name, Integer registry, String password) {
}
