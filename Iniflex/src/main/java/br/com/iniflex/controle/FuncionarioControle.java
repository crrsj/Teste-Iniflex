package br.com.iniflex.controle;

import br.com.iniflex.entidade.Funcionario;
import br.com.iniflex.servico.FuncionarioServico;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/funcionarios")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class FuncionarioControle {

    private final FuncionarioServico funcionarioServico;


    @PostMapping("/lote")
    public ResponseEntity<Void> inserirFuncionarios(@RequestBody List<Funcionario> lista) {
        funcionarioServico.salvarTodos(lista);
        return ResponseEntity.status(201).build();
    }
    @GetMapping
    public ResponseEntity<List<Funcionario>> listarTodos() {
        return ResponseEntity.ok(funcionarioServico.listar(false));
    }


    @GetMapping("/ordenados")
    public ResponseEntity<List<Funcionario>> listarOrdenados() {
        return ResponseEntity.ok(funcionarioServico.listar(true));
    }


    @DeleteMapping("/{nome}")
    public ResponseEntity<Void> removerPorNome(@PathVariable String nome) {
        funcionarioServico.removerPorNome(nome);
        return ResponseEntity.noContent().build();
    }


    @PutMapping("/aumento")
    public ResponseEntity<Void> darAumento() {
        funcionarioServico.darAumento();
        return ResponseEntity.ok().build();
    }


    @GetMapping("/agrupados-por-funcao")
    public ResponseEntity<Map<String, List<Funcionario>>> agruparPorFuncao() {
        return ResponseEntity.ok(funcionarioServico.agruparPorFuncao());
    }

    @GetMapping("/aniversariantes")
    public ResponseEntity<List<Funcionario>> buscarAniversariantes() {
        return ResponseEntity.ok(funcionarioServico.aniversariantesMes(10, 12));
    }


    @GetMapping("/mais-velho")
    public ResponseEntity<Funcionario> buscarMaisVelho() {
        return ResponseEntity.ok(funcionarioServico.buscarMaisVelho());
    }


    @GetMapping("/total-salarios")
    public ResponseEntity<BigDecimal> totalSalarios() {
        return ResponseEntity.ok(funcionarioServico.totalSalarios());
    }


    @GetMapping("/salarios-minimos")
    public ResponseEntity<Map<String, BigDecimal>> calcularSalariosMinimos() {
        return ResponseEntity.ok(funcionarioServico.calcularQtdSalariosMinimos());
    }
}
