package ifsul.aulajpa.dicionario.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "palavra")
public class Palavra implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "texto", length = 200, nullable = false)
    private String texto;

    // ManyToOne - Lado DONO do relacionamento com Idioma
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idioma_id", nullable = false)
    private Idioma idioma;

    // ManyToMany - Lado DONO do relacionamento com Categoria
    @ManyToMany
    @JoinTable(
            name = "palavra_categoria",
            joinColumns = @JoinColumn(name = "palavra_id"),
            inverseJoinColumns = @JoinColumn(name = "categoria_id")
    )
    private Set<Categoria> categorias = new HashSet<>();

    // Construtores
    public Palavra() {}
    public Palavra(String texto) {
        this.texto = texto;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public String getTexto() { return texto; }
    public void setTexto(String texto) { this.texto = texto; }
    public Idioma getIdioma() { return idioma; }
    public void setIdioma(Idioma idioma) { this.idioma = idioma; }
    public Set<Categoria> getCategorias() { return categorias; }

    // toString
    @Override
    public String toString() {
        return "Palavra{" +
               "id=" + id +
               ", texto='" + texto + '\'' +
               '}';
    }
}
