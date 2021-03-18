package controller;

import dao.ProfessorDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.Professor;
import util.Mensagem;
import util.Titulo;
import util.Util;
import util.Valida;
import view.ProfessorView;

/**
 * Classe para armazenar so processos de controle de tela e banco de dados do
 * Professor
 *
 * @author João Victor
 * @since 16/03/2021
 * @version 1.0
 */
public class ProfessorController {

    // atributo para manipular a tela de cadastro
    private ProfessorView tela;

    // lista de alunos para preencher tabela, alterar e excluir
    private ArrayList<Professor> listaProfessores;

    // objeto Aluno para incluir ou alterar
    private Professor professor;

    // flag para controlar a opção de incluir o alterar
    private boolean alterar = false;

    // construtor vazio
    public ProfessorController() {
    }

    // construtor para valorizar o objeto de tela
    public ProfessorController(ProfessorView tela) {
        this.tela = tela;
    }

    /*
     * método para acessar a classe DAO e inserir um registro na tabela aluno
     */
    public void inserir(Professor professor) {

        // objeto DAO para manipular um registro
        ProfessorDAO dao = new ProfessorDAO();

        try {
            // inserindo um registro na tabela
            dao.salvar(professor);

            // mensagem de sucesso
            JOptionPane.showMessageDialog(null, Mensagem.inserir_ok_professor, Titulo.inserir_professor, 1);

        } catch (Exception ex) {
            Logger.getLogger(AlunoController.class.getName()).log(Level.SEVERE, null, ex);

            // mensagem de erro
            JOptionPane.showMessageDialog(null, Mensagem.inserir_n_ok_professor, Titulo.inserir_professor, 0);
        }
    }// fim do método inserir

    /*
     * método para acessar a classe DAO e alterar um registro na tabela aluno
     */
    public void alterar(Professor professor) {
        // objeto DAO para manipular um registro
        ProfessorDAO dao = new ProfessorDAO();

        try {
            // alterando um registro na tabela
            dao.salvar(professor);

            // mensagem de sucesso
            JOptionPane.showMessageDialog(null, Mensagem.alterar_ok_professor, Titulo.alterar_professor, 1);

        } catch (Exception ex) {
            Logger.getLogger(AlunoController.class.getName()).log(Level.SEVERE, null, ex);

            // mensagem de erro
            JOptionPane.showMessageDialog(null, Mensagem.alterar_n_ok_professor, Titulo.alterar_professor, 0);
        }
    }// fim do método alterar

    /*
     * método para acessar a classe DAO e excluir um registro na tabela aluno
     */
    public void excluir(Professor professor) {
        // objeto DAO para manipular um registro
        ProfessorDAO dao = new ProfessorDAO();

        try {
            // excluindo um registro na tabela
            dao.excluir(professor);

            // mensagem de sucesso
            JOptionPane.showMessageDialog(null, Mensagem.excluir_ok_professor, Titulo.excluir_professor, 1);

        } catch (Exception ex) {
            Logger.getLogger(AlunoController.class.getName()).log(Level.SEVERE, null, ex);

            // mensagem de erro
            JOptionPane.showMessageDialog(null, Mensagem.excluir_n_ok_professor, Titulo.excluir_professor, 0);
        }
    }// fim do método excluir

