package util;

import javax.swing.JOptionPane;

/**
 * Classe responsável por criar as tabelas mapeadas no banco de dados
 *
 * @author João Victor
 * @since 17/03/2021
 * @version 1.0
 */
public class GeraTabela {

    public static void main(String[] args) {
        HibernateUtil.getSessionFactory().openSession();
        JOptionPane.showMessageDialog(null, "Tabelas criadas");
        System.exit(0);
    }

}
