/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsul.dao;

import br.edu.ifsul.modelo.Condominio;
import java.io.Serializable;
import javax.ejb.Stateful;

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
    }
}
