package com.econet.epg.portal.rest;

import com.econet.epg.portal.model.*;
import org.apache.commons.lang3.time.DateUtils;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class RestClient {
    private static final Logger logger = LoggerFactory.getLogger(RestClient.class);
    private static final String REST_URI = "http://192.168.100.213:9080/biller-gateway/v1/";
    private static List<Object> providers = new ArrayList<Object>();
    private static WebTarget target;

    public static List<Payment> getPayments() {

        List<Payment> payments = new ArrayList<Payment>();
        try {

            Client client = ClientBuilder.newBuilder().build();
            providers.add(new JacksonJsonProvider());
            client.register(providers);
            WebTarget target = client.target(REST_URI + "payments");

            Response response = target.request(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .get();
            if (response.getStatus() == 200) {
                payments = response.readEntity(new GenericType<List<Payment>>() {
                });
            } else {
                response.close();
                throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return payments;
    }

    public static List<Payment> findRange(int[] range) {

        List<Payment> payments = new ArrayList<Payment>();
        try {

            Client client = ClientBuilder.newBuilder().build();
            providers.add(new JacksonJsonProvider());
            client.register(providers);
            WebTarget target = client.target(REST_URI + "payments?offset=" + range[0] + "&limit=" + range[1]);

            Response response = target.request(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .get();
            if (response.getStatus() == 200) {
                payments = response.readEntity(new GenericType<List<Payment>>() {
                });
            } else {
                response.close();
                throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return payments;
    }

    public static Payment getPaymentById(Long id) {
        Payment payment = new Payment();
        try {
            Client client = ClientBuilder.newBuilder().build();
            client.register(Arrays.asList(new JacksonJsonProvider()));
            WebTarget target = client.target(REST_URI + "payments/" + id);

            Response response = target.request(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .get();
            if (response.getStatus() == 200) {
                payment = response.readEntity(new GenericType<Payment>() {
                });
            } else {
                response.close();
                throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return payment;
    }

    public static List<Detail> getPaymentDetails(Long id) {

        List<Detail> details = new ArrayList<>();
        try {

            Client client = ClientBuilder.newBuilder().build();
            providers.add(new JacksonJsonProvider());
            client.register(providers);
            WebTarget target = client.target(REST_URI + "payments/" + id + "/details");

            Response response = target.request(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .get();
            if (response.getStatus() == 200) {
                details = response.readEntity(new GenericType<List<Detail>>() {
                });
            } else {
                response.close();
                throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return details;
    }

    public static List<Category> getCategories() {

        List<Category> categories = new ArrayList<>();
        try {

            Client client = ClientBuilder.newBuilder().build();
            providers.add(new JacksonJsonProvider());
            client.register(providers);
            WebTarget target = client.target(REST_URI + "categories");

            Response response = target.request(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .get();
            if (response.getStatus() == 200) {
                categories = response.readEntity(new GenericType<List<Category>>() {
                });
            } else {
                response.close();
                throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return categories;
    }

    public static List<SubCategory> getSubCategories() {

        List<SubCategory> subCategories = new ArrayList<>();
        try {

            Client client = ClientBuilder.newBuilder().build();
            providers.add(new JacksonJsonProvider());
            client.register(providers);
            WebTarget target = client.target(REST_URI + "subcategories");

            Response response = target.request(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .get();
            if (response.getStatus() == 200) {
                subCategories = response.readEntity(new GenericType<List<SubCategory>>() {
                });
            } else {
                response.close();
                throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return subCategories;
    }

    public static List<SubCategory> getSubCategories(String categoryName) {

        List<SubCategory> subCategories = new ArrayList<>();
        try {

            Client client = ClientBuilder.newBuilder().build();
            providers.add(new JacksonJsonProvider());
            client.register(providers);
            WebTarget target = client.target(REST_URI + "categories/" + categoryName.trim() + "/subcategories");

            Response response = target.request(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .get();
            if (response.getStatus() == 200) {
                subCategories = response.readEntity(new GenericType<List<SubCategory>>() {
                });
            } else {
                response.close();
                throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return subCategories;
    }

    public static List<Payment> getCategoryPayments(String categoryName) {
        logger.info("Posting url : {}categories/{}/payments",REST_URI,categoryName);
        List<Payment> categorPayments = new ArrayList<>();
        try {

            Client client = ClientBuilder.newBuilder().build();
            providers.add(new JacksonJsonProvider());
            client.register(providers);
            WebTarget target = client.target(REST_URI + "categories/" + categoryName.trim() + "/payments");

            Response response = target.request(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .get();
            if (response.getStatus() == 200) {
                categorPayments = response.readEntity(new GenericType<List<Payment>>() {
                });
            } else {
                response.close();
                throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return categorPayments;
    }

    public static List<Account> getAccounts() {

        List<Account> accounts = new ArrayList<>();
        try {

            Client client = ClientBuilder.newBuilder().build();
            providers.add(new JacksonJsonProvider());
            client.register(providers);
            WebTarget target = client.target(REST_URI + "accounts");
            Response response = target.request(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .get();
            if (response.getStatus() == 200) {
                accounts = response.readEntity(new GenericType<List<Account>>() {
                });
            } else {
                response.close();
                throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return accounts;
    }

    public static List<Account> getAccounts(String subCategoryName) {

        List<Account> accounts = new ArrayList<>();
        try {
            Client client = ClientBuilder.newBuilder().build();
            providers.add(new JacksonJsonProvider());
            client.register(providers);
            WebTarget target = client.target(REST_URI + "subcategories/" + subCategoryName.trim() + "/accounts");
            Response response = target.request(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .get();
            if (response.getStatus() == 200) {
                accounts = response.readEntity(new GenericType<List<Account>>() {
                });
            } else {
                response.close();
                throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return accounts;
    }

    public static List<Payment> getSubCategoryPayments(String subCategoryName) {

        List<Payment> subCategoryPayments = new ArrayList<>();
        try {
            Client client = ClientBuilder.newBuilder().build();
            providers.add(new JacksonJsonProvider());
            client.register(providers);
            WebTarget target = client.target(REST_URI + "subcategories/" + subCategoryName.trim() + "/payments");
            Response response = target.request(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .get();
            if (response.getStatus() == 200) {
                subCategoryPayments = response.readEntity(new GenericType<List<Payment>>() {
                });
            } else {
                response.close();
                throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return subCategoryPayments;
    }

    public static List<Payment> getAccountPayments(String accountName) {
        logger.info("Posting  {}accounts/{}/payments", REST_URI, accountName);
        List<Payment> accountPayments = new ArrayList<>();
        try {

            Client client = ClientBuilder.newBuilder().build();
            providers.add(new JacksonJsonProvider());
            client.register(providers);
            WebTarget target = client.target(REST_URI + "accounts/" + accountName.trim() + "/payments");
            Response response = target.request(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .get();
            if (response.getStatus() == 200) {
                accountPayments = response.readEntity(new GenericType<List<Payment>>() {
                });
            } else {
                response.close();
                throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return accountPayments;
    }

    public static List<Payment> getPaymentsByKeyword(String keyword) {

        List<Payment> keywordPayments = new ArrayList<>();
        try {

            Client client = ClientBuilder.newBuilder().build();
            providers.add(new JacksonJsonProvider());
            client.register(providers);
            WebTarget target = client.target(REST_URI + "payments;keyword=" + keyword.trim());
            Response response = target.request(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .get();
            if (response.getStatus() == 200) {
                keywordPayments = response.readEntity(new GenericType<List<Payment>>() {
                });
            } else {
                response.close();
                throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return keywordPayments;
    }

    public static List<Payment> getPaymentsByDateRange(Date startDate, Date endDate) {

        List<Payment> dateRangePayments = new ArrayList<>();
        try {
            SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
            Client client = ClientBuilder.newBuilder().build();
            providers.add(new JacksonJsonProvider());
            client.register(providers);
            WebTarget target = client.target(REST_URI + "payments;startdate=" + format.format(startDate).trim() + ";enddate=" + format.format(endDate).trim());
            Response response = target.request(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .get();
            if (response.getStatus() == 200) {
                dateRangePayments = response.readEntity(new GenericType<List<Payment>>() {
                });
            } else {
                response.close();
                throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dateRangePayments;
    }

    public static List<Payment> getPaymentsByExactDate(Date exactDate) {

        List<Payment> exactDatePayments = new ArrayList<>();
        try {
            SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
            Client client = ClientBuilder.newBuilder().build();
            providers.add(new JacksonJsonProvider());
            client.register(providers);
            WebTarget target = client.target(REST_URI + "payments;date=" + format.format(exactDate).trim());
            Response response = target.request(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .get();
            if (response.getStatus() == 200) {
                exactDatePayments = response.readEntity(new GenericType<List<Payment>>() {
                });
            } else {
                response.close();
                throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return exactDatePayments;
    }

    public static List<Payment> getPaymentsByStartDate(Date startDate) {

        List<Payment> startDatePayments = new ArrayList<>();
        try {
            SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
            Client client = ClientBuilder.newBuilder().build();
            providers.add(new JacksonJsonProvider());
            client.register(providers);
            WebTarget target = client.target(REST_URI + "payments;startdate=" + format.format(startDate).trim());
            Response response = target.request(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .get();
            if (response.getStatus() == 200) {
                startDatePayments = response.readEntity(new GenericType<List<Payment>>() {
                });
            } else {
                response.close();
                throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return startDatePayments;
    }

    public static List<Payment> getPaymentsByEndDate(Date endDate) {

        List<Payment> endDatePayments = new ArrayList<>();
        try {
            SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
            Client client = ClientBuilder.newBuilder().build();
            providers.add(new JacksonJsonProvider());
            client.register(providers);
            WebTarget target = client.target(REST_URI + "payments;enddate=" + format.format(endDate).trim());
            Response response = target.request(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .get();
            if (response.getStatus() == 200) {
                endDatePayments = response.readEntity(new GenericType<List<Payment>>() {
                });
            } else {
                response.close();
                throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return endDatePayments;
    }

    public static List<Payment> getPaymentsByDetailValue(String fieldName, String value) {
        logger.info("URL : {}payments;fieldname={};value={}", REST_URI, fieldName, value);
        List<Payment> detailPayments = new ArrayList<>();
        try {
            Client client = ClientBuilder.newBuilder().build();
            providers.add(new JacksonJsonProvider());
            client.register(providers);
            WebTarget target = client.target(REST_URI + "payments;fieldname=" + fieldName.trim() + ";value=" + value.replaceAll("/", "%").trim());
            Response response = target.request(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .get();
            if (response.getStatus() == 200) {
                detailPayments = response.readEntity(new GenericType<List<Payment>>() {
                });
            } else {
                response.close();
                throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return detailPayments;
    }

    public static List<Field> getFields() {

        List<Field> fields = new ArrayList<>();
        try {
            Client client = ClientBuilder.newBuilder().build();
            providers.add(new JacksonJsonProvider());
            client.register(providers);
            WebTarget target = client.target(REST_URI + "fields");
            Response response = target.request(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .get();
            if (response.getStatus() == 200) {
                fields = response.readEntity(new GenericType<List<Field>>() {
                });
            } else {
                response.close();
                throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return fields;
    }

    public static List<Payment> getPaymentsBySearchValues(String keyword, String fieldName, String value, Date startDate, Date endDate) {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        logger.info("URL : {}payments;keyword={};fieldname={};value={};startdate={};enddate={}", REST_URI, keyword, fieldName, value, format.format(startDate), format.format(endDate));
        List<Payment> searchValuePayments = new ArrayList<>();
        try {
            Client client = ClientBuilder.newBuilder().build();
            providers.add(new JacksonJsonProvider());
            client.register(providers);
            if (fieldName != null) {
                target = client.target(REST_URI + "payments;keyword=" + keyword.trim() + ";fieldname=" + fieldName.trim() + ";value=" + value.replaceAll("/", "%").trim() + ";startdate=" + format.format(startDate) + ";enddate=" + format.format(endDate));
            } else {
                target = client.target(REST_URI + "payments;keyword=" + keyword.trim() + ";value=" + value.replaceAll("/", "%").trim() + ";startdate=" + format.format(startDate) + ";enddate=" + format.format(endDate));
            }
            Response response = target.request(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .get();
            if (response.getStatus() == 200) {
                searchValuePayments = response.readEntity(new GenericType<List<Payment>>() {
                });
            } else {
                response.close();
                throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return searchValuePayments;
    }

    public static List<Payment> getPaymentsBySearchValuesWithExactDate(String keyword, String fieldName, String value, Date date) {
        logger.info("URL : {}payments;keyword={};fieldname={};value={};date={}", REST_URI, keyword, fieldName, value, date);
        List<Payment> searchValuePayments = new ArrayList<>();
        try {
            SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
            Client client = ClientBuilder.newBuilder().build();
            providers.add(new JacksonJsonProvider());
            client.register(providers);
            if (fieldName != null && !fieldName.isEmpty()) {
                target = client.target(REST_URI + "payments;keyword=" + keyword.trim() + ";fieldname=" + fieldName.trim() + ";value=" + value.trim().replaceAll("/", "%") + ";date=" + format.format(date));
            } else {
                target = client.target(REST_URI + "payments;keyword=" + keyword.trim() + ";value=" + value.trim().replaceAll("/", "%") + ";date=" + format.format(date) + ";enddate=");
            }
            Response response = target.request(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .get();
            if (response.getStatus() == 200) {
                searchValuePayments = response.readEntity(new GenericType<List<Payment>>() {
                });
            } else {
                response.close();
                throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return searchValuePayments;
    }

    public static List<Payment> getPaymentsByKeywordAndDateValues(String keyword, Date startDate, Date endDate) {
        logger.info("URL : {}payments;keyword={};startdate={};enddate={}", REST_URI, keyword, startDate, endDate);
        List<Payment> searchValuePayments = new ArrayList<>();
        try {
            SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
            Client client = ClientBuilder.newBuilder().build();
            providers.add(new JacksonJsonProvider());
            client.register(providers);
            if (DateUtils.isSameDay(startDate, endDate)) {
                target = client.target(REST_URI + "payments;keyword=" + keyword.trim() + ";date=" + format.format(endDate));
            } else {
                target = client.target(REST_URI + "payments;keyword=" + keyword.trim() + ";startdate=" + format.format(startDate) + ";enddate=" + format.format(endDate));
            }
            Response response = target.request(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .get();
            if (response.getStatus() == 200) {
                searchValuePayments = response.readEntity(new GenericType<List<Payment>>() {
                });
            } else {
                response.close();
                throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return searchValuePayments;
    }

    public static List<Payment> getPaymentsBySearchValuesWithoutDate(String keyword, String fieldname, String value) {
        logger.info("URL : {}payments;keyword={};fieldname={};value={}", REST_URI, keyword, fieldname, value);
        List<Payment> searchValuePayments = new ArrayList<>();
        try {
            SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
            Client client = ClientBuilder.newBuilder().build();
            providers.add(new JacksonJsonProvider());
            client.register(providers);
            target = client.target(REST_URI + "payments;keyword=" + keyword.trim() + ";fieldname=" + fieldname.trim() + ";value=" + value.trim());
            Response response = target.request(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .get();
            if (response.getStatus() == 200) {
                searchValuePayments = response.readEntity(new GenericType<List<Payment>>() {
                });
            } else {
                response.close();
                throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return searchValuePayments;
    }









    public static List<Payment> getAccountPaymentsByKeyword(String accountName,String keyword) {

        List<Payment> keywordPayments = new ArrayList<>();
        try {

            Client client = ClientBuilder.newBuilder().build();
            providers.add(new JacksonJsonProvider());
            client.register(providers);
            WebTarget target = client.target(REST_URI + "accounts/" + accountName.trim() + "/payments;keyword=" + keyword.trim());
            Response response = target.request(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .get();
            if (response.getStatus() == 200) {
                keywordPayments = response.readEntity(new GenericType<List<Payment>>() {
                });
            } else {
                response.close();
                throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return keywordPayments;
    }

    public static List<Payment> getAccountPaymentsByDateRange(String accountName,Date startDate, Date endDate) {

        List<Payment> dateRangePayments = new ArrayList<>();
        try {
            SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
            Client client = ClientBuilder.newBuilder().build();
            providers.add(new JacksonJsonProvider());
            client.register(providers);
            WebTarget target = client.target(REST_URI + "accounts/" + accountName.trim() + "/payments;startdate=" + format.format(startDate).trim() + ";enddate=" + format.format(endDate).trim());
            Response response = target.request(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .get();
            if (response.getStatus() == 200) {
                dateRangePayments = response.readEntity(new GenericType<List<Payment>>() {
                });
            } else {
                response.close();
                throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dateRangePayments;
    }

    public static List<Payment> getAccountPaymentsByExactDate(String accountName,Date exactDate) {

        List<Payment> exactDatePayments = new ArrayList<>();
        try {
            SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
            Client client = ClientBuilder.newBuilder().build();
            providers.add(new JacksonJsonProvider());
            client.register(providers);
            WebTarget target = client.target(REST_URI + "accounts/" + accountName.trim() + "/payments;date=" + format.format(exactDate).trim());
            Response response = target.request(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .get();
            if (response.getStatus() == 200) {
                exactDatePayments = response.readEntity(new GenericType<List<Payment>>() {
                });
            } else {
                response.close();
                throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return exactDatePayments;
    }

    public static List<Payment> getAccountPaymentsByStartDate(String accountName,Date startDate) {

        List<Payment> startDatePayments = new ArrayList<>();
        try {
            SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
            Client client = ClientBuilder.newBuilder().build();
            providers.add(new JacksonJsonProvider());
            client.register(providers);
            WebTarget target = client.target(REST_URI + "accounts/" + accountName.trim() + "/payments;startdate=" + format.format(startDate).trim());
            Response response = target.request(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .get();
            if (response.getStatus() == 200) {
                startDatePayments = response.readEntity(new GenericType<List<Payment>>() {
                });
            } else {
                response.close();
                throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return startDatePayments;
    }

    public static List<Payment> getAccountPaymentsByEndDate(String accountName,Date endDate) {

        List<Payment> endDatePayments = new ArrayList<>();
        try {
            SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
            Client client = ClientBuilder.newBuilder().build();
            providers.add(new JacksonJsonProvider());
            client.register(providers);
            WebTarget target = client.target(REST_URI + "accounts/" + accountName.trim() + "/payments;enddate=" + format.format(endDate).trim());
            Response response = target.request(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .get();
            if (response.getStatus() == 200) {
                endDatePayments = response.readEntity(new GenericType<List<Payment>>() {
                });
            } else {
                response.close();
                throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return endDatePayments;
    }

    public static List<Payment> getAccountPaymentsByDetailValue(String accountName,String fieldName, String value) {
        logger.info("URL : {}/accounts/{}/payments;fieldname={};value={}", REST_URI, accountName,fieldName, value);
        List<Payment> detailPayments = new ArrayList<>();
        try {
            Client client = ClientBuilder.newBuilder().build();
            providers.add(new JacksonJsonProvider());
            client.register(providers);
            WebTarget target = client.target(REST_URI +"accounts/" + accountName.trim() + "/payments;fieldname=" + fieldName.trim() + ";value=" + value.replaceAll("/", "%").trim());
            Response response = target.request(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .get();
            if (response.getStatus() == 200) {
                detailPayments = response.readEntity(new GenericType<List<Payment>>() {
                });
            } else {
                response.close();
                throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return detailPayments;
    }
    public static List<Payment> getAccountPaymentsBySearchValues(String accountName,String keyword, String fieldName, String value, Date startDate, Date endDate) {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        logger.info("URL : {}accounts/{}/payments;keyword={};fieldname={};value={};startdate={};enddate={}", REST_URI,accountName, keyword, fieldName, value, format.format(startDate), format.format(endDate));
        List<Payment> searchValuePayments = new ArrayList<>();
        try {
            Client client = ClientBuilder.newBuilder().build();
            providers.add(new JacksonJsonProvider());
            client.register(providers);
            if (fieldName != null) {
                target = client.target(REST_URI +"accounts/" + accountName.trim() + "/payments;keyword=" + keyword.trim() + ";fieldname=" + fieldName.trim() + ";value=" + value.replaceAll("/", "%").trim() + ";startdate=" + format.format(startDate) + ";enddate=" + format.format(endDate));
            } else {
                target = client.target(REST_URI +"accounts/" + accountName.trim() + "/payments;keyword=" + keyword.trim() + ";value=" + value.replaceAll("/", "%").trim() + ";startdate=" + format.format(startDate) + ";enddate=" + format.format(endDate));
            }
            Response response = target.request(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .get();
            if (response.getStatus() == 200) {
                searchValuePayments = response.readEntity(new GenericType<List<Payment>>() {
                });
            } else {
                response.close();
                throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return searchValuePayments;
    }

    public static List<Payment> getAccountPaymentsBySearchValuesWithExactDate(String accountName,String keyword, String fieldName, String value, Date date) {
        logger.info("URL : {}accounts/{}/payments;keyword={};fieldname={};value={};date={}", REST_URI,accountName, keyword, fieldName, value, date);
        List<Payment> searchValuePayments = new ArrayList<>();
        try {
            SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
            Client client = ClientBuilder.newBuilder().build();
            providers.add(new JacksonJsonProvider());
            client.register(providers);
            if (fieldName != null && !fieldName.isEmpty()) {
                target = client.target(REST_URI +"accounts/" + accountName.trim() + "/payments;keyword=" + keyword.trim() + ";fieldname=" + fieldName.trim() + ";value=" + value.trim().replaceAll("/", "%") + ";date=" + format.format(date));
            } else {
                target = client.target(REST_URI +"accounts/" + accountName.trim() + "/payments;keyword=" + keyword.trim() + ";value=" + value.trim().replaceAll("/", "%") + ";date=" + format.format(date) + ";enddate=");
            }
            Response response = target.request(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .get();
            if (response.getStatus() == 200) {
                searchValuePayments = response.readEntity(new GenericType<List<Payment>>() {
                });
            } else {
                response.close();
                throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return searchValuePayments;
    }

    public static List<Payment> getAccountPaymentsByKeywordAndDateValues(String accountName, String keyword, Date startDate, Date endDate) {
        logger.info("URL : {}accounts/{}/payments;keyword={};startdate={};enddate={}", REST_URI,accountName,keyword, startDate, endDate);
        List<Payment> searchValuePayments = new ArrayList<>();
        try {
            SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
            Client client = ClientBuilder.newBuilder().build();
            providers.add(new JacksonJsonProvider());
            client.register(providers);
            if (DateUtils.isSameDay(startDate, endDate)) {
                target = client.target(REST_URI +"accounts/" + accountName.trim() + "/payments;keyword=" + keyword.trim() + ";date=" + format.format(endDate));
            } else {
                target = client.target(REST_URI +"accounts/" + accountName.trim() + "/payments;keyword=" + keyword.trim() + ";startdate=" + format.format(startDate) + ";enddate=" + format.format(endDate));
            }
            Response response = target.request(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .get();
            if (response.getStatus() == 200) {
                searchValuePayments = response.readEntity(new GenericType<List<Payment>>() {
                });
            } else {
                response.close();
                throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return searchValuePayments;
    }
    public static List<Payment> getAccountPaymentsBySearchValuesWithoutDate(String accountName, String keyword, String fieldname, String value) {
        logger.info("URL : {}accounts/{}/payments;keyword={};fieldname={};value={}", REST_URI,accountName, keyword, fieldname, value);
        List<Payment> searchValuePayments = new ArrayList<>();
        try {
            SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
            Client client = ClientBuilder.newBuilder().build();
            providers.add(new JacksonJsonProvider());
            client.register(providers);
            target = client.target(REST_URI + "accounts/" + accountName.trim() + "/payments;keyword=" + keyword.trim() + ";fieldname=" + fieldname.trim() + ";value=" + value.trim());
            Response response = target.request(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .get();
            if (response.getStatus() == 200) {
                searchValuePayments = response.readEntity(new GenericType<List<Payment>>() {
                });
            } else {
                response.close();
                throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return searchValuePayments;
    }









    public static List<Payment> getSubCategoryPaymentsByKeyword(String subCategoryName,String keyword) {

        List<Payment> keywordPayments = new ArrayList<>();
        try {

            Client client = ClientBuilder.newBuilder().build();
            providers.add(new JacksonJsonProvider());
            client.register(providers);
            WebTarget target = client.target(REST_URI + "subcategories/" + subCategoryName.trim() + "/payments;keyword=" + keyword.trim());
            Response response = target.request(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .get();
            if (response.getStatus() == 200) {
                keywordPayments = response.readEntity(new GenericType<List<Payment>>() {
                });
            } else {
                response.close();
                throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return keywordPayments;
    }

    public static List<Payment> getSubCategoryPaymentsByDateRange(String subCategoryName,Date startDate, Date endDate) {

        List<Payment> dateRangePayments = new ArrayList<>();
        try {
            SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
            Client client = ClientBuilder.newBuilder().build();
            providers.add(new JacksonJsonProvider());
            client.register(providers);
            WebTarget target = client.target(REST_URI + "subcategories/" + subCategoryName.trim() + "/payments;startdate=" + format.format(startDate).trim() + ";enddate=" + format.format(endDate).trim());
            Response response = target.request(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .get();
            if (response.getStatus() == 200) {
                dateRangePayments = response.readEntity(new GenericType<List<Payment>>() {
                });
            } else {
                response.close();
                throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dateRangePayments;
    }

    public static List<Payment> getSubCategoryPaymentsByExactDate(String subCategoryName,Date exactDate) {

        List<Payment> exactDatePayments = new ArrayList<>();
        try {
            SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
            Client client = ClientBuilder.newBuilder().build();
            providers.add(new JacksonJsonProvider());
            client.register(providers);
            WebTarget target = client.target(REST_URI + "subcategories/" + subCategoryName.trim() + "/payments;date=" + format.format(exactDate).trim());
            Response response = target.request(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .get();
            if (response.getStatus() == 200) {
                exactDatePayments = response.readEntity(new GenericType<List<Payment>>() {
                });
            } else {
                response.close();
                throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return exactDatePayments;
    }

    public static List<Payment> getSubCategoryPaymentsByStartDate(String subCategoryName,Date startDate) {

        List<Payment> startDatePayments = new ArrayList<>();
        try {
            SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
            Client client = ClientBuilder.newBuilder().build();
            providers.add(new JacksonJsonProvider());
            client.register(providers);
            WebTarget target = client.target(REST_URI + "subcategories/" + subCategoryName.trim() + "/payments;startdate=" + format.format(startDate).trim());
            Response response = target.request(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .get();
            if (response.getStatus() == 200) {
                startDatePayments = response.readEntity(new GenericType<List<Payment>>() {
                });
            } else {
                response.close();
                throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return startDatePayments;
    }

    public static List<Payment> getSubCategoryPaymentsByEndDate(String subCategoryName,Date endDate) {

        List<Payment> endDatePayments = new ArrayList<>();
        try {
            SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
            Client client = ClientBuilder.newBuilder().build();
            providers.add(new JacksonJsonProvider());
            client.register(providers);
            WebTarget target = client.target(REST_URI + "subcategories/" + subCategoryName.trim() + "/payments;enddate=" + format.format(endDate).trim());
            Response response = target.request(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .get();
            if (response.getStatus() == 200) {
                endDatePayments = response.readEntity(new GenericType<List<Payment>>() {
                });
            } else {
                response.close();
                throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return endDatePayments;
    }

    public static List<Payment> getSubCategoryPaymentsByDetailValue(String subCategoryName,String fieldName, String value) {
        logger.info("URL : {}/subcategories/{}/payments;fieldname={};value={}", REST_URI, subCategoryName,fieldName, value);
        List<Payment> detailPayments = new ArrayList<>();
        try {
            Client client = ClientBuilder.newBuilder().build();
            providers.add(new JacksonJsonProvider());
            client.register(providers);
            WebTarget target = client.target(REST_URI +"subcategories/" + subCategoryName.trim() + "/payments;fieldname=" + fieldName.trim() + ";value=" + value.replaceAll("/", "%").trim());
            Response response = target.request(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .get();
            if (response.getStatus() == 200) {
                detailPayments = response.readEntity(new GenericType<List<Payment>>() {
                });
            } else {
                response.close();
                throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return detailPayments;
    }
    public static List<Payment> getSubCategoryPaymentsBySearchValues(String subCategoryName,String keyword, String fieldName, String value, Date startDate, Date endDate) {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        logger.info("URL : {}subcategories/{}/payments;keyword={};fieldname={};value={};startdate={};enddate={}", REST_URI,subCategoryName, keyword, fieldName, value, format.format(startDate), format.format(endDate));
        List<Payment> searchValuePayments = new ArrayList<>();
        try {
            Client client = ClientBuilder.newBuilder().build();
            providers.add(new JacksonJsonProvider());
            client.register(providers);
            if (fieldName != null) {
                target = client.target(REST_URI +"subcategories/" + subCategoryName.trim() + "/payments;keyword=" + keyword.trim() + ";fieldname=" + fieldName.trim() + ";value=" + value.replaceAll("/", "%").trim() + ";startdate=" + format.format(startDate) + ";enddate=" + format.format(endDate));
            } else {
                target = client.target(REST_URI +"subcategories/" + subCategoryName.trim() + "/payments;keyword=" + keyword.trim() + ";value=" + value.replaceAll("/", "%").trim() + ";startdate=" + format.format(startDate) + ";enddate=" + format.format(endDate));
            }
            Response response = target.request(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .get();
            if (response.getStatus() == 200) {
                searchValuePayments = response.readEntity(new GenericType<List<Payment>>() {
                });
            } else {
                response.close();
                throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return searchValuePayments;
    }

    public static List<Payment> getSubCategoryPaymentsBySearchValuesWithExactDate(String subCategoryName,String keyword, String fieldName, String value, Date date) {
        logger.info("URL : {}subcategories/{}/payments;keyword={};fieldname={};value={};date={}", REST_URI,subCategoryName, keyword, fieldName, value, date);
        List<Payment> searchValuePayments = new ArrayList<>();
        try {
            SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
            Client client = ClientBuilder.newBuilder().build();
            providers.add(new JacksonJsonProvider());
            client.register(providers);
            if (fieldName != null && !fieldName.isEmpty()) {
                target = client.target(REST_URI +"subcategories/" + subCategoryName.trim() + "/payments;keyword=" + keyword.trim() + ";fieldname=" + fieldName.trim() + ";value=" + value.trim().replaceAll("/", "%") + ";date=" + format.format(date));
            } else {
                target = client.target(REST_URI +"subcategories/" + subCategoryName.trim() + "/payments;keyword=" + keyword.trim() + ";value=" + value.trim().replaceAll("/", "%") + ";date=" + format.format(date) + ";enddate=");
            }
            Response response = target.request(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .get();
            if (response.getStatus() == 200) {
                searchValuePayments = response.readEntity(new GenericType<List<Payment>>() {
                });
            } else {
                response.close();
                throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return searchValuePayments;
    }

    public static List<Payment> getSubCategoryPaymentsByKeywordAndDateValues(String subCategoryName, String keyword, Date startDate, Date endDate) {
        logger.info("URL : {}subcategories/{}/payments;keyword={};startdate={};enddate={}", REST_URI,subCategoryName,keyword, startDate, endDate);
        List<Payment> searchValuePayments = new ArrayList<>();
        try {
            SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
            Client client = ClientBuilder.newBuilder().build();
            providers.add(new JacksonJsonProvider());
            client.register(providers);
            if (DateUtils.isSameDay(startDate, endDate)) {
                target = client.target(REST_URI +"subcategories/" + subCategoryName.trim() + "/payments;keyword=" + keyword.trim() + ";date=" + format.format(endDate));
            } else {
                target = client.target(REST_URI +"subcategories/" + subCategoryName.trim() + "/payments;keyword=" + keyword.trim() + ";startdate=" + format.format(startDate) + ";enddate=" + format.format(endDate));
            }
            Response response = target.request(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .get();
            if (response.getStatus() == 200) {
                searchValuePayments = response.readEntity(new GenericType<List<Payment>>() {
                });
            } else {
                response.close();
                throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return searchValuePayments;
    }
    public static List<Payment> getSubCategoryPaymentsBySearchValuesWithoutDate(String subCategoryName, String keyword, String fieldname, String value) {
        logger.info("URL : {}subcategories/{}/payments;keyword={};fieldname={};value={}", REST_URI,subCategoryName, keyword, fieldname, value);
        List<Payment> searchValuePayments = new ArrayList<>();
        try {
            SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
            Client client = ClientBuilder.newBuilder().build();
            providers.add(new JacksonJsonProvider());
            client.register(providers);
            target = client.target(REST_URI + "subcategories/" + subCategoryName.trim() + "/payments;keyword=" + keyword.trim() + ";fieldname=" + fieldname.trim() + ";value=" + value.trim());
            Response response = target.request(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .get();
            if (response.getStatus() == 200) {
                searchValuePayments = response.readEntity(new GenericType<List<Payment>>() {
                });
            } else {
                response.close();
                throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return searchValuePayments;
    }

    public static List<Payment> getCategoryPaymentsByKeyword(String categoryName,String keyword) {

        List<Payment> keywordPayments = new ArrayList<>();
        try {

            Client client = ClientBuilder.newBuilder().build();
            providers.add(new JacksonJsonProvider());
            client.register(providers);
            WebTarget target = client.target(REST_URI + "categories/" + categoryName.trim() + "/payments;keyword=" + keyword.trim());
            Response response = target.request(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .get();
            if (response.getStatus() == 200) {
                keywordPayments = response.readEntity(new GenericType<List<Payment>>() {
                });
            } else {
                response.close();
                throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return keywordPayments;
    }

    public static List<Payment> getCategoryPaymentsByDateRange(String categoryName,Date startDate, Date endDate) {

        List<Payment> dateRangePayments = new ArrayList<>();
        try {
            SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
            Client client = ClientBuilder.newBuilder().build();
            providers.add(new JacksonJsonProvider());
            client.register(providers);
            WebTarget target = client.target(REST_URI + "categories/" + categoryName.trim() + "/payments;startdate=" + format.format(startDate).trim() + ";enddate=" + format.format(endDate).trim());
            Response response = target.request(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .get();
            if (response.getStatus() == 200) {
                dateRangePayments = response.readEntity(new GenericType<List<Payment>>() {
                });
            } else {
                response.close();
                throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dateRangePayments;
    }

    public static List<Payment> getCategoryPaymentsByExactDate(String categoryName,Date exactDate) {

        List<Payment> exactDatePayments = new ArrayList<>();
        try {
            SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
            Client client = ClientBuilder.newBuilder().build();
            providers.add(new JacksonJsonProvider());
            client.register(providers);
            WebTarget target = client.target(REST_URI + "categories/" + categoryName.trim() + "/payments;date=" + format.format(exactDate).trim());
            Response response = target.request(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .get();
            if (response.getStatus() == 200) {
                exactDatePayments = response.readEntity(new GenericType<List<Payment>>() {
                });
            } else {
                response.close();
                throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return exactDatePayments;
    }

    public static List<Payment> getCategoryPaymentsByStartDate(String categoryName,Date startDate) {

        List<Payment> startDatePayments = new ArrayList<>();
        try {
            SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
            Client client = ClientBuilder.newBuilder().build();
            providers.add(new JacksonJsonProvider());
            client.register(providers);
            WebTarget target = client.target(REST_URI + "categories/" + categoryName.trim() + "/payments;startdate=" + format.format(startDate).trim());
            Response response = target.request(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .get();
            if (response.getStatus() == 200) {
                startDatePayments = response.readEntity(new GenericType<List<Payment>>() {
                });
            } else {
                response.close();
                throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return startDatePayments;
    }

    public static List<Payment> getCategoryPaymentsByEndDate(String categoryName,Date endDate) {

        List<Payment> endDatePayments = new ArrayList<>();
        try {
            SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
            Client client = ClientBuilder.newBuilder().build();
            providers.add(new JacksonJsonProvider());
            client.register(providers);
            WebTarget target = client.target(REST_URI + "categories/" + categoryName.trim() + "/payments;enddate=" + format.format(endDate).trim());
            Response response = target.request(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .get();
            if (response.getStatus() == 200) {
                endDatePayments = response.readEntity(new GenericType<List<Payment>>() {
                });
            } else {
                response.close();
                throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return endDatePayments;
    }

    public static List<Payment> getCategoryPaymentsByDetailValue(String categoryName,String fieldName, String value) {
        logger.info("URL : {}/categories/{}/payments;fieldname={};value={}", REST_URI, categoryName,fieldName, value);
        List<Payment> detailPayments = new ArrayList<>();
        try {
            Client client = ClientBuilder.newBuilder().build();
            providers.add(new JacksonJsonProvider());
            client.register(providers);
            WebTarget target = client.target(REST_URI +"categories/" + categoryName.trim() + "/payments;fieldname=" + fieldName.trim() + ";value=" + value.replaceAll("/", "%").trim());
            Response response = target.request(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .get();
            if (response.getStatus() == 200) {
                detailPayments = response.readEntity(new GenericType<List<Payment>>() {
                });
            } else {
                response.close();
                throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return detailPayments;
    }
    public static List<Payment> getCategoryPaymentsBySearchValues(String categoryName,String keyword, String fieldName, String value, Date startDate, Date endDate) {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        logger.info("URL : {}categories/{}/payments;keyword={};fieldname={};value={};startdate={};enddate={}", REST_URI,categoryName, keyword, fieldName, value, format.format(startDate), format.format(endDate));
        List<Payment> searchValuePayments = new ArrayList<>();
        try {
            Client client = ClientBuilder.newBuilder().build();
            providers.add(new JacksonJsonProvider());
            client.register(providers);
            if (fieldName != null) {
                target = client.target(REST_URI +"categories/" + categoryName.trim() + "/payments;keyword=" + keyword.trim() + ";fieldname=" + fieldName.trim() + ";value=" + value.replaceAll("/", "%").trim() + ";startdate=" + format.format(startDate) + ";enddate=" + format.format(endDate));
            } else {
                target = client.target(REST_URI +"categories/" + categoryName.trim() + "/payments;keyword=" + keyword.trim() + ";value=" + value.replaceAll("/", "%").trim() + ";startdate=" + format.format(startDate) + ";enddate=" + format.format(endDate));
            }
            Response response = target.request(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .get();
            if (response.getStatus() == 200) {
                searchValuePayments = response.readEntity(new GenericType<List<Payment>>() {
                });
            } else {
                response.close();
                throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return searchValuePayments;
    }

    public static List<Payment> getCategoryPaymentsBySearchValuesWithExactDate(String categoryName,String keyword, String fieldName, String value, Date date) {
        logger.info("URL : {}categories/{}/payments;keyword={};fieldname={};value={};date={}", REST_URI,categoryName, keyword, fieldName, value, date);
        List<Payment> searchValuePayments = new ArrayList<>();
        try {
            SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
            Client client = ClientBuilder.newBuilder().build();
            providers.add(new JacksonJsonProvider());
            client.register(providers);
            if (fieldName != null && !fieldName.isEmpty()) {
                target = client.target(REST_URI +"categories/" + categoryName.trim() + "/payments;keyword=" + keyword.trim() + ";fieldname=" + fieldName.trim() + ";value=" + value.trim().replaceAll("/", "%") + ";date=" + format.format(date));
            } else {
                target = client.target(REST_URI +"categories/" + categoryName.trim() + "/payments;keyword=" + keyword.trim() + ";value=" + value.trim().replaceAll("/", "%") + ";date=" + format.format(date) + ";enddate=");
            }
            Response response = target.request(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .get();
            if (response.getStatus() == 200) {
                searchValuePayments = response.readEntity(new GenericType<List<Payment>>() {
                });
            } else {
                response.close();
                throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return searchValuePayments;
    }

    public static List<Payment> getCategoryPaymentsByKeywordAndDateValues(String categoryName, String keyword, Date startDate, Date endDate) {
        logger.info("URL : {}categories/{}/payments;keyword={};startdate={};enddate={}", REST_URI,categoryName,keyword, startDate, endDate);
        List<Payment> searchValuePayments = new ArrayList<>();
        try {
            SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
            Client client = ClientBuilder.newBuilder().build();
            providers.add(new JacksonJsonProvider());
            client.register(providers);
            if (DateUtils.isSameDay(startDate, endDate)) {
                target = client.target(REST_URI +"categories/" + categoryName.trim() + "/payments;keyword=" + keyword.trim() + ";date=" + format.format(endDate));
            } else {
                target = client.target(REST_URI +"categories/" + categoryName.trim() + "/payments;keyword=" + keyword.trim() + ";startdate=" + format.format(startDate) + ";enddate=" + format.format(endDate));
            }
            Response response = target.request(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .get();
            if (response.getStatus() == 200) {
                searchValuePayments = response.readEntity(new GenericType<List<Payment>>() {
                });
            } else {
                response.close();
                throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return searchValuePayments;
    }
    public static List<Payment> getCategoryPaymentsBySearchValuesWithoutDate(String categoryName, String keyword, String fieldname, String value) {
        logger.info("URL : {}categories/{}/payments;keyword={};fieldname={};value={}", REST_URI,categoryName, keyword, fieldname, value);
        List<Payment> searchValuePayments = new ArrayList<>();
        try {
            SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
            Client client = ClientBuilder.newBuilder().build();
            providers.add(new JacksonJsonProvider());
            client.register(providers);
            target = client.target(REST_URI + "categories/" + categoryName.trim() + "/payments;keyword=" + keyword.trim() + ";fieldname=" + fieldname.trim() + ";value=" + value.trim());
            Response response = target.request(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .get();
            if (response.getStatus() == 200) {
                searchValuePayments = response.readEntity(new GenericType<List<Payment>>() {
                });
            } else {
                response.close();
                throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return searchValuePayments;
    }
}
