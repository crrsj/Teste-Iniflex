package br.com.iniflex.entidade;

import jakarta.persistence.MappedSuperclass;
import lombok.*;

import java.time.LocalDate;


@MappedSuperclass
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Pessoa {
    private String nome;
    private LocalDate dataNascimento;



}
