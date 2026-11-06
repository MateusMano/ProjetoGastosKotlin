# ESTRAGOS — Atividade MVVM Meus Gastos

## Estrago 1 — Instância do ViewModel

**O que foi alterado:** Substituí a função `viewModel()` pela criação direta de `GastosViewModel()`.

**Resultado observado:** Adicionei dois gastos e, depois de digitar uma letra no campo de descrição, os dois gastos continuaram aparecendo normalmente na tela.

**Conclusão:** Mesmo que os gastos tenham permanecido visíveis durante o teste, criar o ViewModel diretamente não é a abordagem recomendada, pois ele deixa de ser gerenciado pelo ciclo de vida do Android. Utilizar `viewModel()` é mais adequado para manter a instância corretamente.

## Estrago 2 — Lista não observável pelo Compose

**O que foi alterado:** Substituí `mutableStateOf(repository.listar())` por uma lista comum utilizando `mutableListOf`/`repository.listar()`.

**Resultado observado:** Os gastos apareceram normalmente na tela, inclusive depois de digitar uma letra no campo de descrição.

**Conclusão:** O resultado aconteceu porque outras alterações na tela provocaram recomposições, permitindo que a lista fosse exibida novamente. Entretanto, uma lista comum não é observável pelo Compose, portanto suas alterações não garantem uma atualização automática da interface.

## Estrago 3 — Total armazenado separadamente

**O que foi alterado:** Substituí o cálculo automático do total por uma variável `mutableStateOf`, atualizada somente quando um gasto era adicionado.

**Resultado observado:** Depois de adicionar três gastos, o total correspondia à soma dos três valores. Porém, ao remover um dos gastos, o total permaneceu igual, considerando os três valores.

**Conclusão:** Armazenar o total separadamente pode causar inconsistências, pois ele precisa ser atualizado em todas as operações que modificam a lista. O cálculo automático evita esse problema.

## Estrago 4 — Dependência de uma implementação concreta

**O que foi alterado:** O ViewModel passou a depender diretamente de `GastoRepositoryMemoria`, em vez de utilizar a interface `GastoRepository`.

**Resultado observado:** Ao tentar substituir `GastoRepositoryMemoria` por `GastoRepositoryDemo`, o Android Studio apresentou o seguinte erro:

`Type mismatch: inferred type is 'GastoRepositoryDemo', but 'GastoRepositoryMemoria' was expected.`

**Conclusão:** Quando o ViewModel depende diretamente de uma implementação específica, fica mais difícil substituir a fonte de dados. Utilizar uma interface permite trocar as implementações sem precisar modificar o funcionamento do ViewModel.

## Estrago 5 — Validação na interface

**O que foi alterado:** Removi a validação do ViewModel e coloquei a verificação da descrição diretamente no botão de adicionar gasto.

**Resultado observado:**
- Primeiro teste: o cadastro com descrição e valor preenchidos funcionou normalmente.
- Segundo teste: o cadastro com a descrição vazia foi bloqueado.
- Terceiro teste: o cadastro com descrição preenchida, mas sem valor, foi realizado normalmente, pois o campo vazio foi convertido para `0.0`.

**Conclusão:** Colocar a validação somente na interface não é uma boa prática. O ViewModel também deve validar os dados, pois outras telas ou componentes podem chamar suas funções. Dessa forma, as regras são respeitadas independentemente de onde o cadastro é realizado.