    /*
     * método para acessar a classe DAO e consultar os registros na tabela aluno
     */
    public ArrayList<Professor> buscarTodos() {
        // lista auxiliar para retornar no método
        ArrayList<Professor> retorno = null;

        try {
            // acessando a classe DAO  e efetuando a consulta
            retorno = new ProfessorDAO().buscarTodos();

        } catch (Exception ex) {
            Logger.getLogger(AlunoController.class.getName()).log(Level.SEVERE, null, ex);

            // mensagem de erro
            JOptionPane.showMessageDialog(null, Mensagem.consultar_n_ok_professor, Titulo.consulta_professor, 0);
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
        tela.getRbMasculino().setEnabled(true);
        tela.getRbFeminino().setEnabled(true);
        tela.getTfDataNascimento().setEditable(true);
        // habilitando os botões de controle
        tela.getBtSalvar().setEnabled(true);
        tela.getBtCancelar().setEnabled(true);
        // alterando a flag de controle
        alterar = false;
        // instanciando um novo objeto de Professor
        professor = new Professor();
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
            JOptionPane.showMessageDialog(null, Mensagem.selecione_professor, Titulo.alterar_professor, 0);
        } else {
            // inicializando o objeto Professor com o professor da tabela
            professor = listaProfessores.get(tela.getTabela().getSelectedRow());
            // carregando a tela com os dados do aluno selecionado
            carregarTela(professor);
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
            JOptionPane.showMessageDialog(null, Mensagem.selecione_professor, Titulo.alterar_professor, 0);
        } else {
            // inicializando o objeto Professor com o aluno da tabela
            professor = listaProfessores.get(tela.getTabela().getSelectedRow());

            // verificando a exclusão do Professor
            int opcao = JOptionPane.showConfirmDialog(null, Mensagem.excluir_professor, Titulo.excluir_professor, JOptionPane.YES_OPTION, JOptionPane.CANCEL_OPTION);
            if (opcao == JOptionPane.YES_OPTION) {
                // excluindo registro
                excluir(professor);

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
                alterar(getProfessorAlterar());
                // limpando a tela e bloqueando os campos
                botaoCancelar();
                //carregando a tabela
                carregarTabela();
            }
        } else {
            // procedimentos de inclusão
            if (validarIncluir()) {
                //inserindo o registro na tabela
                inserir(getProfessorInserir());
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
        tela.getRbMasculino().setEnabled(false);
        tela.getRbFeminino().setEnabled(false);
        tela.getTfDataNascimento().setEditable(false);
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
        tela.getGrpSexo().clearSelection();
        tela.getTfDataNascimento().setValue(null);
    }//fim do método limpartELA

    /*
     * método para carregar a tabela de Professores cadastrados
     */
    public void carregarTabela() {
        // capturando o modelo da tabela atual
        DefaultTableModel modelo = (DefaultTableModel) tela.getTabela().getModel();
        // inicializando as linhas da tabela
        modelo.setRowCount(0);
        // carregando a lista de alunos para preencher a tabela
        listaProfessores = buscarTodos();
        // laço de repetição para preencher as linhas da tabela
        for (Professor professor : listaProfessores) {
            modelo.addRow(new String[]{professor.getNome(), professor.getIdade() + "", professor.getCidade(), professor.getSexo() + "", professor.getDataNascimento()});
        }
    }// fim do método carregarTabela

    /*
     * método para validar os campos da inclusão
     */
    private boolean validarIncluir() {
        // verificando se o campo nome está válido
        if (Valida.isEmptyOrNull(tela.getTfNome().getText())) {
            JOptionPane.showMessageDialog(null, Mensagem.nome_n_ok, Titulo.inserir_professor, 0);
            tela.getTfNome().grabFocus();
            return false;
        } else if (Valida.isStringValida(tela.getTfNome().getText())) {
            // informando ao usuário que o nome é inválido
            JOptionPane.showMessageDialog(null, Mensagem.nome_invalido, Titulo.inserir_professor, 0);
            // colocando o foco no campo com erro
            tela.getTfNome().grabFocus();
            // retornar false para o método
            return false;
        }

        // verificando se o campo idade está preenchido
        if (!Valida.isInteger(tela.getTfIdade().getText())) {
            JOptionPane.showMessageDialog(null, Mensagem.idade_n_ok, Titulo.inserir_professor, 0);
            tela.getTfIdade().grabFocus();
            return false;
        }

        // verificando se o campo cidade está preenchido
        if (Valida.isEmptyOrNull(tela.getTfCidade().getText())) {
            JOptionPane.showMessageDialog(null, Mensagem.cidade_n_ok, Titulo.inserir_professor, 0);
            tela.getTfCidade().grabFocus();
            return false;
        } else if (Valida.isStringValida(tela.getTfCidade().getText())) {
            // informando ao usuário que a cidade é inválida
            JOptionPane.showMessageDialog(null, Mensagem.cidade_invalida, Titulo.inserir_professor, 0);
            // colocando o foco no campo com erro
            tela.getTfCidade().grabFocus();
            // retornar false para o método
            return false;
        }

        // validação do radio button de sexo
        if (!tela.getRbMasculino().isSelected()) {
            if (!tela.getRbFeminino().isSelected()) {
                // informando o usuário que o sexo não foi selecionado
                JOptionPane.showMessageDialog(null, Mensagem.sexo_n_ok, Titulo.inserir_professor, 0);
                // retornar falso para o método
                return false;
            }
        }

        // verificando se o campo data de nascimento está preenchido
        if (Valida.isEmptyOrNull(tela.getTfDataNascimento().getText()) || tela.getTfDataNascimento().getText().equals("  /  /    ")) {
            JOptionPane.showMessageDialog(null, Mensagem.data_nascimento_n_ok, Titulo.inserir_professor, 0);
            tela.getTfDataNascimento().grabFocus();
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
            JOptionPane.showMessageDialog(null, Mensagem.cidade_n_ok, Titulo.alterar_professor, 0);
            tela.getTfCidade().grabFocus();
            return false;
        } else if (Valida.isStringValida(tela.getTfCidade().getText())) {
            // informando ao usuário que a cidade é inválida
            JOptionPane.showMessageDialog(null, Mensagem.cidade_invalida, Titulo.inserir_professor, 0);
            // colocando o foco no campo com erro
            tela.getTfCidade().grabFocus();
            // retornar false para o método
            return false;
        }
        return true;
    }// fim do método validarAlterar

    /*
     * método para retornar um objeto Professor para inclusão
     */
    private Professor getProfessorInserir() {
        professor.setNome(tela.getTfNome().getText());
        professor.setIdade(Util.getInteger(tela.getTfIdade().getText()));
        professor.setCidade(tela.getTfCidade().getText());
        // condicional ternária - if em uma linha
        professor.setSexo((tela.getRbMasculino().isSelected()) ? 'M' : 'F');
        professor.setDataNascimento(tela.getTfDataNascimento().getText());
        return professor;
    }// fim do método getProfessorInserir

    /*
     * método para carregar os campos da tela
     */
    private void carregarTela(Professor professor) {
        tela.getTfNome().setText(professor.getNome());
        tela.getTfIdade().setText(professor.getIdade() + "");
        tela.getTfCidade().setText(professor.getCidade());
        tela.getTfDataNascimento().setText(professor.getDataNascimento());
    }// fim do método carregarTela

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
    private Professor getProfessorAlterar() {
        professor.setCidade(tela.getTfCidade().getText());
        return professor;
    }
}// fim da classe
