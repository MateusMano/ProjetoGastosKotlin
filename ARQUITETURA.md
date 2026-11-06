# ARQUITETURA

## 1. Se amanhã o app precisar buscar os gastos de uma API em vez da memória, quais arquivos precisam mudar? E quais com certeza NÃO mudam?

Acredito que seria necessário criar uma nova implementação do repositório pra buscar os dados pela API, além de ajustar a maneira de fornecer essa implementação no ViewModel. A interface GastoRepository pode continuar, desde que a API forneça as operações de adicionar, listar e remover gastos, e a tela e as regras podem continuar, pois continuam usando os mesmos métodos da interface

## 2. Por que o total é calculado e não guardado? Descreva um bug concreto que apareceria se ele fosse uma variável atualizada manualmente.

O total é calculado, pois ele depende dos gastos que estão sendo exibidos e do filtro selecionado, assim o valor sempre atualiza de acordo com a lista. Se fosse guardado em uma variável e atualizado manualmente, se algum gasto fosse removido, ainda continuaria exibindo o valor removido, por exemplo: Se adicionarmos 10, 20 e 30 reais, o total mostrará 60 reais, se removermos 30 reais, ainda continuaria exibindo 60 reais. Isso faria com que o resumo exibisse uma informação incorreta.

## 3. O FormularioGasto não recebe o ViewModel. O que isso te permitiu fazer que não seria possível se ele recebesse?

Bom, isso permitiria que o formulário se tornasse um componente independente, ou seja, recebe os valores necessários e as funções que deve executar, dessa forma, ele pode ser reutilizado em outras telas e também ter um Preview com dados de exemplo, inclusive mostrando mensagem de erro, sem precisar criar ou acessar um ViewModel
