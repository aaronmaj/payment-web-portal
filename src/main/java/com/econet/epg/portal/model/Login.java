package com.econet.epg.portal.model;

import java.io.*;
import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.validation.constraints.*;

@Entity
@Table(name="logins")
public class Login implements Serializable {
    @Id
    private String username;
    private String password;
    private LoginType loginType;
    private String email;
    private String firstName;
    private String lastName;
    /**
     * Only allows letters and numbers for a username
     *
     * @return a username
     */
    @Id
    @NotNull
    @Pattern(regexp = "[a-zA-Z_0-9]*", message = "Username uses incorrect characters")
    @Size(min = 1, message = "This field cannot be blank")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Only allows letters and numbers for a password
     *
     * @return a password
     */
    @NotNull
    @Pattern(regexp = "^[a-zA-Z0-9!@#$&()\\\\-`.+,/\\\"]*$", message = "Password uses incorrect character")
    @Size(min = 1, message = "This field cannot be blank")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * An enum value that is used for the account type
     *
     * @return an account type
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "login_type")
    public LoginType getLoginType() {
        return loginType;
    }

    public void setLoginType(LoginType loginType) {
        this.loginType = loginType;
    }

    /**
     * The email of the user. Must only contain a letter/numbers, and must
     * consist of an '@' and '.com' part.
     *
     * @return an email
     */
    @NotNull
    @Pattern(regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
    @Size(min = 1)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    @Column(name ="first_name")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    @Column(name ="last_name")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
