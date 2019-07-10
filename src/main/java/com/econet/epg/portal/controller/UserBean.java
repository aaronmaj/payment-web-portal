/*
 * The log in controller for the log in aspect for the web site
 */
package com.econet.epg.portal.controller;


import com.econet.epg.portal.model.Login;
import com.econet.epg.portal.util.SessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import javax.enterprise.context.*;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

@Named
@SessionScoped
public class UserBean implements Serializable {
    private static final Logger logger = LoggerFactory.getLogger(UserBean.class);

    @PersistenceContext(name = "epg-pu", unitName = "epg-pu",type = PersistenceContextType.TRANSACTION)
    private EntityManager entityManager;

    private static final long serialVersionUID = 1L;
    private Login currentLogin = new Login();

    public Login getCurrentLogin() {
        return currentLogin;
    }

    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String odlPassword;
    private String newPassword;
    private String confirmPassword;

    public void grantPermission() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
        Object userLoggedIn = session.getAttribute("user");
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        if (userLoggedIn == null) {
            try {
                context.redirect("login.xhtml?faces-redirect=true");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public String validateLogin() {

        username = currentLogin.getUsername();
        password = currentLogin.getPassword();
        try {
            Login login = entityManager.createQuery("SELECT login FROM Login  login WHERE login.username=:userName and login.password=:password", Login.class)
                    .setParameter("userName", currentLogin.getUsername().trim())
                    .setParameter("password", currentLogin.getPassword().trim())
                    .getSingleResult();


        if (username.equals(login.getUsername()) && password.equals(login.getPassword())) {
            currentLogin = login;
            HttpSession session = SessionUtils.getSession();
            session.setAttribute("user", "user");

            return "index?faces-redirect=true";
        } else {
            String message = "Oops! Something is wrong with your username and password";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
            return "login";
        }
        }catch (Exception e){
            String message = "Oops! Something is wrong with your username and password";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
            return "login";
        }
    }

    /**
     * Will log the user into the web site using container managed security
     *
     * @return Will take the user to the Welcome page or will return the user
     * back to the log in page with an error message
     */
    public String login() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        try {
            //If the log in is successful..
            request.login(currentLogin.getUsername(), currentLogin.getPassword());
            return "welcome?faces-redirect=true";
        } catch (ServletException e) {
            //else...
            displayMessage("Username or password is incorrect!");
            return null;
        }
    }

    /**
     * Displays a message on a chosen web page
     *
     * @param message The message that is to be displayed to the user
     */
    private void displayMessage(String message) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(message));
    }

    /**
     * Logs the user out of the portal
     *
     * @return returns the user to the login page
     **/
    public String logout() {
        HttpSession session = SessionUtils.getSession();
        session.invalidate();
        return "login.xhtml?faces-redirected=true";
    }

    public String showProfile() {
        HttpSession session = SessionUtils.getSession();
        if (session != null || SessionUtils.getRequest().isRequestedSessionIdValid()) {
            return "profile.xhtml?faces-redirected=true";

        } else {
            return "login.xhtml?faces-redirected=true";
        }
    }
    @Transactional
    public String changePassword(){
        logger.info("Old Password : {}, current login : {}, new password:  {}, confirm password : {}  ",odlPassword,currentLogin.getPassword(),newPassword,confirmPassword);
        if(odlPassword.trim().equals(currentLogin.getPassword().trim()) && newPassword.trim().equals(confirmPassword.trim())){
            currentLogin.setPassword(newPassword);
            entityManager.merge(currentLogin);
            entityManager.flush();
            return "index?faces-redirect=true";
        } else {
            return "profile.xhtml?faces-redirected=true";
        }

    }
    public void setCurrentLogin(Login currentLogin) {
        this.currentLogin = currentLogin;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getOdlPassword() {
        return odlPassword;
    }

    public void setOdlPassword(String odlPassword) {
        this.odlPassword = odlPassword;
    }
}
