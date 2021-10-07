/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsul.converter;

import br.edu.ifsul.dao.Ordem;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Iago Figueira
 */
@FacesConverter(value = "converterOrdem")
@RequestScoped
public class ConverterOrdem implements Serializable, Converter<Object>{

    private List<Ordem> listaOrdem;
    
    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        for(Ordem o : listaOrdem){
            if(o.getAtributo().equals(string)){
                return o;
            }
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object t) {
        if(t == null){
            return null;
        }
        return t.toString();
    }

    /**
     * @return the listaOrdem
     */
    public List<Ordem> getListaOrdem() {
        return listaOrdem;
    }

    /**
     * @param listaOrdem the listaOrdem to set
     */
    public void setListaOrdem(List<Ordem> listaOrdem) {
        this.listaOrdem = listaOrdem;
    }
    
}
