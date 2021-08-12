/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsul.controle;

import br.edu.ifsul.dao.UnidadeCondominialDAO;
import br.edu.ifsul.modelo.UnidadeCondominial;
import br.edu.ifsul.util.Util;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author Iago Figueira
 */
@Named(value="controleUnidadeCondominial")
@ViewScoped
public class ControleUnidadeCondominial implements Serializable{
    @EJB
    private UnidadeCondominialDAO<UnidadeCondominial> dao;
    private UnidadeCondominial objeto;

    
    public ControleUnidadeCondominial(){
        
    }
    
    public UnidadeCondominialDAO<UnidadeCondominial> getDao() {
        return dao;
    }
    /**
     * @param dao the dao to set
     */
    public void setDao(UnidadeCondominialDAO<UnidadeCondominial> dao) {
        this.dao = dao;
    }

    /**
     * @return the objeto
     */
    public UnidadeCondominial getObjeto() {
        return objeto;
    }

    /**
     * @param objeto the obj to set
     */
    public void setObjeto(UnidadeCondominial obj) {
        this.objeto = obj;
    }
    
    public String listar(){
        return "/privado/unidadecondominial/listar?faces-redirect=true";
    }
    
    public void novo(){
        objeto = new UnidadeCondominial();
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
    
}
