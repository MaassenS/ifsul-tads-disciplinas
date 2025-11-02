package ifsul.aulajpa.dicionario.dao;

import ifsul.aulajpa.dicionario.entity.Categoria;
import ifsul.aulajpa.dicionario.entity.Idioma;
import ifsul.aulajpa.dicionario.entity.Palavra;
import java.util.List;

public class PalavraDao extends BaseEm {

    public void salvar(Palavra palavra) {
        em.persist(palavra);
    }

    public Palavra buscarPorId(Long id) {
        return em.find(Palavra.class, id);
    }

    public List<Palavra> listarPorIdioma(Long idiomaId) {
        return em.createQuery(
                         "SELECT p FROM Palavra p WHERE p.idioma.id = :idiomaId",
                         Palavra.class)
                 .setParameter("idiomaId", idiomaId)
                 .getResultList();
    }

    public List<Palavra> listarTodas() {
        return em.createQuery("SELECT p FROM Palavra p", Palavra.class)
                 .getResultList();
    }

    public void adicionarCategoria(Long palavraId, Long categoriaId) {
        Palavra palavra = em.find(Palavra.class, palavraId);
        Categoria categoria = em.find(Categoria.class, categoriaId);
        palavra.getCategorias().add(categoria);
        em.merge(palavra);
    }

    public void removerCategoria(Long palavraId, Long categoriaId) {
        Palavra palavra = em.find(Palavra.class, palavraId);
        Categoria categoria = em.find(Categoria.class, categoriaId);
        palavra.getCategorias().remove(categoria);
        em.merge(palavra);
    }
}
