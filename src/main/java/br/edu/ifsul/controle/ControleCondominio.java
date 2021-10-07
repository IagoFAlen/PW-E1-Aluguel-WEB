/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsul.controle;

import br.edu.ifsul.dao.CondominioDAO;
import br.edu.ifsul.dao.PessoaDAO;
import br.edu.ifsul.dao.RecursoDAO;
import br.edu.ifsul.dao.UnidadeCondominialDAO;
import br.edu.ifsul.modelo.Condominio;
import br.edu.ifsul.modelo.Pessoa;
import br.edu.ifsul.modelo.Recurso;
import br.edu.ifsul.modelo.UnidadeCondominial;
import br.edu.ifsul.util.Util;
import br.edu.ifsul.util.UtilRelatorios;
import java.io.Serializable;
import java.util.HashMap;
import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
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
    
    @EJB
    private PessoaDAO<Pessoa> daoPessoa;
    private Pessoa pessoa;
    
    protected UnidadeCondominial unidadeCondominial;
    protected Boolean novaUnidadeCondominial;
    
    private Boolean novo;
    
    public ControleCondominio(){
        
    }
    
    public void imprimeCondominios(){
        HashMap parametros = new HashMap();
        UtilRelatorios.imprimeRelatorio("relatorioCondominio", parametros, dao.getListaTodos());
    }
    
    public void novaUnidadeCondominial(){
        novaUnidadeCondominial = true;
        unidadeCondominial = new UnidadeCondominial();
    }
    
    public void alterarUnidadeCondominial(int index){
        unidadeCondominial = objeto.getUnidadescondominiais().get(index);
        novaUnidadeCondominial = false;
    }
    
    public void salvarUnidadeCondominial(){
        if(novaUnidadeCondominial){
            objeto.adicionarUnidadeCondominial(unidadeCondominial);
        }
        Util.mensagemInformacao("Unidade Condominial adicionada ou atualizada com sucesso");
    }
    
    public void removerRecurso(Recurso obj){
        objeto.getRecursos().remove(obj);
        Util.mensagemInformacao("Recurso removida com sucesso!");
    }
    
    public void removerUnidadeCondominial(int index){
        objeto.removerUnidadeCondominial(index);
        Util.mensagemInformacao("Unidade Condominial removida com sucesso");
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
    
    public void verificaUnicidadeNomeCondominio(){
        if(novo){
            try{
                if(!dao.verificaUnicidadeNomeCondominio(objeto.getNome())){
                    Util.mensagemErro("Nome do Condomínio '" + objeto.getNome() + "' "
                                + " já existe no banco de dados");
                    objeto.setNome(null);
                    // capturar o componente que chamou o método
                    UIComponent comp
                            = UIComponent.getCurrentComponent(FacesContext.getCurrentInstance());
                    if(comp != null){
                        // deixar vermelho após o update
                        UIInput input = (UIInput) comp;
                        input.setValid(false);
                    }
                }
            }catch (Exception e){
                Util.mensagemErro("Erro do sistema" + Util.getMensagemErro(e));
            }
        }
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
     * @return the daoPessoa
     */
    public PessoaDAO<Pessoa> getDaoPessoa() {
        return daoPessoa;
    }

    /**
     * @param daoPessoa the daoRecurso to set
     */
    public void setDaoPessoa(PessoaDAO<Pessoa> daoPessoa) {
        this.daoPessoa = daoPessoa;
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
     * @return the pessoa
     */
    public Pessoa getPessoa() {
        return pessoa;
    }

    /**
     * @param pessoa the recurso to set
     */
    public void setRecurso(Pessoa pessoa) {
        this.pessoa = pessoa;
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
     * @return the novo
     */
    public Boolean getNovo() {
        return novo;
    }

    /**
     * @param novo the novo to set
     */
    public void setNovo(Boolean novo) {
        this.novo = novo;
    }
    
    public UnidadeCondominial getUnidadeCondominial(){
        return unidadeCondominial;
    }
    
    public void setUnidadeCondominial(UnidadeCondominial unidadeCondominial){
        this.unidadeCondominial = unidadeCondominial;
    }
    
    public Boolean getNovaUnidadeCondominial(){
        return novaUnidadeCondominial;
    }
    
    public void setNovaUnidadeCondominial(Boolean novaUnidadeCondominial){
        this.novaUnidadeCondominial = novaUnidadeCondominial;
    }
}
