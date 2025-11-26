package ifsul.aulajpa.dicionario.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "categoria")
public class Categoria implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", length = 100, nullable = false, unique = true)
    private String nome;

    // ManyToMany
    @ManyToMany(mappedBy = "categorias")
    private Set<Palavra> palavras = new HashSet<>();

    // Construtores
    public Categoria() {}
    public Categoria(String nome) {
        this.nome = nome;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public Set<Palavra> getPalavras() { return palavras; }

    @Override
    public String toString() {
        return "Categoria{" +
               "id=" + id +
               ", nome='" + nome + '\'' +
               ", palavras=" + palavras.size() +
               '}';
    }
}
