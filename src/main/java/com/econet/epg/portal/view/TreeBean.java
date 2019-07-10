package com.econet.epg.portal.view;


import com.econet.epg.portal.model.*;
import com.econet.epg.portal.rest.RestClient;
import org.apache.commons.lang3.time.DateUtils;
import org.primefaces.event.NodeCollapseEvent;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.lang3.StringUtils;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Named;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Named
@ApplicationScoped
public class TreeBean implements Serializable {
    private static final Logger logger = LoggerFactory.getLogger(TreeBean.class);
    Map<String, TreeNode> treeNodeMap = new HashMap<>();
    Map<String, TreeNode> subTreeNodeMap = new HashMap<>();
    Map<String, TreeNode> accountTreeNodeMap = new HashMap<>();
    private TreeNode root;
    private TreeNode selectedNode;
    private List<Category> categories;
    private List<SubCategory> subCategories;
    private List<Payment> payments;
    private String keyword;
    private String detail;
    private Date startDate;
    private Date endDate;
    private Field selectedItem;
    private Date currentDate = new Date();

    @PostConstruct
    public void init() {
        categories = RestClient.getCategories();
        subCategories = RestClient.getSubCategories();
        root = new DefaultTreeNode("Root", null);
        treeNodeMap.put("Tous", new DefaultTreeNode("Tous", root));

        for ( Category category : categories ) {
            treeNodeMap.put(category.getName(), new DefaultTreeNode(category.getName(), root));
        }

        Iterator<Map.Entry<String, TreeNode>> entries = treeNodeMap.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<String, TreeNode> entry = entries.next();
            Optional<Category> categoryOpt = categories
                    .stream()
                    .filter(cat -> cat.getName().trim().equals(entry.getKey().trim())).findAny();

            if (categoryOpt.isPresent()) {
                Category category = categoryOpt.get();

                List<SubCategory> subCategories = RestClient.getSubCategories(category.getName())
                        .stream()
                        .collect(Collectors.toList());
                for ( SubCategory subCategory : subCategories ) {
                    subTreeNodeMap.put(subCategory.getName(), new DefaultTreeNode(subCategory.getName(), entry.getValue()));
                }
            }
        }

