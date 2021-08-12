/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsul.controle;

import br.edu.ifsul.dao.LocatarioDAO;
import br.edu.ifsul.dao.PessoaDAO;
import br.edu.ifsul.modelo.Locatario;
import br.edu.ifsul.modelo.Pessoa;
import br.edu.ifsul.util.Util;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author Iago Figueira
 */
@Named(value="controleLocatario")
@ViewScoped
public class ControleLocatario implements Serializable{
    @EJB
    private LocatarioDAO<Locatario> dao;
    private Locatario objeto;

    @EJB
    private PessoaDAO<Pessoa> daoPessoa;
    
    public ControleLocatario(){
        
    }
    
    public LocatarioDAO<Locatario> getDao() {
        return dao;
    }
    /**
     * @param dao the dao to set
     */
    public void setDao(LocatarioDAO<Locatario> dao) {
        this.dao = dao;
    }

    /**
     * @return the objeto
     */
    public Locatario getObjeto() {
        return objeto;
    }

    /**
     * @param objeto the obj to set
     */
    public void setObjeto(Locatario obj) {
        this.objeto = obj;
    }
    
    public String listar(){
        return "/privado/locatario/listar?faces-redirect=true";
    }
    
    public void novo(){
        objeto = new Locatario();
    }
    
    
    public void alterar(Object id){
        try{
            objeto = getDao().localizar(id);
        }catch(Exception e){
            Util.mensagemErro("Erro ao recuperar o objeto: " + Util.getMensagemErro(e));
        }
    }
    
    public void excluir(Object id){
        try{
            objeto = getDao().localizar(id);
            getDao().remove(objeto);
            Util.mensagemInformacao("Objeto removido com sucesso");
        }catch(Exception e){
            Util.mensagemErro("Erro ao remover o objeto");
        }
    }
    
    public void salvar(){
        try{
            if(objeto.getId() == null){
                getDao().persist(objeto);
            }else{
                getDao().merge(objeto);
            }
            Util.mensagemInformacao("Objeto persistido com sucesso!");
        }catch (Exception e){
            Util.mensagemErro("Erro ao persistir objeto : " + Util.getMensagemErro(e));
        }
    }

    /**
     * @return the daoPessoa
     */
    public PessoaDAO<Pessoa> getDaoPessoa() {
        return daoPessoa;
    }

    /**
     * @param daoPessoa the daoPessoa to set
     */
    public void setDaoPessoa(PessoaDAO<Pessoa> daoPessoa) {
        this.daoPessoa = daoPessoa;
    }
    
}

