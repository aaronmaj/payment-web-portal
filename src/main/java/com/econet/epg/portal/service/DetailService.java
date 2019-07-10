package com.econet.epg.portal.service;


import com.econet.epg.portal.model.Field;
import com.econet.epg.portal.rest.RestClient;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.util.List;

@Named
@ApplicationScoped
public class DetailService {

    private List<Field> fields;

    @PostConstruct
    //public void init( @Observes PostConstructApplicationEvent event)
    public void init() {
        fields = RestClient.getFields();
    }

    public List<Field> getFields() {
        return fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }
}
