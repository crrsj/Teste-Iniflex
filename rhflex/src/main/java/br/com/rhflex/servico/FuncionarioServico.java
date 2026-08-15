package br.com.iniflex.servico;

import br.com.iniflex.entidade.Funcionario;
import br.com.iniflex.entidade.Pessoa;
import br.com.iniflex.repositorio.FuncionarioRepositorio;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FuncionarioServico {
    private final FuncionarioRepositorio repo;
    private final BigDecimal SALARIO_MINIMO = new BigDecimal("1212.00");

    @Transactional
    public void salvarTodos(List<Funcionario> lista) { repo.saveAll(lista); }


    @Transactional
    public void removerPorNome(String nome) {
        repo.deleteByNome(nome);
    }

    @Transactional
    public void darAumento() {
        repo.findAll().forEach(f -> f.setSalario(f.getSalario().multiply(new BigDecimal("1.10"))));
    }


    public List<Funcionario> listar(boolean ordenado) {
        return ordenado ? repo.findAll(Sort.by("nome")) : repo.findAll();
    }


    public Map<String, List<Funcionario>> agruparPorFuncao() {
        return repo.findAll().stream().collect(Collectors.groupingBy(Funcionario::getFuncao));
    }


    public List<Funcionario> aniversariantesMes(int... meses) {
        Set<Integer> mesesSet = Arrays.stream(meses).boxed().collect(Collectors.toSet());
        return repo.findAll().stream()
                .filter(f -> mesesSet.contains(f.getDataNascimento().getMonthValue()))
                .toList();
    }


    public Funcionario buscarMaisVelho() {
        return repo.findAll().stream().min(Comparator.comparing(Pessoa::getDataNascimento)).orElse(null);
    }


    public BigDecimal totalSalarios() {
        return repo.findAll().stream().map(Funcionario::getSalario).reduce(BigDecimal.ZERO, BigDecimal::add);
    }


    public Map<String, BigDecimal> calcularQtdSalariosMinimos() {
        return repo.findAll().stream().collect(Collectors.toMap(
                Funcionario::getNome,
                f -> f.getSalario().divide(SALARIO_MINIMO, 2, RoundingMode.HALF_UP),
                (valorExistente, valorNovo) -> valorExistente
        ));
    }
}
