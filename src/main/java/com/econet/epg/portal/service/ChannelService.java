package com.econet.epg.portal.service;

import com.econet.epg.portal.model.Channel;
import com.econet.epg.portal.model.Transaction;
import com.econet.epg.portal.view.ChannelTreeBean;
import oracle.jdbc.OracleTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.annotation.ManagedProperty;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Named
public class ChannelService implements Serializable {
    private static final Logger logger = LoggerFactory.getLogger(ChannelService.class);

    @PersistenceContext(name = "ecocash-pu", unitName = "ecocash-pu")
    private EntityManager entityManager;
    @PersistenceContext(name = "epg-pu", unitName = "epg-pu")
    private EntityManager entityManager2;
    private StoredProcedureQuery storedProcedureQuery;
    private Date startDate;
    private Date endDate;
    final String TABLE_NAME = "P_RESULT_OUT";
    private Channel channel;
    private String code;
    final Integer WALLET_TYPE = 12;
    private List<Transaction> transactions;
    private List<Channel> channels;
    @Inject
    @ManagedProperty("#{channelTreeBean}")
    private ChannelTreeBean channelTreeBean;

    @PostConstruct
    public void load() {
         channel= (Channel) entityManager2.createQuery("SELECT channel FROM Channel  channel")
                .getSingleResult();
        if (channelTreeBean.getStartDate()!=null && channelTreeBean.getEndDate()!=null){
            startDate=channelTreeBean.getStartDate();
            endDate=channelTreeBean.getEndDate();
        }else{
            final Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -5);
            startDate= cal.getTime();
            endDate= new Date();

        }
        transactions = this.getTransactionDetails();
        channels = this.getChannelBalances();
    }

    public List<Transaction> getTransactionDetails() {

        List<Transaction> transactions = new ArrayList<>();
        this.storedProcedureQuery = this.createTransactionStoredProcedureQuery();
        storedProcedureQuery.setParameter("p_date1", startDate);
        storedProcedureQuery.setParameter("p_date2", endDate);
        storedProcedureQuery.setParameter("p_channel_msisdn", channel.getBillerAcount());
        logger.info("Executing transaction details request with start date ={} , end date ={}, and channel numebr ={}.........", startDate, endDate, channel);

        storedProcedureQuery.execute();

        List<Object[]> transactionCursor = storedProcedureQuery.getResultList();

        for ( Object[] row : transactionCursor ) {
            for ( int i = 0; i < row.length; i++ ) {
                System.out.print(row[i] + "\t");
            }
            System.out.println();
            Transaction transaction = new Transaction();
            transaction.setTransactionCode((String) row[0]);
            transaction.setCreatedDate((Date) row[1]);
            transaction.setTransactionType((String) row[2]);
            transaction.setAuthor((String) row[3]);
            transaction.setAccountNumber((String) row[4]);
            transaction.setBalanceBefore(((BigDecimal)row[5]).doubleValue());
            transaction.setIncreaseAmount(((BigDecimal) row[6]).doubleValue());
            transaction.setReduceAmount(((BigDecimal) row[7]).doubleValue());
            transaction.setBalance(((BigDecimal) row[8]).doubleValue());
            transactions.add(transaction);

        }
        this.transactions = transactions;
        return transactions;
    }

    public List<Channel> getChannelBalances() {
        List<Channel> channels = new ArrayList<>();
        this.storedProcedureQuery = this.createChannelBalanceStoredProcedureQuery();
        storedProcedureQuery.setParameter("p_date1", startDate);
        storedProcedureQuery.setParameter("p_date2", endDate);
        storedProcedureQuery.setParameter("p_channel_code", channel.getBillerCode());
        //storedProcedureQuery.setParameter("p_channel_msisdn", channel.getBillerAcount());
        storedProcedureQuery.setParameter("p_wallet_type", WALLET_TYPE);
        logger.info("Executing transaction details request with start date ={} , end date ={}, and channel number ={}.........", startDate, endDate, channel.getBillerCode());

        storedProcedureQuery.execute();

        List<Object[]> accountBalanceCursor = storedProcedureQuery.getResultList();
        for ( Object[] row : accountBalanceCursor ) {
            for ( int i = 0; i < row.length; i++ ) {
                System.out.print(row[i] + "\t");
            }
            System.out.println();
            Channel channel = new Channel();
            channel.setTransferDate((Date) row[0]);
            channel.setBillerAcount((String) row[1]);
            channel.setBillerName((String) row[2]);
            channel.setOpeningBalance(((BigDecimal) row[3]).doubleValue());
            channel.setInAmount(((BigDecimal)row[4]).doubleValue());
            channel.setInCount(((BigDecimal) row[5]).intValue());
            channel.setOutAmount(((BigDecimal) row[6]).doubleValue());
            channel.setOutCount(((BigDecimal) row[7]).intValue());
            channel.setClosingBalance(((BigDecimal) row[8]).doubleValue());

            channels.add(channel);
        }
        this.channels = channels;
        return channels;
    }
    /*
    @PreDestroy
    public void dispose() {
        entityManager.close();
    }*/

    public StoredProcedureQuery createTransactionStoredProcedureQuery() {
        StoredProcedureQuery storedProcedureQuery = entityManager.createStoredProcedureQuery("ECOKASH.sp_transaction_detail");
        storedProcedureQuery.registerStoredProcedureParameter("p_date1", Date.class, ParameterMode.IN);
        storedProcedureQuery.registerStoredProcedureParameter("p_date2", Date.class, ParameterMode.IN);
        storedProcedureQuery.registerStoredProcedureParameter("p_channel_msisdn", String.class, ParameterMode.IN);
        storedProcedureQuery.registerStoredProcedureParameter(TABLE_NAME, Class.class, ParameterMode.REF_CURSOR);
        return storedProcedureQuery;
    }

    public StoredProcedureQuery createChannelBalanceStoredProcedureQuery() {

        StoredProcedureQuery storedProcedureQuery = entityManager.createStoredProcedureQuery("ECOKASH.sp_channel_mouvement_detail");
        storedProcedureQuery.registerStoredProcedureParameter("p_date1", Date.class, ParameterMode.IN);
        storedProcedureQuery.registerStoredProcedureParameter("p_date2", Date.class, ParameterMode.IN);
        storedProcedureQuery.registerStoredProcedureParameter("p_channel_code", String.class, ParameterMode.IN);
        //storedProcedureQuery.registerStoredProcedureParameter("p_channel_msisdn", String.class, ParameterMode.IN);
        storedProcedureQuery.registerStoredProcedureParameter("p_wallet_type", Integer.class, ParameterMode.IN);
        storedProcedureQuery.registerStoredProcedureParameter(TABLE_NAME, Class.class, ParameterMode.REF_CURSOR);
        return storedProcedureQuery;
    }

    public String showChannelAccount(){
        transactions = this.getTransactionDetails();
        channels = this.getChannelBalances();

        return "transactions.xhtml?faces-redirected=true";
    }

    public StoredProcedureQuery getStoredProcedureQuery() {
        return storedProcedureQuery;
    }

    public void setStoredProcedureQuery(StoredProcedureQuery storedProcedureQuery) {
        this.storedProcedureQuery = storedProcedureQuery;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public List<Channel> getChannels() {
        return channels;
    }

    public void setChannels(List<Channel> channels) {
        this.channels = channels;
    }
}
