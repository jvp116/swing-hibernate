package model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Classe responsável por armazenar os atributos, métodos e mapeamento do objeto
 * Aluno
 *
 * @author João Victor
 * @since 17/03/2021
 * @version 1.0
 */
@Entity
@Table(name = "aluno")
public class Aluno implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "id_aluno")
    private int idAluno;

    @Column(name = "nome")
    @Basic(optional = false)
    private String nome;

    @Column(name = "idade")
    @Basic(optional = false)
    private int idade;

    @Column(name = "cidade")
    @Basic(optional = false)
    private String cidade;

    public int getIdAluno() {
        return idAluno;
    }

    public void setIdAluno(int idAluno) {
        this.idAluno = idAluno;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

}
