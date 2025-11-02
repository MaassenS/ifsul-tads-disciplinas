package ifsul.aulajpa.dicionario.dao;

import ifsul.aulajpa.dicionario.entity.Categoria;
import java.util.List;

public class CategoriaDao extends BaseEm {

    public void salvar(Categoria categoria) {
        em.persist(categoria);
    }

    public Categoria buscarPorId(Long id) {
        return em.find(Categoria.class, id);
    }

    public List<Categoria> listarTodas() {
        return em.createQuery("SELECT c FROM Categoria c", Categoria.class)
                 .getResultList();
    }
}
