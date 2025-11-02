package ifsul.aulajpa.dicionario.controller;

import ifsul.aulajpa.dicionario.entity.Categoria;
import ifsul.aulajpa.dicionario.entity.Idioma;
import ifsul.aulajpa.dicionario.entity.Palavra;
import ifsul.aulajpa.dicionario.service.DicionarioService;
import ifsul.aulajpa.dicionario.util.JPAUtil;

import java.util.List;

public class MainTeste {
    public static void main(String[] args) {
        DicionarioService service = new DicionarioService();

        try {
            System.out.println("=== CRIANDO IDIOMAS ===");
            Idioma pt = service.criarIdioma("Português");
            Idioma en = service.criarIdioma("Inglês");
            Idioma es = service.criarIdioma("Espanhol");
            System.out.println("✓ Idiomas criados: " + pt.getNome() + ", " + en.getNome() + ", " + es.getNome());

            System.out.println("\n=== CRIANDO CATEGORIAS ===");
            Categoria animais = service.criarCategoria("Animais");
            Categoria comida = service.criarCategoria("Comida");
            Categoria tecnologia = service.criarCategoria("Tecnologia");
            Categoria verbo = service.criarCategoria("Verbo");
            Categoria substantivo = service.criarCategoria("Substantivo");
            System.out.println("✓ " + service.listarCategorias().size() + " categorias criadas");

            System.out.println("\n=== CRIANDO PALAVRAS EM PORTUGUÊS ===");
            Palavra gato = service.criarPalavra("Gato", pt.getId());
            Palavra cachorro = service.criarPalavra("Cachorro", pt.getId());
            Palavra passaro = service.criarPalavra("Pássaro", pt.getId());
            Palavra maca = service.criarPalavra("Maçã", pt.getId());
            Palavra banana = service.criarPalavra("Banana", pt.getId());
            Palavra computador = service.criarPalavra("Computador", pt.getId());
            Palavra mouse = service.criarPalavra("Mouse", pt.getId());
            Palavra correr = service.criarPalavra("Correr", pt.getId());

            System.out.println("\n=== CRIANDO PALAVRAS (INGLÊS) ===");
            Palavra cat = service.criarPalavra("Cat", en.getId());
            Palavra dog = service.criarPalavra("Dog", en.getId());
            Palavra bird = service.criarPalavra("Bird", en.getId());
            Palavra apple = service.criarPalavra("Apple", en.getId());
            Palavra computer = service.criarPalavra("Computer", en.getId());
            Palavra run = service.criarPalavra("Run", en.getId());

            System.out.println("\n=== CRIANDO PALAVRAS (ESPANHOL) ===");
            Palavra gato_es = service.criarPalavra("Gato", es.getId());
            Palavra perro = service.criarPalavra("Perro", es.getId());
            Palavra manzana = service.criarPalavra("Manzana", es.getId());
            Palavra correr_es = service.criarPalavra("Correr", es.getId());

            System.out.println("Total de palavras criadas: " + service.listarPalavras().size());

            System.out.println("\n=== ASSOCIANDO CATEGORIAS ===");
            // Animais
            service.associarCategoria(gato.getId(), animais.getId());
            service.associarCategoria(gato.getId(), substantivo.getId());
            service.associarCategoria(cachorro.getId(), animais.getId());
            service.associarCategoria(cachorro.getId(), substantivo.getId());
            service.associarCategoria(passaro.getId(), animais.getId());
            service.associarCategoria(passaro.getId(), substantivo.getId());
            service.associarCategoria(cat.getId(), animais.getId());
            service.associarCategoria(dog.getId(), animais.getId());
            service.associarCategoria(bird.getId(), animais.getId());
            service.associarCategoria(gato_es.getId(), animais.getId());
            service.associarCategoria(perro.getId(), animais.getId());

            // Comida
            service.associarCategoria(maca.getId(), comida.getId());
            service.associarCategoria(maca.getId(), substantivo.getId());
            service.associarCategoria(banana.getId(), comida.getId());
            service.associarCategoria(banana.getId(), substantivo.getId());
            service.associarCategoria(apple.getId(), comida.getId());
            service.associarCategoria(manzana.getId(), comida.getId());

            // Tecnologia
            service.associarCategoria(computador.getId(), tecnologia.getId());
            service.associarCategoria(computador.getId(), substantivo.getId());
            service.associarCategoria(mouse.getId(), tecnologia.getId());
            service.associarCategoria(mouse.getId(), substantivo.getId());
            service.associarCategoria(computer.getId(), tecnologia.getId());

            // Verbos
            service.associarCategoria(correr.getId(), verbo.getId());
            service.associarCategoria(run.getId(), verbo.getId());
            service.associarCategoria(correr_es.getId(), verbo.getId());

            System.out.println("Categorias associadas");

            System.out.println("\n=== LISTANDO PALAVRAS POR IDIOMA ===");
            System.out.println("\n--- PTBR ---");
            List<Palavra> palavrasPt = service.listarPalavrasPorIdioma(pt.getId());
            palavrasPt.forEach(p -> {
                System.out.print("  - " + p.getTexto() + " | Categorias: ");
                p.getCategorias().forEach(c -> System.out.print(c.getNome() + " "));
                System.out.println();
            });

            System.out.println("\n--- ING ---");
            List<Palavra> palavrasEn = service.listarPalavrasPorIdioma(en.getId());
            palavrasEn.forEach(p -> {
                System.out.print("  - " + p.getTexto() + " | Categorias: ");
                p.getCategorias().forEach(c -> System.out.print(c.getNome() + " "));
                System.out.println();
            });

            System.out.println("\n--- ESP ---");
            List<Palavra> palavrasEs = service.listarPalavrasPorIdioma(es.getId());
            palavrasEs.forEach(p -> {
                System.out.print("  - " + p.getTexto() + " | Categorias: ");
                p.getCategorias().forEach(c -> System.out.print(c.getNome() + " "));
                System.out.println();
            });

            System.out.println("\n=== TESTE DE DESASSOCIAÇÃO ===");
            service.desassociarCategoria(gato.getId(), animais.getId());
            System.out.println("✓ Categoria 'Animais' removida de 'Gato'");

            System.out.println("\nPalavra 'Gato' após desassociação:");
            Palavra gatoAtualizado = service.listarPalavras().stream()
                                            .filter(p -> p.getId().equals(gato.getId()))
                                            .findFirst()
                                            .orElse(null);
            if (gatoAtualizado != null) {
                System.out.print("  - " + gatoAtualizado.getTexto() + " | Categorias: ");
                gatoAtualizado.getCategorias().forEach(c -> System.out.print(c.getNome() + " "));
                System.out.println();
            }

            System.out.println("\n=== ESTATÍSTICAS ===");
            System.out.println("Total de idiomas: " + service.listarIdiomas().size());
            System.out.println("Total de categorias: " + service.listarCategorias().size());
            System.out.println("Total de palavras: " + service.listarPalavras().size());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JPAUtil.shutdown();
        }
    }
}
