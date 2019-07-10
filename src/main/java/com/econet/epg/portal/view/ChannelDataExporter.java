package com.econet.epg.portal.view;

import com.econet.epg.portal.model.Channel;
import com.econet.epg.portal.model.Transaction;
import com.econet.epg.portal.service.ChannelService;

import javax.annotation.PostConstruct;
import javax.faces.annotation.ManagedProperty;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
public class ChannelDataExporter implements Serializable {
    private List<Transaction> transactions;
    @Inject
    @ManagedProperty("#{channelService}")
    private ChannelService channelService;
    @Inject
    @ManagedProperty("#{channelTreeBean}")
    private ChannelTreeBean channelTreeBean;
    private List<Channel> channelBalances;

    @PostConstruct
    public void init() {
        transactions = channelService.getTransactions();
        channelBalances = channelService.getChannelBalances();

    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public ChannelService getChannelService() {
        return channelService;
    }

    public void setChannelService(ChannelService channelService) {
        this.channelService = channelService;
    }

    public ChannelTreeBean getChannelTreeBean() {
        return channelTreeBean;
    }

    public void setChannelTreeBean(ChannelTreeBean channelTreeBean) {
        this.channelTreeBean = channelTreeBean;
    }

    public List<Channel> getChannelBalances() {
        return channelBalances;
    }

    public void setChannelBalances(List<Channel> channelBalances) {
        this.channelBalances = channelBalances;
    }
}
