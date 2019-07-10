package com.econet.epg.portal.view;

import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Named
public class CalendarView {

    private static final Logger logger = LoggerFactory.getLogger(CalendarView.class);
    private Date startDate;
    private Date endDate;
    private Date currentDate = new Date();


}
