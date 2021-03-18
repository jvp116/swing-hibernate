package util;

/**
 * Classe responsável pro armazenar os métodos de validação de dados
 *
 * @author João Victor
 * @since 17/03/2021
 * @version 1.0
 */
public class Valida {

    /*
     * método para verificar se o campo é diferente de vazio ou nulo
     */
    public static boolean isEmptyOrNull(String args) {
        return (args.trim().equals("") || args == null);
    }

    /*
     * método para verificar se o campo é um inteiro
     */
    public static boolean isInteger(String args) {
        try {
            Integer.parseInt(args);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /*
     * método para verificar String válida
     */
    public static boolean isStringValida(String args) {
        if (args.matches(".*\\d.*")) {
            return true;
        } else {
            return false;
        }
    }
}
