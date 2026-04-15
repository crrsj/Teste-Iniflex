const API_URL = "http://localhost:8080/api/funcionarios";


const formatMoeda = (v) => new Intl.NumberFormat('pt-BR', { style: 'currency', currency: 'BRL' }).format(v);
const formatData = (d) => new Date(d).toLocaleDateString('pt-BR', {timeZone: 'UTC'});


async function cargaInicial() {
    const dados = [
        {nome: "Maria", dataNascimento: "2000-10-18", salario: 2009.44, funcao: "Operador"},
        {nome: "João", dataNascimento: "1990-05-12", salario: 2284.38, funcao: "Operador"},
        {nome: "Caio", dataNascimento: "1961-05-02", salario: 9836.14, funcao: "Coordenador"},
        {nome: "Miguel", dataNascimento: "1988-10-14", salario: 19119.88, funcao: "Diretor"},
        {nome: "Alice", dataNascimento: "1995-01-05", salario: 2234.68, funcao: "Recepcionista"},
        {nome: "Heitor", dataNascimento: "1999-11-19", salario: 1582.72, funcao: "Operador"},
        {nome: "Arthur", dataNascimento: "1993-03-31", salario: 4071.84, funcao: "Contador"},
        {nome: "Laura", dataNascimento: "1994-07-08", salario: 3017.45, funcao: "Gerente"},
        {nome: "Heloísa", dataNascimento: "2003-05-24", salario: 1606.85, funcao: "Eletricista"},
        {nome: "Helena", dataNascimento: "1996-06-02", salario: 2799.93, funcao: "Gerente"}
    ];
    await fetch(`${API_URL}/lote`, {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(dados)
    });
    buscarTodos();
}


async function buscarTodos() { carregarTabela(API_URL, "Lista Geral"); }
async function buscarOrdenados() { carregarTabela(`${API_URL}/ordenados`, "Lista Ordenada (A-Z)"); }
async function buscarAniversariantes() { carregarTabela(`${API_URL}/aniversariantes`, "Aniversariantes de Outubro e Dezembro"); }


async function carregarTabela(url, titulo) {
   
    document.getElementById('tabelaPrincipal').style.display = 'table';
    document.getElementById('tituloTabela').style.display = 'block';
    document.getElementById('tituloTabela').innerText = titulo;
    
   
    document.getElementById('containerAgrupado').style.display = 'none';
    

    try {
        const [resFunc, resMin] = await Promise.all([
            fetch(url),
            fetch(`${API_URL}/salarios-minimos`)
        ]);

        if (!resFunc.ok || !resMin.ok) throw new Error("Erro na API");

        const funcionarios = await resFunc.json();
        const salariosMinMap = await resMin.json();

        const corpo = document.getElementById('corpoTabela');
        corpo.innerHTML = '';

        funcionarios.forEach(f => {
            const valorMinimo = salariosMinMap[f.nome] || "0.00";

            corpo.innerHTML += `
                <tr>
                    <td>${f.nome}</td>
                    <td>${formatData(f.dataNascimento)}</td>
                    <td>${formatMoeda(f.salario)}</td>
                    <td>${f.funcao}</td>
                    <td>${valorMinimo} salários</td>
                    <td>
                        <button class="btn-danger" onclick="remover('${f.nome}')">Excluir</button>
                    </td>
                </tr>`;
        });
        
        atualizarEstatisticas();
    } catch (e) {
        console.error("Erro ao carregar dados:", e);
    }
}

async function buscarAgrupados() {
    
    document.getElementById('tabelaPrincipal').style.display = 'none';
    document.getElementById('tituloTabela').innerText = "Funcionários Agrupados por Função (3.5/3.6)";
    
    
    const container = document.getElementById('containerAgrupado');
    const lista = document.getElementById('listaAgrupada');
    container.style.display = 'block';
    
    try {
        
        const res = await fetch(`${API_URL}/agrupados-por-funcao`);
        if (!res.ok) throw new Error("Erro ao buscar dados agrupados");
        
        const agrupados = await res.json();
        
        
        lista.innerHTML = '';
        
        for (let funcao in agrupados) {
            
            const nomes = agrupados[funcao].map(f => f.nome).join(', ');
            
            lista.innerHTML += `
                <div class="card-agrupado" style="background: white; padding: 15px; margin-bottom: 10px; border-radius: 8px; border-left: 5px solid #3498db; shadow: 0 2px 4px rgba(0,0,0,0.1);">
                    <h4 style="margin: 0 0 5px 0; color: #2c3e50;">${funcao}</h4>
                    <p style="margin: 0; color: #7f8c8d;">${nomes}</p>
                </div>`;
        }
    } catch (e) {
        console.error("Erro no agrupamento:", e);
        lista.innerHTML = `<p style="color: red;">Erro ao carregar agrupamento.</p>`;
    }
}


async function remover(nome) {
    await fetch(`${API_URL}/${nome}`, { method: 'DELETE' });
    buscarTodos();
}


async function aplicarAumento() {
    await fetch(`${API_URL}/aumento`, { method: 'PUT' });
    buscarTodos();
}


async function atualizarEstatisticas() {
    const [resTotal, resVelho] = await Promise.all([
        fetch(`${API_URL}/total-salarios`),
        fetch(`${API_URL}/mais-velho`)
    ]);
    
    const total = await resTotal.json();
    const velho = await resVelho.json();
    
    document.getElementById('totalSalarios').innerText = formatMoeda(total);
    if(velho) {
        const idade = new Date().getFullYear() - new Date(velho.dataNascimento).getFullYear();
        document.getElementById('maisVelhoInfo').innerText = `${velho.nome} (${idade} anos)`;
    }
}

window.onload = buscarTodos;