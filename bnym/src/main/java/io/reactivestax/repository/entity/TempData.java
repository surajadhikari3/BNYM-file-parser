package io.reactivestax.repository.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TempData {
    private String ruleType;
    private int leftNode;
    private int rightNode;
}
