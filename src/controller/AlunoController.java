package controller;

import dao.AlunoDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.Aluno;
import util.Mensagem;
import util.Titulo;
import util.Util;
import util.Valida;
import view.AlunoView;

/**
 * Classe para armazenar so processos de controle de tela e banco de dados do
 * Aluno
 *
 * @author João Victor
 * @since 16/03/2021
 * @version 1.0
 */
public class AlunoController {

    // atributo para manipular a tela de cadastro
    private AlunoView tela;

    // lista de alunos para preencher tabela, alterar e excluir
    private ArrayList<Aluno> listaAlunos;

    // objeto Aluno para incluir ou alterar
    private Aluno aluno;

    // flag para controlar a opção de incluir o alterar
    private boolean alterar = false;

    // construtor vazio
    public AlunoController() {
    }

    // construtor para valorizar o objeto de tela
    public AlunoController(AlunoView tela) {
        this.tela = tela;
    }

    /*
     * método para acessar a classe DAO e inserir um registro na tabela aluno
     */
    public void inserir(Aluno aluno) {

        // objeto DAO para manipular um registro
        AlunoDAO dao = new AlunoDAO();

        try {
            // inserindo um registro na tabela
            dao.salvar(aluno);

            // mensagem de sucesso
            JOptionPane.showMessageDialog(null, Mensagem.inserir_ok, Titulo.inserir_aluno, 1);

        } catch (Exception ex) {
            Logger.getLogger(AlunoController.class.getName()).log(Level.SEVERE, null, ex);

            // mensagem de erro
            JOptionPane.showMessageDialog(null, Mensagem.inserir_n_ok, Titulo.inserir_aluno, 0);
        }
    }// fim do método inserir

    /*
     * método para acessar a classe DAO e alterar um registro na tabela aluno
     */
    public void alterar(Aluno aluno) {
        // objeto DAO para manipular um registro
        AlunoDAO dao = new AlunoDAO();

        try {
            // alterando um registro na tabela
            dao.salvar(aluno);

            // mensagem de sucesso
            JOptionPane.showMessageDialog(null, Mensagem.alterar_ok, Titulo.alterar_aluno, 1);

        } catch (Exception ex) {
            Logger.getLogger(AlunoController.class.getName()).log(Level.SEVERE, null, ex);

            // mensagem de erro
            JOptionPane.showMessageDialog(null, Mensagem.alterar_n_ok, Titulo.alterar_aluno, 0);
        }
    }// fim do método alterar

    /*
     * método para acessar a classe DAO e excluir um registro na tabela aluno
     */
    public void excluir(Aluno aluno) {
        // objeto DAO para manipular um registro
        AlunoDAO dao = new AlunoDAO();

        try {
            // excluindo um registro na tabela
            dao.excluir(aluno);

            // mensagem de sucesso
            JOptionPane.showMessageDialog(null, Mensagem.excluir_ok, Titulo.excluir_aluno, 1);

        } catch (Exception ex) {
            Logger.getLogger(AlunoController.class.getName()).log(Level.SEVERE, null, ex);

            // mensagem de erro
            JOptionPane.showMessageDialog(null, Mensagem.excluir_n_ok, Titulo.excluir_aluno, 0);
        }
    }// fim do método excluir

    /*
     * método para acessar a classe DAO e consultar os registros na tabela aluno
     */
    public ArrayList<Aluno> buscarTodos() {
        // lista auxiliar para retornar no método
        ArrayList<Aluno> retorno = null;

        try {
            // acessando a classe DAO  e efetuando a consulta
            retorno = new AlunoDAO().buscarTodos();

        } catch (Exception ex) {
            Logger.getLogger(AlunoController.class.getName()).log(Level.SEVERE, null, ex);

            // mensagem de erro
            JOptionPane.showMessageDialog(null, Mensagem.consultar_n_ok, Titulo.consulta_aluno, 0);
        }
        return retorno;
    }// fim do método buscarTodos

    /*
     * método para controlar a ação do botão NOVO
     */
    public void botaoNovo() {
        // bloqueando botões de opção
        tela.getBtNovo().setEnabled(false);
        tela.getBtAlterar().setEnabled(false);
        tela.getBtExcluir().setEnabled(false);
        tela.getBtSair().setEnabled(false);
        // habilitando os textFileds
        tela.getTfNome().setEditable(true);
        tela.getTfIdade().setEditable(true);
        tela.getTfCidade().setEditable(true);
        // habilitando os botões de controle
        tela.getBtSalvar().setEnabled(true);
        tela.getBtCancelar().setEnabled(true);
        // alterando a flag de controle
        alterar = false;
        // instanciando um novo objeto de Aluno
        aluno = new Aluno();
    }// fim do método botaoNovo

