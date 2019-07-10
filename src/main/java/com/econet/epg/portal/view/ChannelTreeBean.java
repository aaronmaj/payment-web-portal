package com.econet.epg.portal.view;

import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Named;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Named
@ApplicationScoped
public class ChannelTreeBean implements Serializable {

    private static final Logger logger = LoggerFactory.getLogger(ChannelTreeBean.class);
    Map<String, TreeNode> treeNodeMap = new HashMap<>();
    private TreeNode root;
    private TreeNode selectedNode;
    private String keyword;
    private String detail;
    private Date startDate;
    private Date endDate;
    private Date currentDate = new Date();
    private String period;

    @PostConstruct
    public void init() {
        root = new DefaultTreeNode("Root", null);
        treeNodeMap.put("Transactions", new DefaultTreeNode("Transactions", root));
        treeNodeMap.put("Account", new DefaultTreeNode("Account", root));
    }

    public void onNodeSelect(NodeSelectEvent event) {
        if (selectedNode != null) {
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Selected", event.getTreeNode().toString());
            FacesContext.getCurrentInstance().addMessage(null, message);
            if (selectedNode.getParent() == root) {
                String nodeName = (String) event.getTreeNode().getData();
                logger.info("Selected node {}", nodeName);
                if (nodeName.trim().equalsIgnoreCase("Transactions")) {
                    FacesContext
                            .getCurrentInstance()
                            .getApplication()
                            .getNavigationHandler()
                            .handleNavigation(FacesContext.getCurrentInstance(), null, "transactions.xhtml?faces-redirect=true");

                } else if (nodeName.trim().equalsIgnoreCase("Account")) {
                    FacesContext
                            .getCurrentInstance()
                            .getApplication()
                            .getNavigationHandler()
                            .handleNavigation(FacesContext.getCurrentInstance(), null, "balances.xhtml?faces-redirect=true");

                }

            }
        }
    }

    public void submit() {
        if (selectedNode != null && selectedNode.getData().equals("Transactions")) {
            FacesContext
                    .getCurrentInstance()
                    .getApplication()
                    .getNavigationHandler()
                    .handleNavigation(FacesContext.getCurrentInstance(), null, "transactions.xhtml?faces-redirect=true");

        } else if (selectedNode != null && selectedNode.getData().equals("Account")) {
            FacesContext
                    .getCurrentInstance()
                    .getApplication()
                    .getNavigationHandler()
                    .handleNavigation(FacesContext.getCurrentInstance(), null, "balances.xhtml?faces-redirect=true");

        } else {
            FacesContext
                    .getCurrentInstance()
                    .getApplication()
                    .getNavigationHandler()
                    .handleNavigation(FacesContext.getCurrentInstance(), null, "transactions.xhtml?faces-redirect=true");

        }
    }

    public void reset(ActionEvent actionEvent) {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        startDate = cal.getTime();
        endDate = new Date();

        if (selectedNode != null && selectedNode.getData().equals("Account")) {
            FacesContext
                    .getCurrentInstance()
                    .getApplication()
                    .getNavigationHandler()
                    .handleNavigation(FacesContext.getCurrentInstance(), null, "balances.xhtml?faces-redirect=true");

        } else {
            FacesContext
                    .getCurrentInstance()
                    .getApplication()
                    .getNavigationHandler()
                    .handleNavigation(FacesContext.getCurrentInstance(), null, "transactions.xhtml?faces-redirect=true");
        }
    }

    public void onStartDateSelect(SelectEvent event) {

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        try {
            startDate = format.parse(format.format(event.getObject()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        logger.info("Date selected {}", format.format(startDate));
        if (startDate != null && endDate != null) {
            logger.info("Start date and end date are selected ");
        }
        if (startDate != null) {
            logger.info("Start date selected ");
        }
        if (endDate != null) {
            logger.info("End date selected ");
        }
    }

    public void onEndDateSelect(SelectEvent event) {

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

        try {
            endDate = format.parse(format.format(event.getObject()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (startDate != null && endDate != null) {
            logger.info("Start date and end date are selected ");
        }
        if (startDate != null) {
            logger.info("Start date selected ");
        }
        if (endDate != null) {
            logger.info("End date selected ");
        }
    }

    public void startClick(AjaxBehaviorEvent event) {
        startDate = (Date) ((UIOutput) event.getSource()).getValue();
        logger.info("Start date changed : {} ", ((UIOutput) event.getSource()).getValue());

    }

    public void endClick(AjaxBehaviorEvent event) {

        endDate = (Date) ((UIOutput) event.getSource()).getValue();
        logger.info("End date changed : {} ", ((UIOutput) event.getSource()).getValue());

    }

    public Map<String, TreeNode> getTreeNodeMap() {
        return treeNodeMap;
    }

    public void setTreeNodeMap(Map<String, TreeNode> treeNodeMap) {
        this.treeNodeMap = treeNodeMap;
    }

    public TreeNode getRoot() {
        return root;
    }

    public void setRoot(TreeNode root) {
        this.root = root;
    }

    public TreeNode getSelectedNode() {
        return selectedNode;
    }

    public void setSelectedNode(TreeNode selectedNode) {
        this.selectedNode = selectedNode;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
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

    public Date getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(Date currentDate) {
        this.currentDate = currentDate;
    }

    public String getPeriod() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        period=format.format(startDate)+"_"+format.format(endDate);
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }
}
