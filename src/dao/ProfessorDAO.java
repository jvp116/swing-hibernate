package dao;

import java.util.ArrayList;
import model.Professor;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import util.HibernateUtil;

/**
 * Classe responsável por armazenar os métodos para acesso ao banco de dados
 *
 * @author João Victor
 * @since 17/03/2021
 * @version 1.0
 */
public class ProfessorDAO extends GenericDAO {

    /*
     * método para consultar os alunos gravados na tabela
     */
    public ArrayList<Professor> buscarTodos() throws Exception {
        ArrayList<Professor> retorno = new ArrayList<>();
        // classe auxiliar para armazenar a sessão com o banco de dados
        Session sessao = HibernateUtil.getSessionFactory().openSession();
        // classe auxiliar para consultar o banco de dados
        Criteria criteria = sessao.createCriteria(Professor.class);
        // adicionando a ordenação na pesquisa
        criteria.addOrder(Order.asc("idProfessor"));
        // valorizando o objeto de retorno do método com os registros da tabela
        retorno = (ArrayList<Professor>) criteria.list();
        // encerrando a conexão com o banco de dados
        sessao.close();
        // retornando a lista preenchida
        return retorno;
    }// fim do método buscarTodos

}
