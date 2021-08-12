/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsul.controle;

import br.edu.ifsul.dao.CondominioDAO;
import br.edu.ifsul.dao.RecursoDAO;
import br.edu.ifsul.dao.UnidadeCondominialDAO;
import br.edu.ifsul.modelo.Condominio;
import br.edu.ifsul.modelo.Recurso;
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
@Named(value="controleCondominio")
@ViewScoped
public class ControleCondominio implements Serializable{
    @EJB
    private CondominioDAO<Condominio> dao;
    private Condominio objeto;

    @EJB
    private RecursoDAO<Recurso> daoRecurso;
    private Recurso recurso;
    
    @EJB
    private UnidadeCondominialDAO<UnidadeCondominial> daoUnidadeCondominial;
    
    
    public ControleCondominio(){
        
    }
    
    public void removerRecurso(Recurso obj){
        objeto.getRecursos().remove(obj);
        Util.mensagemInformacao("Recurso removida com sucesso!");
    }
            
    public void adicionarRecurso(){
        if(!objeto.getRecursos().contains(recurso)){
            objeto.getRecursos().add(recurso);
            Util.mensagemInformacao("Recurso adicionado com sucesso!");
        }else{
            Util.mensagemInformacao("Condomínio já possui o recurso!");
        }
    }
    
    public CondominioDAO<Condominio> getDao() {
        return dao;
    }
    /**
     * @param dao the dao to set
     */
    public void setDao(CondominioDAO<Condominio> dao) {
        this.dao = dao;
    }

    /**
     * @return the objeto
     */
    public Condominio getObjeto() {
        return objeto;
    }

    /**
     * @param objeto the obj to set
     */
    public void setObjeto(Condominio obj) {
        this.objeto = obj;
    }
    
    public String listar(){
        return "/privado/condominio/listar?faces-redirect=true";
    }
    
    public void novo(){
        objeto = new Condominio();
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
     * @return the daoRecurso
     */
    public RecursoDAO<Recurso> getDaoRecurso() {
        return daoRecurso;
    }

    /**
     * @param daoRecurso the daoRecurso to set
     */
    public void setDaoRecurso(RecursoDAO<Recurso> daoRecurso) {
        this.daoRecurso = daoRecurso;
    }

    /**
     * @return the recurso
     */
    public Recurso getRecurso() {
        return recurso;
    }

    /**
     * @param recurso the recurso to set
     */
    public void setRecurso(Recurso recurso) {
        this.recurso = recurso;
    }

    /**
     * @return the daoUnidadeCondominial
     */
    public UnidadeCondominialDAO<UnidadeCondominial> getDaoUnidadeCondominial() {
        return daoUnidadeCondominial;
    }

    /**
     * @param daoUnidadeCondominial the daoUnidadeCondominial to set
     */
    public void setDaoUnidadeCondominial(UnidadeCondominialDAO<UnidadeCondominial> daoUnidadeCondominial) {
        this.daoUnidadeCondominial = daoUnidadeCondominial;
    }
    
}
