package ifsul.aulajpa.dicionario.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "idioma")
public class Idioma implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", length = 100, nullable = false)
    private String nome;

    // OneToMany com CASCADE -> persistir palavras automaticamente
    @OneToMany(mappedBy = "idioma", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Palavra> palavras = new ArrayList<>();

    // Construtores
    public Idioma() {}
    public Idioma(String nome) {
        this.nome = nome;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public List<Palavra> getPalavras() { return palavras; }

    // Metodos
    public void adicionarPalavra(Palavra palavra) {
        palavra.setIdioma(this);        // Define FK
        this.palavras.add(palavra);      // Adiciona na lista
    }

    // toString
    @Override
    public String toString() {
        return "Idioma{" +
               "id=" + id +
               ", nome='" + nome + '\'' +
               ", palavras=" + palavras.size() +
               '}';
    }
}