    /*
     * método para controlar a ação do botão ALTERAR
     */
    public void botaoAlterar() {
        // alterando a flag de controle
        alterar = true;

        // verificando s eo usuário selecionou algum registro
        if (tela.getTabela().getSelectedRow() < 0) {
            // exibindo a mensagem de erro
            JOptionPane.showMessageDialog(null, Mensagem.selecione_aluno, Titulo.alterar_aluno, 0);
        } else {
            // inicializando o objeto Aluno com o aluno da tabela
            aluno = listaAlunos.get(tela.getTabela().getSelectedRow());
            // carregando a tela com os dados do aluno selecionado
            carregarTela(aluno);
            // bloqueando a tela
            bloqueioAlterar();
        }

    }// fim do método botaoAlterar

    /*
     * método para controlar a ação do botão EXLUIR
     */
    public void botaoExcluir() {
// verificando s eo usuário selecionou algum registro
        if (tela.getTabela().getSelectedRow() < 0) {
            // exibindo a mensagem de erro
            JOptionPane.showMessageDialog(null, Mensagem.selecione_aluno, Titulo.alterar_aluno, 0);
        } else {
            // inicializando o objeto Aluno com o aluno da tabela
            aluno = listaAlunos.get(tela.getTabela().getSelectedRow());

            // verificando a exclusão do aluno
            int opcao = JOptionPane.showConfirmDialog(null, Mensagem.excluir_aluno, Titulo.excluir_aluno, JOptionPane.YES_OPTION, JOptionPane.CANCEL_OPTION);
            if (opcao == JOptionPane.YES_OPTION) {
                // excluindo registro
                excluir(aluno);

                // carregando a tabela
                carregarTabela();
            }
        }
    }// fim do método botaoExcluir

    /*
     * método para controlar a ação do botão SAIR
     */
    public void botaoSair() throws SQLException {
        tela.dispose();
    }// fim do método botaoSair

    /*
     * método para controlar a ação do botão SALVAR
     */
    public void botaoSalvar() {
        // verificando a opção de incluir ou alterar
        if (alterar) {
            // procedimentos de alteração
            if (validarAlterar()) {
                // alterando o registro na tabela
                alterar(getAlunoAlterar());
                // limpando a tela e bloqueando os campos
                botaoCancelar();
                //carregando a tabela
                carregarTabela();
            }
        } else {
            // procedimentos de inclusão
            if (validarIncluir()) {
                //inserindo o registro na tabela
                inserir(getAlunoInserir());
                //limpando a tela e bloqueando os campos
                botaoCancelar();
                //carregando tabela
                carregarTabela();
            }
        }
    }// fim do método botaoSalvar

    /*
     * método para controlar a ação do botão CANCELAR
     */
    public void botaoCancelar() {
        // desbloqueando botões de opção
        tela.getBtNovo().setEnabled(true);
        tela.getBtAlterar().setEnabled(true);
        tela.getBtExcluir().setEnabled(true);
        tela.getBtSair().setEnabled(true);
        // limpando os textFields
        limparTela();
        // bloqueando a tela
        bloqueioInicial();
    }// fim do método botaoCancelar

    /*
     * método para bloqueio inicial dos objetos
     */
    public void bloqueioInicial() {

        // desabilitadno os textFileds
        tela.getTfNome().setEditable(false);
        tela.getTfIdade().setEditable(false);
        tela.getTfCidade().setEditable(false);
        // desabilitando os botões
        tela.getBtSalvar().setEnabled(false);
        tela.getBtCancelar().setEnabled(false);
    }// fim do método bloqueioInicial

    /*
     * método para limpar os campos da tela
     */
    private void limparTela() {
        tela.getTfNome().setText(null);
        tela.getTfIdade().setText(null);
        tela.getTfCidade().setText(null);
    }//fim do método limpartELA

    /*
     * método para carregar a tabela de Alunos cadastrados
     */
    public void carregarTabela() {
        // capturando o modelo da tabela atual
        DefaultTableModel modelo = (DefaultTableModel) tela.getTabela().getModel();
        // inicializando as linhas da tabela
        modelo.setRowCount(0);
        // carregando a lista de alunos para preencher a tabela
        listaAlunos = buscarTodos();
        // laço de repetição para preencher as linhas da tabela
        for (Aluno aluno : listaAlunos) {
            modelo.addRow(new String[]{aluno.getNome(), aluno.getIdade() + "", aluno.getCidade()});
        }
    }// fim do método carregarTabela

