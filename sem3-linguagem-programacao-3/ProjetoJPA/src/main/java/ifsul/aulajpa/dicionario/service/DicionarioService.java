package ifsul.aulajpa.dicionario.service;

import ifsul.aulajpa.dicionario.dao.CategoriaDao;
import ifsul.aulajpa.dicionario.dao.IdiomaDao;
import ifsul.aulajpa.dicionario.dao.PalavraDao;
import ifsul.aulajpa.dicionario.entity.Categoria;
import ifsul.aulajpa.dicionario.entity.Idioma;
import ifsul.aulajpa.dicionario.entity.Palavra;
import ifsul.aulajpa.dicionario.util.JPAUtil;

import javax.persistence.EntityManager;
import java.util.List;

public class DicionarioService {

    // Criar Idioma
    public Idioma criarIdioma(String nome) {
        EntityManager em = JPAUtil.createEntityManager();
        try {
            em.getTransaction().begin();
            IdiomaDao dao = new IdiomaDao();
            dao.setEm(em);
            Idioma idioma = new Idioma(nome);
            dao.salvar(idioma);
            em.getTransaction().commit();
            return idioma;
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    // Criar Categoria
    public Categoria criarCategoria(String nome) {
        EntityManager em = JPAUtil.createEntityManager();
        try {
            em.getTransaction().begin();
            CategoriaDao dao = new CategoriaDao();
            dao.setEm(em);
            Categoria categoria = new Categoria(nome);
            dao.salvar(categoria);
            em.getTransaction().commit();
            return categoria;
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    // Criar Palavra
    public Palavra criarPalavra(String texto, Long idiomaId) {
        EntityManager em = JPAUtil.createEntityManager();
        try {
            em.getTransaction().begin();
            PalavraDao dao = new PalavraDao();
            dao.setEm(em);
            IdiomaDao idiomaDao = new IdiomaDao();
            idiomaDao.setEm(em);

            Idioma idioma = idiomaDao.buscarPorId(idiomaId);
            Palavra palavra = new Palavra(texto);
            palavra.setIdioma(idioma);
            dao.salvar(palavra);
            em.getTransaction().commit();
            return palavra;
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    // Associar Categoria a Palavra
    public void associarCategoria(Long palavraId, Long categoriaId) {
        EntityManager em = JPAUtil.createEntityManager();
        try {
            em.getTransaction().begin();
            PalavraDao dao = new PalavraDao();
            dao.setEm(em);
            dao.adicionarCategoria(palavraId, categoriaId);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    // Desassociar Categoria de Palavra
    public void desassociarCategoria(Long palavraId, Long categoriaId) {
        EntityManager em = JPAUtil.createEntityManager();
        try {
            em.getTransaction().begin();
            PalavraDao dao = new PalavraDao();
            dao.setEm(em);
            dao.removerCategoria(palavraId, categoriaId);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    // Metodos de Listagem:
    public List<Idioma> listarIdiomas() {
        EntityManager em = JPAUtil.createEntityManager();
        try {
            IdiomaDao dao = new IdiomaDao();
            dao.setEm(em);
            return dao.listarTodos();
        } finally {
            em.close();
        }
    }

    public List<Categoria> listarCategorias() {
        EntityManager em = JPAUtil.createEntityManager();
        try {
            CategoriaDao dao = new CategoriaDao();
            dao.setEm(em);
            return dao.listarTodas();
        } finally {
            em.close();
        }
    }

    public List<Palavra> listarPalavras() {
        EntityManager em = JPAUtil.createEntityManager();
        try {
            PalavraDao dao = new PalavraDao();
            dao.setEm(em);
            return dao.listarTodas();
        } finally {
            em.close();
        }
    }

    public List<Palavra> listarPalavrasPorIdioma(Long idiomaId) {
        EntityManager em = JPAUtil.createEntityManager();
        try {
            PalavraDao dao = new PalavraDao();
            dao.setEm(em);
            return dao.listarPorIdioma(idiomaId);
        } finally {
            em.close();
        }
    }
}
