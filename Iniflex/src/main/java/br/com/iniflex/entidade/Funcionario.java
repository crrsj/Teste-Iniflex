package br.com.iniflex.entidade;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "funcionarios")
@Getter
@Setter
@NoArgsConstructor
public class Funcionario extends Pessoa{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(precision = 15, scale = 2, nullable = false)
    private BigDecimal salario;

    @Column(nullable = false)
    private String funcao;

    public Funcionario(String nome, LocalDate dataNascimento, BigDecimal salario, String funcao) {
        super(nome, dataNascimento);
        this.salario = salario;
        this.funcao = funcao;
    }
}
