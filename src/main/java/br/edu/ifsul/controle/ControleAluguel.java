/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsul.controle;

import br.edu.ifsul.dao.AluguelDAO;
import br.edu.ifsul.dao.LocatarioDAO;
import br.edu.ifsul.dao.UnidadeCondominialDAO;
import br.edu.ifsul.modelo.Aluguel;
import br.edu.ifsul.modelo.Locatario;
import br.edu.ifsul.modelo.Mensalidades;
import br.edu.ifsul.modelo.UnidadeCondominial;
import br.edu.ifsul.util.Util;
import br.edu.ifsul.util.UtilRelatorios;
import java.io.Serializable;
import java.util.HashMap;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author Iago Figueira
 */
@Named(value="controleAluguel")
@ViewScoped
public class ControleAluguel implements Serializable{
    @EJB
    private AluguelDAO<Aluguel> dao;
    private Aluguel objeto;
    @EJB
    private LocatarioDAO<Locatario> daoLocatario;
    @EJB
    private UnidadeCondominialDAO<UnidadeCondominial> daoUnidadeCondominial;
    
    private Mensalidades mensalidades;
    private Boolean novaMensalidade;
    
    public ControleAluguel(){
        
    }
    
    public void imprimeAlugueis(){
        HashMap parametros = new HashMap();
        UtilRelatorios.imprimeRelatorio("relatorioAlugueis", parametros, dao.getListaTodos());
    }
    public void novaMensalidade(){
        novaMensalidade = true;
        mensalidades = new Mensalidades();
        
    }
    
    public void alterarMensalidade(int index){
        mensalidades = objeto.getMensalidades().get(index);
        novaMensalidade = false;
    }
    
    public void salvarMensalidade(){
        if(novaMensalidade){
            objeto.adicionarMensalidades(mensalidades);
        }
        Util.mensagemInformacao("Mensalidade adicionada ou atualizada com sucesso");
    }
    
    public void removerMensalidade(int index){
        objeto.removerMensalidades(index);
        Util.mensagemInformacao("Mensalidade removida com sucesso");
    }
    public AluguelDAO<Aluguel> getDao() {
        return dao;
    }
    /**
     * @param dao the dao to set
     */
    public void setDao(AluguelDAO<Aluguel> dao) {
        this.dao = dao;
    }

    /**
     * @return the objeto
     */
    public Aluguel getObjeto() {
        return objeto;
    }

    /**
     * @param objeto the obj to set
     */
    public void setObjeto(Aluguel obj) {
        this.objeto = obj;
    }
    
    public String listar(){
        return "/privado/aluguel/listar?faces-redirect=true";
    }
    
    public void novo(){
        objeto = new Aluguel();
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
     * @return the daoLocatario
     */
    public LocatarioDAO<Locatario> getDaoLocatario() {
        return daoLocatario;
    }

    /**
     * @param daoLocatario the daoLocatario to set
     */
    public void setDaoLocatario(LocatarioDAO<Locatario> daoLocatario) {
        this.daoLocatario = daoLocatario;
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

    /**
     * @return the mensalidades
     */
    public Mensalidades getMensalidades() {
        return mensalidades;
    }

    /**
     * @param mensalidades the mensalidades to set
     */
    public void setMensalidades(Mensalidades mensalidades) {
        this.mensalidades = mensalidades;
    }

    /**
     * @return the novaMensalidade
     */
    public Boolean getNovaMensalidade() {
        return novaMensalidade;
    }

    /**
     * @param novaMensalidade the novaMensalidade to set
     */
    public void setNovaMensalidade(Boolean novaMensalidade) {
        this.novaMensalidade = novaMensalidade;
    }
    
}
