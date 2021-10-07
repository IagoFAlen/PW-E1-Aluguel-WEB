/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsul.dao;

import br.edu.ifsul.converter.ConverterOrdem;
import br.edu.ifsul.modelo.Condominio;
import java.io.Serializable;
import javax.ejb.Stateful;
import javax.persistence.Query;

/**
 *
 * @author Iago Figueira
 * @param <TIPO>
 */
@Stateful
public class CondominioDAO<TIPO> extends DAOGenerico<Condominio> implements Serializable {
    public CondominioDAO(){
        super();
        classePersistente = Condominio.class;
        listaOrdem.add(new Ordem("id", "ID", "="));
        listaOrdem.add(new Ordem("nome", "Nome", "like"));
        // definição da ordem atual
        ordemAtual = listaOrdem.get(1); // vai pegar o segundo da lista de ordens
        // criando uma instância do conversor
        converterOrdem = new ConverterOrdem();
        // criando uma lista de ordens do conversor
        converterOrdem.setListaOrdem(listaOrdem);
    }
    
    public boolean verificaUnicidadeNomeCondominio(String nome) throws Exception{
        String jpql = "from Condominio where nome = :pnome";
        Query query = em.createQuery(jpql);
        query.setParameter("pnome", nome);
        if(query.getResultList().size() > 0){
            return false;
        }else{
            return true;
        }
    }
    
    @Override
    public Condominio localizar(Object id) throws Exception{
        Condominio objeto = em.find(Condominio.class, id);
        // Deve-se inicializar a coleção ou coleções de objeto para não
        // dar um erro de lazy inicialization exception
        objeto.getUnidadescondominiais().size();
        objeto.getRecursos().size();
        
        return objeto;
    }
    
}
