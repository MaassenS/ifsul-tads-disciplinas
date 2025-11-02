package ifsul.aulajpa.dicionario.util;

import javax.persistence.*;

public class JPAUtil {

    private static final EntityManagerFactory EMF =
            Persistence.createEntityManagerFactory("PU_aulajpa");

    // Retorna a EntityManagerFactory única da aplicação
    public static EntityManagerFactory getEmf() {
        return EMF;
    }

    // Cria EntityManager para operações
    public static EntityManager createEntityManager() {
        return EMF.createEntityManager();
    }

    // Fecha a EMF ao desligar a aplicação
    public static void shutdown() {
        EMF.close();
    }
}
