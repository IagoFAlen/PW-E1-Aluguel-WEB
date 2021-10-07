/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsul.dao;

import br.edu.ifsul.converter.ConverterOrdem;
import br.edu.ifsul.modelo.UnidadeCondominial;
import java.io.Serializable;
import javax.ejb.Stateful;

/**
 *
 * @author Iago Figueira
 * @param <TIPO>
 */
@Stateful
public class UnidadeCondominialDAO<TIPO> extends DAOGenerico<UnidadeCondominial> implements Serializable {
    public UnidadeCondominialDAO(){
        super();
        classePersistente = UnidadeCondominial.class;
        // definição da lista de ordenações
        listaOrdem.add(new Ordem("id", "ID", "="));
        listaOrdem.add(new Ordem("numero", "Numero", "like"));
        // definição da ordem atual
        ordemAtual = listaOrdem.get(1); // vai pegar o segundo da lista de ordens
        // criando uma instância do conversor
        converterOrdem = new ConverterOrdem();
        // criando uma lista de ordens do conversor
        converterOrdem.setListaOrdem(listaOrdem);
    }
}
