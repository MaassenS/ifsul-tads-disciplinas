package ifsul.aulajpa.dicionario.dao;

import ifsul.aulajpa.dicionario.entity.Idioma;
import java.util.List;

public class IdiomaDao extends BaseEm {

    public void salvar(Idioma idioma) {
        em.persist(idioma);
    }

    public Idioma buscarPorId(Long id) {
        return em.find(Idioma.class, id);
    }

    public List<Idioma> listarTodos() {
        return em.createQuery("SELECT i FROM Idioma i", Idioma.class)
                 .getResultList();
    }

    public void remover(Idioma idioma) {
        em.remove(em.contains(idioma) ? idioma : em.merge(idioma));
    }
}
