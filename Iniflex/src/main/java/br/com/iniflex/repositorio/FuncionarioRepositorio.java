package br.com.iniflex.repositorio;

import br.com.iniflex.entidade.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FuncionarioRepositorio extends JpaRepository<Funcionario,Long> {
    void deleteByNome(String nome);
}