        Iterator<Map.Entry<String, TreeNode>> accountEntries = subTreeNodeMap.entrySet().iterator();
        while (accountEntries.hasNext()) {
            Map.Entry<String, TreeNode> entry = accountEntries.next();
            SubCategory subCategory = subCategories
                    .stream()
                    .filter(subCat -> subCat.getName().trim().equals(entry.getKey().trim())).findAny().get();
            List<Account> accounts = RestClient.getAccounts(subCategory.getName())
                    .stream()
                    .collect(Collectors.toList());
            for ( Account account : accounts ) {
                accountTreeNodeMap.put(account.getName(), new DefaultTreeNode(account.getName(), entry.getValue()));
            }

        }
    }

    public void displaySelectedSingle() {
        if (selectedNode != null) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Selected", selectedNode.getData().toString());
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }
    public void onNodeCollapse(NodeCollapseEvent event) {
        event.getTreeNode().setExpanded(false);
        if (selectedNode.getParent() == root) {
            selectedNode.getChildren().stream().forEach(treeNode ->  event.getTreeNode().setExpanded(false));
        }
    }
    public void onNodeSelect(NodeSelectEvent event) {
        if (selectedNode != null) {
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Selected", event.getTreeNode().toString());
            FacesContext.getCurrentInstance().addMessage(null, message);
            if (selectedNode.isLeaf() && selectedNode.getParent() == root) {
                String nodeName = (String) event.getTreeNode().getData();
                logger.info("Selected node {}", nodeName);
                payments = RestClient.getPayments();
            }
            if (selectedNode.getParent() == root && !selectedNode.isLeaf()) {
                String nodeName = (String) event.getTreeNode().getData();
                logger.info("Selected node {}", nodeName);
                if (StringUtils.isNotEmpty(keyword) && this.getStartDate() != null && this.getEndDate() != null && selectedItem != null && detail != null) {
                    logger.info("Search by category name {} , keyword :{}, date range between: {} and {}, parameter name ={}, parameter value={}", nodeName, keyword, format.format(this.getStartDate()), format.format(this.getEndDate()), selectedItem.getName(), detail);
                    if (DateUtils.isSameDay(this.getStartDate(), this.getEndDate())) {
                        payments = RestClient.getCategoryPaymentsBySearchValuesWithExactDate(nodeName, keyword, selectedItem.getName(), detail, this.getStartDate());
                    } else {
                        payments = RestClient.getCategoryPaymentsBySearchValues(nodeName, keyword, selectedItem.getName(), detail, this.getStartDate(), this.getEndDate());
                    }
                } else if (StringUtils.isNotEmpty(keyword) && this.getStartDate() != null && this.getEndDate() != null && detail != null) {
                    logger.info("Search by category name {} , date range : {} and {}", nodeName, format.format(this.getStartDate()), format.format(this.getEndDate()));
                    if (DateUtils.isSameDay(this.getStartDate(), this.getEndDate())) {
                        payments = RestClient.getCategoryPaymentsBySearchValuesWithExactDate(nodeName, keyword, null, detail, this.getStartDate());

                    } else {
                        payments = RestClient.getCategoryPaymentsBySearchValues(nodeName, keyword, null, detail, this.getStartDate(), this.getEndDate());

                    }
                } else if (StringUtils.isNotEmpty(keyword) && this.getStartDate() != null && this.getEndDate() != null) {
                    logger.info("Search by category name {} , keword -{}- and  date range : {} and {}", nodeName, keyword, format.format(this.getStartDate()), format.format(this.getEndDate()));
                    payments = RestClient.getCategoryPaymentsByKeywordAndDateValues(nodeName, keyword, this.getStartDate(), this.getEndDate());

                } else if (this.getStartDate() != null && this.getEndDate() != null) {
                    logger.info("Search by category name {} , date range : {} and {}", nodeName, format.format(this.getStartDate()), format.format(this.getEndDate()));
                    if (DateUtils.isSameDay(this.getStartDate(), this.getEndDate())) {
                        payments = RestClient.getCategoryPaymentsByExactDate(nodeName, this.getStartDate());
                    } else {
                        payments = RestClient.getCategoryPaymentsByDateRange(nodeName, this.getStartDate(), this.getEndDate());
                    }
                } else if (StringUtils.isNotEmpty(keyword) && selectedItem != null && detail != null) {
                    payments = RestClient.getCategoryPaymentsBySearchValuesWithoutDate(nodeName, keyword, selectedItem.getName(), detail);
                } else if (selectedItem != null && detail != null) {
                    payments = RestClient.getCategoryPaymentsByDetailValue(nodeName, selectedItem.getName(), detail);
                } else if (StringUtils.isNotEmpty(keyword)) {
                    payments = RestClient.getCategoryPaymentsByKeyword(nodeName, keyword);
                } else if (getStartDate() != null) {
                    payments = RestClient.getCategoryPaymentsByStartDate(nodeName, this.getStartDate());
                } else if (getEndDate() != null) {
                    payments = RestClient.getCategoryPaymentsByEndDate(nodeName, this.getEndDate());
                } else {
                    payments = RestClient.getCategoryPayments(nodeName);
                }
            }
            if (selectedNode.getParent().getParent() == root) {
                String nodeName = (String) event.getTreeNode().getData();
                logger.info("Selected node {}", nodeName);
                if (StringUtils.isNotEmpty(keyword) && this.getStartDate() != null && this.getEndDate() != null && selectedItem != null && detail != null) {
                    logger.info("Search by subCategory name {} , keyword :{}, date range between: {} and {}, parameter name ={}, parameter value={}", nodeName, keyword, format.format(this.getStartDate()), format.format(this.getEndDate()), selectedItem.getName(), detail);
                    if (DateUtils.isSameDay(this.getStartDate(), this.getEndDate())) {
                        payments = RestClient.getSubCategoryPaymentsBySearchValuesWithExactDate(nodeName, keyword, selectedItem.getName(), detail, this.getStartDate());
                    } else {
                        payments = RestClient.getSubCategoryPaymentsBySearchValues(nodeName, keyword, selectedItem.getName(), detail, this.getStartDate(), this.getEndDate());
                    }
                } else if (StringUtils.isNotEmpty(keyword) && this.getStartDate() != null && this.getEndDate() != null && detail != null) {
                    logger.info("Search by subCategory name {} , date range : {} and {}", nodeName, format.format(this.getStartDate()), format.format(this.getEndDate()));
                    if (DateUtils.isSameDay(this.getStartDate(), this.getEndDate())) {
                        payments = RestClient.getSubCategoryPaymentsBySearchValuesWithExactDate(nodeName, keyword, null, detail, this.getStartDate());

                    } else {
                        payments = RestClient.getSubCategoryPaymentsBySearchValues(nodeName, keyword, null, detail, this.getStartDate(), this.getEndDate());

                    }
                } else if (StringUtils.isNotEmpty(keyword) && this.getStartDate() != null && this.getEndDate() != null) {
                    logger.info("Search by subCategory name {} , keword -{}- and  date range : {} and {}", nodeName, keyword, format.format(this.getStartDate()), format.format(this.getEndDate()));
                    payments = RestClient.getSubCategoryPaymentsByKeywordAndDateValues(nodeName, keyword, this.getStartDate(), this.getEndDate());

                } else if (this.getStartDate() != null && this.getEndDate() != null) {
                    logger.info("Search by subCategory name {} , date range : {} and {}", nodeName, format.format(this.getStartDate()), format.format(this.getEndDate()));
                    if (DateUtils.isSameDay(this.getStartDate(), this.getEndDate())) {
                        payments = RestClient.getSubCategoryPaymentsByExactDate(nodeName, this.getStartDate());
                    } else {
                        payments = RestClient.getSubCategoryPaymentsByDateRange(nodeName, this.getStartDate(), this.getEndDate());
                    }
                } else if (StringUtils.isNotEmpty(keyword) && selectedItem != null && detail != null) {
                    payments = RestClient.getSubCategoryPaymentsBySearchValuesWithoutDate(nodeName, keyword, selectedItem.getName(), detail);
                } else if (selectedItem != null && detail != null) {
                    payments = RestClient.getSubCategoryPaymentsByDetailValue(nodeName, selectedItem.getName(), detail);
                } else if (StringUtils.isNotEmpty(keyword)) {
                    payments = RestClient.getSubCategoryPaymentsByKeyword(nodeName, keyword);
                } else if (getStartDate() != null) {
                    payments = RestClient.getSubCategoryPaymentsByStartDate(nodeName, this.getStartDate());
                } else if (getEndDate() != null) {
                    payments = RestClient.getSubCategoryPaymentsByEndDate(nodeName, this.getEndDate());
                } else {

                    payments = RestClient.getSubCategoryPayments(nodeName);
                }
            }
            if (selectedNode.isLeaf() && selectedNode.getParent() != root) {
                String nodeName = (String) event.getTreeNode().getData();
                logger.info("Selected node {}", nodeName);
                logger.info("Selected node name {}", selectedNode.getData().toString());

                if (StringUtils.isNotEmpty(keyword) && this.getStartDate() != null && this.getEndDate() != null && selectedItem != null && detail != null) {
                    logger.info("Search by account name {} , keyword :{}, date range between: {} and {}, parameter name ={}, parameter value={}", nodeName, keyword, format.format(this.getStartDate()), format.format(this.getEndDate()), selectedItem.getName(), detail);
                    if (DateUtils.isSameDay(this.getStartDate(), this.getEndDate())) {
                        payments = RestClient.getAccountPaymentsBySearchValuesWithExactDate(nodeName, keyword, selectedItem.getName(), detail, this.getStartDate());
                    } else {
                        payments = RestClient.getAccountPaymentsBySearchValues(nodeName, keyword, selectedItem.getName(), detail, this.getStartDate(), this.getEndDate());
                    }
                } else if (StringUtils.isNotEmpty(keyword) && this.getStartDate() != null && this.getEndDate() != null && detail != null) {
                    logger.info("Search by account name {} , date range : {} and {}", nodeName, format.format(this.getStartDate()), format.format(this.getEndDate()));
                    if (DateUtils.isSameDay(this.getStartDate(), this.getEndDate())) {
                        payments = RestClient.getAccountPaymentsBySearchValuesWithExactDate(nodeName, keyword, null, detail, this.getStartDate());

                    } else {
                        payments = RestClient.getAccountPaymentsBySearchValues(nodeName, keyword, null, detail, this.getStartDate(), this.getEndDate());

                    }
                } else if (StringUtils.isNotEmpty(keyword) && this.getStartDate() != null && this.getEndDate() != null) {
                    logger.info("Search by account name {} , keword -{}- and  date range : {} and {}", nodeName, keyword, format.format(this.getStartDate()), format.format(this.getEndDate()));
                    payments = RestClient.getAccountPaymentsByKeywordAndDateValues(nodeName, keyword, this.getStartDate(), this.getEndDate());

                } else if (this.getStartDate() != null && this.getEndDate() != null) {
                    logger.info("Search by account name {} , date range : {} and {}", nodeName, format.format(this.getStartDate()), format.format(this.getEndDate()));
                    if (DateUtils.isSameDay(this.getStartDate(), this.getEndDate())) {
                        payments = RestClient.getAccountPaymentsByExactDate(nodeName, this.getStartDate());
                    } else {
                        payments = RestClient.getAccountPaymentsByDateRange(nodeName, this.getStartDate(), this.getEndDate());
                    }
                } else if (StringUtils.isNotEmpty(keyword) && selectedItem != null && detail != null) {
                    payments = RestClient.getAccountPaymentsBySearchValuesWithoutDate(nodeName, keyword, selectedItem.getName(), detail);
                } else if (selectedItem != null && detail != null) {
                    payments = RestClient.getAccountPaymentsByDetailValue(nodeName, selectedItem.getName(), detail);
                } else if (StringUtils.isNotEmpty(keyword)) {
                    payments = RestClient.getAccountPaymentsByKeyword(nodeName, keyword);
                } else if (getStartDate() != null) {
                    payments = RestClient.getAccountPaymentsByStartDate(nodeName, this.getStartDate());
                } else if (getEndDate() != null) {
                    payments = RestClient.getAccountPaymentsByEndDate(nodeName, this.getEndDate());
                } else {

                    payments = RestClient.getAccountPayments(nodeName);
                }
            }
            FacesContext
                    .getCurrentInstance()
                    .getApplication()
                    .getNavigationHandler()
                    .handleNavigation(FacesContext.getCurrentInstance(), null, "index.xhtml?faces-redirect=true");

        }

    }

    public Map<String, TreeNode> getTreeNodeMap() {
        return treeNodeMap;
    }

    public void setTreeNodeMap(Map<String, TreeNode> treeNodeMap) {
        this.treeNodeMap = treeNodeMap;
    }

    public Map<String, TreeNode> getSubTreeNodeMap() {
        return subTreeNodeMap;
    }

    public void setSubTreeNodeMap(Map<String, TreeNode> subTreeNodeMap) {
        this.subTreeNodeMap = subTreeNodeMap;
    }

    public Map<String, TreeNode> getAccountTreeNodeMap() {
        return accountTreeNodeMap;
    }

    public void setAccountTreeNodeMap(Map<String, TreeNode> accountTreeNodeMap) {
        this.accountTreeNodeMap = accountTreeNodeMap;
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

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<SubCategory> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(List<SubCategory> subCategories) {
        this.subCategories = subCategories;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public void listener(AjaxBehaviorEvent event) {
        logger.info("Search by key word : {}", keyword);
        payments = RestClient.getPaymentsByKeyword(keyword);
        FacesContext
                .getCurrentInstance()
                .getApplication()
                .getNavigationHandler()
                .handleNavigation(FacesContext.getCurrentInstance(), null, "index.xhtml?faces-redirect=true");

    }

    public void detailListener(AjaxBehaviorEvent event) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        if (StringUtils.isNotEmpty(keyword) && this.getStartDate() != null && this.getEndDate() != null && selectedItem != null && detail != null) {
            logger.info("Search by keyword :{}, date range between: {} and {}, parameter name ={}, parameter value={}", keyword, format.format(this.getStartDate()), format.format(this.getEndDate()), selectedItem.getName(), detail);
            if (DateUtils.isSameDay(this.getStartDate(), this.getEndDate())) {
                payments = RestClient.getPaymentsBySearchValuesWithExactDate(keyword, selectedItem.getName(), detail, this.getStartDate());
            } else {
                payments = RestClient.getPaymentsBySearchValues(keyword, selectedItem.getName(), detail, this.getStartDate(), this.getEndDate());
            }
        } else if (StringUtils.isNotEmpty(keyword) && selectedItem != null && detail != null) {
            logger.info("Search by keyword :{}, parameter name ={}, parameter value={}", keyword, selectedItem.getName(), detail);
            payments = RestClient.getPaymentsBySearchValuesWithoutDate(keyword, selectedItem.getName(), detail);

        } else if (selectedItem != null && detail != null) {
            logger.info("Search by details : {} {}", selectedItem.getName(), detail);
            payments = RestClient.getPaymentsByDetailValue(selectedItem.getName(), detail);
            FacesContext
                    .getCurrentInstance()
                    .getApplication()
                    .getNavigationHandler()
                    .handleNavigation(FacesContext.getCurrentInstance(), null, "index.xhtml?faces-redirect=true");
        }
    }

    public void submit() {
        logger.info("Search by criteria =========================>>>>>>}");
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        if (StringUtils.isNotEmpty(keyword) && this.getStartDate() != null && this.getEndDate() != null && selectedItem != null && detail != null) {
            logger.info("Search by keyword :{}, date range between: {} and {}, parameter name ={}, parameter value={}", keyword, format.format(this.getStartDate()), format.format(this.getEndDate()), selectedItem.getName(), detail);
            if (DateUtils.isSameDay(this.getStartDate(), this.getEndDate())) {
                payments = RestClient.getPaymentsBySearchValuesWithExactDate(keyword, selectedItem.getName(), detail, this.getStartDate());
            } else {
                payments = RestClient.getPaymentsBySearchValues(keyword, selectedItem.getName(), detail, this.getStartDate(), this.getEndDate());
            }
        } else if (StringUtils.isNotEmpty(keyword) && this.getStartDate() != null && this.getEndDate() != null && detail != null) {
            logger.info("Search by keyword {}, date range : {} and {} with value {}",keyword, format.format(this.getStartDate()), format.format(this.getEndDate()),detail);
            if (DateUtils.isSameDay(this.getStartDate(), this.getEndDate())) {
                payments = RestClient.getPaymentsBySearchValuesWithExactDate(keyword, null, detail, this.getStartDate());

            } else {
                payments = RestClient.getPaymentsBySearchValues(keyword, null, detail, this.getStartDate(), this.getEndDate());

            }
        } else if (StringUtils.isNotEmpty(keyword) && this.getStartDate() != null && this.getEndDate() != null) {
            logger.info("Search by keword -{}- and  date range : {} and {}", keyword, format.format(this.getStartDate()), format.format(this.getEndDate()));
            payments = RestClient.getPaymentsByKeywordAndDateValues(keyword, this.getStartDate(), this.getEndDate());

        } else if (this.getStartDate() != null && this.getEndDate() != null) {
            logger.info("Search by date range : {} and {}", format.format(this.getStartDate()), format.format(this.getEndDate()));
            if (DateUtils.isSameDay(this.getStartDate(), this.getEndDate())) {
                payments = RestClient.getPaymentsByExactDate(this.getStartDate());
            } else {
                payments = RestClient.getPaymentsByDateRange(this.getStartDate(), this.getEndDate());
            }
        } else if (StringUtils.isNotEmpty(keyword) && selectedItem != null && detail != null) {
            payments = RestClient.getPaymentsBySearchValuesWithoutDate(keyword, selectedItem.getName(), detail);
        } else if (selectedItem != null && detail != null) {
            payments = RestClient.getPaymentsByDetailValue(selectedItem.getName(), detail);
        } else if (StringUtils.isNotEmpty(keyword)) {
            payments = RestClient.getPaymentsByKeyword(keyword);
        } else if (getStartDate() != null) {
            payments = RestClient.getPaymentsByStartDate(this.getStartDate());
        } else if (getEndDate() != null) {
            payments = RestClient.getPaymentsByStartDate(this.getEndDate());
        }
        FacesContext
                .getCurrentInstance()
                .getApplication()
                .getNavigationHandler()
                .handleNavigation(FacesContext.getCurrentInstance(), null, "index.xhtml?faces-redirect=true");

    }
    public void collapseAll() {
        setExpandedRecursively(root, false);
    }

    public void expandAll() {
        setExpandedRecursively(root, true);
    }

    private void setExpandedRecursively(final TreeNode node, final boolean expanded) {
        for (final TreeNode child : node.getChildren()) {
            setExpandedRecursively(child, expanded);
        }
        node.setExpanded(expanded);
    }
    public void reset(ActionEvent actionEvent) {
        keyword = null;
        detail = null;
        startDate = null;
        endDate = null;
        selectedItem = null;
        payments = RestClient.getPayments();
        collapseAll();
        FacesContext
                .getCurrentInstance()
                .getApplication()
                .getNavigationHandler()
                .handleNavigation(FacesContext.getCurrentInstance(), null, "index.xhtml?faces-redirect=true");

    }

    public void onStartDateSelect(SelectEvent event) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Date Selected", format.format(event.getObject())));
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
        FacesContext facesContext = FacesContext.getCurrentInstance();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Date Selected", format.format(event.getObject())));

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

    public void valueChange(ValueChangeEvent event) {

        if (event.getNewValue() instanceof Field) {
            Field selectedItem = (Field) event.getNewValue();
            logger.info("Selected item : name - {}, description - {}", selectedItem.getName(), selectedItem.getDescription());
            this.selectedItem = selectedItem;
        } else {
            logger.info("Selected item class name - {}", event.getNewValue().getClass().getName());
        }
    }

    public void selectedListener(AjaxBehaviorEvent event) {
        //Field selectedItem= (Field) ((UIOutput) event.getSource()).getValue();
        //this.selectedItem = selectedItem;

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

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Field getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(Field selectedItem) {
        this.selectedItem = selectedItem;
    }
}
