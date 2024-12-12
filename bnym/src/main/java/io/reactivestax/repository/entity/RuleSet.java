package io.reactivestax.repository.entity;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Entity
@Builder
@Data
@Table(name = "rule_set")
public class RuleSet {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    private String leftNode;

    private String rightNode;

    private String value;

}