    /*
     * método para validar os campos da inclusão
     */
    private boolean validarIncluir() {
        // verificando se o campo nome está válido
        if (Valida.isEmptyOrNull(tela.getTfNome().getText())) {
            JOptionPane.showMessageDialog(null, Mensagem.nome_n_ok, Titulo.inserir_aluno, 0);
            tela.getTfNome().grabFocus();
            return false;
        } else if (Valida.isStringValida(tela.getTfNome().getText())) {
            // informando ao usuário que o nome é inválido
            JOptionPane.showMessageDialog(null, Mensagem.nome_invalido, Titulo.inserir_aluno, 0);
            // colocando o foco no campo com erro
            tela.getTfNome().grabFocus();
            // retornar false para o método
            return false;
        }

        // verificando se o campo idade está preenchido
        if (!Valida.isInteger(tela.getTfIdade().getText())) {
            JOptionPane.showMessageDialog(null, Mensagem.idade_n_ok, Titulo.inserir_aluno, 0);
            tela.getTfIdade().grabFocus();
            return false;
        }

        // verificando se o campo cidade está preenchido
        if (Valida.isEmptyOrNull(tela.getTfCidade().getText())) {
            JOptionPane.showMessageDialog(null, Mensagem.cidade_n_ok, Titulo.inserir_aluno, 0);
            tela.getTfCidade().grabFocus();
            return false;
        } else if (Valida.isStringValida(tela.getTfCidade().getText())) {
            // informando ao usuário que o cidade é inválido
            JOptionPane.showMessageDialog(null, Mensagem.cidade_invalida, Titulo.inserir_aluno, 0);
            // colocando o foco no campo com erro
            tela.getTfCidade().grabFocus();
            // retornar false para o método
            return false;
        }

        return true;
    }// fim do método validarIncluir

    /*
     * método para validar os campos da alteração
     */
    private boolean validarAlterar() {
        // verificando se o campo cidade está preenchido
        if (Valida.isEmptyOrNull(tela.getTfCidade().getText())) {
            JOptionPane.showMessageDialog(null, Mensagem.cidade_n_ok, Titulo.alterar_aluno, 0);
            tela.getTfCidade().grabFocus();
            return false;
        } else if (Valida.isStringValida(tela.getTfCidade().getText())) {
            // informando ao usuário que o cidade é inválido
            JOptionPane.showMessageDialog(null, Mensagem.cidade_invalida, Titulo.inserir_aluno, 0);
            // colocando o foco no campo com erro
            tela.getTfCidade().grabFocus();
            // retornar false para o método
            return false;
        }
        return true;
    }// fim do método validarAlterar

    /*
     * método para retornar um objeto Aluno para inclusão
     */
    private Aluno getAlunoInserir() {
        aluno.setNome(tela.getTfNome().getText());
        aluno.setIdade(Util.getInteger(tela.getTfIdade().getText()));
        aluno.setCidade(tela.getTfCidade().getText());
        return aluno;
    }// fim do método getAlunoInserir

    /*
     * método para carregar os campos da tela
     */
    private void carregarTela(Aluno aluno) {
        tela.getTfNome().setText(aluno.getNome());
        tela.getTfIdade().setText(aluno.getIdade() + "");
        tela.getTfCidade().setText(aluno.getCidade());
    }// fim do mpetodo carregarTela

    /*
     * método para
     */
    private void bloqueioAlterar() {
        //desabilitando os bt
        tela.getBtNovo().setEnabled(false);
        tela.getBtAlterar().setEnabled(false);
        tela.getBtExcluir().setEnabled(false);
        tela.getBtSair().setEnabled(false);

        //habilitando os tf
        tela.getTfCidade().setEditable(true);
        //habilitando os bt
        tela.getBtSalvar().setEnabled(true);
        tela.getBtCancelar().setEnabled(true);
        tela.getTfCidade().grabFocus();
    }// fim do método para retornar o objeto Aluno para alteração

    /*
     * método para retornar o objeto Aluno para alteração
     */
    private Aluno getAlunoAlterar() {
        aluno.setCidade(tela.getTfCidade().getText());
        return aluno;
    }
}// fim da classe
