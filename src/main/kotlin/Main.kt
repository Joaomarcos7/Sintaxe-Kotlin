class Livro(var titulo: String, var preco: Double) {
    override fun toString(): String {
        return "Livro: Titulo = $titulo, Preco = $preco"
    }
}

fun menu() {
    println("1 - Cadastrar livro")
    println("2 - Excluir livro")
    println("3 - Buscar livro")
    println("4 - Editar livro")
    println("5 - Listar livros")
    println("6 - Listar livros que começam com letra escolhida")
    println("7 - Listar livros com preço abaixo do informado")
    println("8 - Sair")
}

fun inputTitulo(): String {
    print("Digite o título do livro: ")
    return readLine() ?: ""
}

fun inputPreco(): Double {
    var preco: Double
    do {
        print("Digite o preço do livro: ")
        preco = readLine()?.toDoubleOrNull() ?: 0.0
        if (preco < 0) {
            println("Preço não pode ser negativo. Tente novamente.")
        }
    } while (preco < 0)
    return preco
}

fun cadastrarLivro(repositorio: MutableList<Livro>) {
    val titulo = inputTitulo()
    val preco = inputPreco()

    repositorio.add(Livro(titulo, preco))
    println("\nCadastrado com sucesso!\n")
}

fun excluirLivro(repositorio: MutableList<Livro>) {
    val livro = buscarNome(repositorio)
    if (livro != null) {
        if (repositorio.remove(livro)) {
            println("Livro removido com sucesso!")
        } else {
            println("Falha ao remover o livro.")
        }
    } else {
        println("Livro não encontrado!")
    }
}

fun buscarNome(repositorio: MutableList<Livro>): Livro? {
    val titulo = inputTitulo()
    return repositorio.find { it.titulo == titulo }
}

fun editarLivro(repositorio: MutableList<Livro>) {
    val livro = buscarNome(repositorio)
    if (livro != null) {
        println("Qual informação você deseja editar?")
        println("1 - Título")
        println("2 - Preço")
        print("Escolha a opção: ")
        when (readLine()?.toIntOrNull() ?: 0) {
            1 -> {
                print("Digite o novo título: ")
                livro.titulo = readLine() ?: ""
                println("Título editado com sucesso!")
            }
            2 -> {
                livro.preco = inputPreco()
                println("Preço editado com sucesso!")
            }
            else -> println("Opção inválida.")
        }
    } else {
        println("Livro não encontrado!")
    }
}

fun listar(repositorio: MutableList<Livro>) {
    println("Lista de livros:")
    for (livro in repositorio) {
        println(livro)
    }
}

fun listarComLetraInicial(repositorio: MutableList<Livro>) {
    print("Informe a letra: ")
    var letra = readLine() ?: ""

    while (letra.length > 1) {
        print("Informe apenas uma letra: ")
        letra = readLine() ?: ""
    }

    if (letra.isNotEmpty()) {
        val livros = repositorio.filter { it.titulo.startsWith(letra, ignoreCase = true) }
        if (livros.isNotEmpty()) {
            println("Livros que começam com a letra '$letra':")
            livros.forEach { println(it) }
        } else {
            println("Nenhum livro encontrado com a letra '$letra'.")
        }
    } else {
        println("É necessário informar pelo menos um caracter para esta função executar!")
    }
}

fun listarComPrecoAbaixo(repositorio: MutableList<Livro>) {
    val preco = inputPreco()
    val livros = repositorio.filter { it.preco < preco }
    if (livros.isNotEmpty()) {
        println("Livros com preço abaixo de $preco:")
        livros.forEach { println(it) }
    } else {
        println("Nenhum livro encontrado com preço abaixo de $preco.")
    }
}

fun main() {
    val repositorioLivros = mutableListOf(
        Livro("Livro dos Livros", 999999.99),
        Livro("Turma da Monica", 4.99),
        Livro("Kotlin for Dummies", 29.99),
        Livro("A", 59.99)
    )

    var opcao = 0
    while (opcao != 8) {
        menu()
        print("Digite a opção: ")
        opcao = readLine()?.toIntOrNull() ?: 8

        when (opcao) {
            1 -> cadastrarLivro(repositorioLivros)
            2 -> excluirLivro(repositorioLivros)
            3 -> {
                val livro = buscarNome(repositorioLivros)
                println(livro)
            }
            4 -> editarLivro(repositorioLivros)
            5 -> listar(repositorioLivros)
            6 -> listarComLetraInicial(repositorioLivros)
            7 -> listarComPrecoAbaixo(repositorioLivros)
            8 -> println("Até a próxima :)")
            else -> println("Opção inválida! Por favor, digite um número de 1 a 8.")
        }
        Thread.sleep(3000)
    }
}
