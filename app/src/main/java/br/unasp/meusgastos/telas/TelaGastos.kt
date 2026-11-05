package br.unasp.meusgastos.telas

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.unasp.meusgastos.model.Categoria
import br.unasp.meusgastos.viewmodel.GastosViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaGastos(viewModel: GastosViewModel) {
    var descricao by remember { mutableStateOf("") }
    var valor by remember { mutableStateOf("") }
    var categoria by remember { mutableStateOf(Categoria.OUTROS) }

    val gastos = viewModel.gastosFiltrados

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Meus Gastos", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = descricao,
            onValueChange = { descricao = it },
            label = { Text("Descrição") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = valor,
            onValueChange = { valor = it },
            label = { Text("Valor") },
            modifier = Modifier.fillMaxWidth()
        )

        var expandido by remember { mutableStateOf(false) }

        ExposedDropdownMenuBox(
            expanded = expandido,
            onExpandedChange = { expandido = !expandido }
        ) {
            OutlinedTextField(
                value = categoria.rotulo,
                onValueChange = {},
                readOnly = true,
                label = { Text("Categoria") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandido)
                },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
            )

            ExposedDropdownMenu(
                expanded = expandido,
                onDismissRequest = { expandido = false }
            ) {
                Categoria.entries.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(item.rotulo) },
                        onClick = {
                            categoria = item
                            expandido = false
                        }
                    )
                }
            }
        }

        Button(
            onClick = {
                viewModel.adicionarGasto(
                    descricao,
                    valor.toDoubleOrNull() ?: 0.0,
                    categoria
                )
                descricao = ""
                valor = ""
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Adicionar gasto")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            "Total: R$ %.2f".format(viewModel.total),
            style = MaterialTheme.typography.titleLarge
        )

        LazyColumn {
            items(gastos, key = { it.id }) { gasto ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(gasto.descricao)
                            Text(gasto.categoria.rotulo)
                            Text("R$ %.2f".format(gasto.valor))
                        }

                        TextButton(
                            onClick = { viewModel.removerGasto(gasto.id) }
                        ) {
                            Text("Remover")
                        }
                    }
                }
            }
        }
    }
}