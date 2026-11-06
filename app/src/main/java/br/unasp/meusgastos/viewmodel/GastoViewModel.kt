package br.unasp.meusgastos.viewmodel
import br.unasp.meusgastos.model.GastoRepositoryDemo

import androidx.lifecycle.ViewModel
import br.unasp.meusgastos.model.Categoria
import br.unasp.meusgastos.model.Gasto
import br.unasp.meusgastos.model.GastoRepository
import br.unasp.meusgastos.model.GastoRepositoryMemoria
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

class GastosViewModel(
    private val repository: GastoRepositoryMemoria = GastoRepositoryDemo()
) : ViewModel() {

    var gastos by mutableStateOf(repository.listar())
        private set

    var filtroCategoria by mutableStateOf<Categoria?>(null)
        private set

    val gastosFiltrados: List<Gasto>
        get() = if (filtroCategoria == null) {
            gastos
        } else {
            gastos.filter { it.categoria == filtroCategoria }
        }

    val total: Double
        get() = gastosFiltrados.sumOf { it.valor }

    fun adicionarGasto(
        descricao: String,
        valor: Double,
        categoria: Categoria
    ) {
        if (descricao.isBlank() || valor <= 0) return

        repository.adicionar(descricao, valor, categoria)
        atualizarGastos()
    }

    fun removerGasto(id: Long) {
        repository.remover(id)
        atualizarGastos()
    }

    fun filtrarPorCategoria(categoria: Categoria?) {
        filtroCategoria = categoria
    }

    private fun atualizarGastos() {
        gastos = repository.listar()
    }
}