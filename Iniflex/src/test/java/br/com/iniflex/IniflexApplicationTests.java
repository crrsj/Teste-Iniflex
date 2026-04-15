package br.com.iniflex;

import br.com.iniflex.entidade.Funcionario;
import br.com.iniflex.repositorio.FuncionarioRepositorio;
import br.com.iniflex.servico.FuncionarioServico;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IniflexApplicationTests {


	@Mock
	private FuncionarioRepositorio repo;

	@InjectMocks
	private FuncionarioServico service;

	@Test
	@DisplayName("Deve calcular corretamente a quantidade de salários mínimos")
	void deveCalcularSalariosMinimos() {
		Funcionario f = new Funcionario();
		f.setNome("Maria");
		f.setSalario(new BigDecimal("2424.00"));
		when(repo.findAll()).thenReturn(List.of(f));
		Map<String, BigDecimal> resultado = service.calcularQtdSalariosMinimos();
		assertEquals(new BigDecimal("2.00"), resultado.get("Maria"));
	}

	@Test
	@DisplayName("Deve aplicar o aumento de 10% a todos os funcionários")
	void deveAplicarAumento() {
		Funcionario f = new Funcionario();
		f.setSalario(new BigDecimal("1000.00"));
		when(repo.findAll()).thenReturn(List.of(f));
		service.darAumento();
		assertTrue(new BigDecimal("1100.00").compareTo(f.getSalario()) == 0);
	}

	@Test
	@DisplayName("3.1 - Deve chamar o repositório para salvar uma lista de funcionários")
	void deveSalvarTodosOsFuncionarios() {
		List<Funcionario> lista = List.of(new Funcionario(), new Funcionario());
		service.salvarTodos(lista);
		verify(repo, times(1)).saveAll(lista);
	}

	@Test
	@DisplayName("3.2 - Deve chamar o repositório para remover um funcionário pelo nome")
	void deveRemoverFuncionarioPorNome() {
		String nomeParaRemover = "João";
		service.removerPorNome(nomeParaRemover);
		verify(repo, times(1)).deleteByNome(nomeParaRemover);
	}

	@Test
	@DisplayName("3.10 - Deve listar funcionários ordenados por nome")
	void deveListarOrdenado() {
		service.listar(true);
		verify(repo, times(1)).findAll(Sort.by("nome"));
	}

	@Test
	@DisplayName("3.5/3.6 - Deve agrupar funcionários por função")
	void deveAgruparPorFuncao() {
		Funcionario f1 = new Funcionario();
		f1.setFuncao("Gerente");
		Funcionario f2 = new Funcionario();
		f2.setFuncao("Operador");

		when(repo.findAll()).thenReturn(List.of(f1, f2));

		Map<String, List<Funcionario>> resultado = service.agruparPorFuncao();

		assertEquals(2, resultado.size());
		assertTrue(resultado.containsKey("Gerente"));
		assertTrue(resultado.containsKey("Operador"));
	}

	@Test
	@DisplayName("3.8 - Deve filtrar aniversariantes dos meses 10 e 12")
	void deveFiltrarAniversariantes() {
		Funcionario f1 = new Funcionario();
		f1.setDataNascimento(LocalDate.of(1990, 10, 10));
		Funcionario f2 = new Funcionario();
		f2.setDataNascimento(LocalDate.of(1990, 5, 10));

		when(repo.findAll()).thenReturn(List.of(f1, f2));

		List<Funcionario> resultado = service.aniversariantesMes(10, 12);

		assertEquals(1, resultado.size());
		assertEquals(10, resultado.get(0).getDataNascimento().getMonthValue());
	}

	@Test
	@DisplayName("3.9 - Deve encontrar o funcionário mais velho")
	void deveBuscarMaisVelho() {
		Funcionario velho = new Funcionario();
		velho.setNome("Caio");
		velho.setDataNascimento(LocalDate.of(1961, 5, 2));

		Funcionario novo = new Funcionario();
		novo.setNome("Alice");
		novo.setDataNascimento(LocalDate.of(1995, 1, 5));

		when(repo.findAll()).thenReturn(List.of(velho, novo));

		Funcionario resultado = service.buscarMaisVelho();

		assertEquals("Caio", resultado.getNome());
	}

	@Test
	@DisplayName("3.11 - Deve calcular o total de todos os salários")
	void deveCalcularTotalSalarios() {
		Funcionario f1 = new Funcionario();
		f1.setSalario(new BigDecimal("1500.00"));
		Funcionario f2 = new Funcionario();
		f2.setSalario(new BigDecimal("2500.00"));

		when(repo.findAll()).thenReturn(List.of(f1, f2));

		BigDecimal total = service.totalSalarios();

		assertTrue(new BigDecimal("4000.00").compareTo(total) == 0);
	}
}
