package ifsul.aulajpa.dicionario.dao;

import javax.persistence.EntityManager;

public abstract class BaseEm {
    protected EntityManager em;

    public void setEm(EntityManager em) {
        this.em = em;
    }
}
