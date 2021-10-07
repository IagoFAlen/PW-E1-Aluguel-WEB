/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsul.dao;


import br.edu.ifsul.converter.ConverterOrdem;
import br.edu.ifsul.modelo.Aluguel;
import java.io.Serializable;
import javax.ejb.Stateful;

/**
 *
 * @author Iago Figueira
 */ 

@Stateful
public class AluguelDAO<TIPO> extends DAOGenerico<Aluguel> implements Serializable {
    public AluguelDAO(){
        super();
        classePersistente = Aluguel.class;
        listaOrdem.add(new Ordem("id", "ID", "="));
        listaOrdem.add(new Ordem("valor", "Valor", "like"));
        // definição da ordem atual
        ordemAtual = listaOrdem.get(1); // vai pegar o segundo da lista de ordens
        // criando uma instância do conversor
        converterOrdem = new ConverterOrdem();
        // criando uma lista de ordens do conversor
        converterOrdem.setListaOrdem(listaOrdem);
    }
    
    @Override
    public Aluguel localizar(Object id) throws Exception{
        Aluguel objeto = em.find(Aluguel.class, id);
        // Deve-se inicializar a coleção ou coleções de objeto para não
        // dar um erro de lazy inicialization exception
        objeto.getMensalidades().size();
        
        return objeto;
    }
}
