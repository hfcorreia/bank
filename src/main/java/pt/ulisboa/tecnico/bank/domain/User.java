package pt.ulisboa.tecnico.bank.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Created with IntelliJ IDEA.
 * Author: Aliaksandra Sankova
 * Date: 11/29/13
 * Time: 2:34 AM
 */
@Entity
@Table(name = "user")
public class User extends DomainObject implements UserDetails {
    @NotNull
    @Column(name = "username")
    private String username;

    @NotNull
    @Column(name = "password")
    private String password;

    @NotNull
    @Column(name = "salt")
    private String salt;

    @NotNull
    @Column(name = "iterations")
    private String iterations;

    @NotNull
    @Column(name = "matrix", length=1000)
    private String matrix;
    
    @Column(name = "email")
    private String email;

    @Column(name = "enabled")
    private Boolean enabled = Boolean.TRUE;

    @Column(name = "account_non_expired")
    private Boolean accountNonExpired = Boolean.TRUE;

    @Column(name = "credentials_non_expired")
    private Boolean credentialsNonExpired = Boolean.TRUE;

    @Column(name = "account_non_locked")
    private Boolean accountNonLocked = Boolean.TRUE;

    @Size(max = 30)
    @Column(name = "first_name")
    private String firstName;

    @Size(max = 30)
    @Column(name = "last_name")
    private String lastName;

    @Size(max = 15)
    @Column(name = "phone")
    private String phone;

    @NotNull
    @Column(name = "role")
    private Role role;

    @OneToMany(targetEntity= Account.class)
    @JoinColumn(name="userAccounts", referencedColumnName="id")
    private List<Account> accounts;

    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public Collection<GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
        auths.add((GrantedAuthority)getRole());
        return auths;
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

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getIterations() {
        return iterations;
    }

    public void setIterations(String iterations) {
        this.iterations = iterations;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean getAccountNonExpired() {
        return accountNonExpired;
    }

    public void setAccountNonExpired(Boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    public Boolean getCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    public void setCredentialsNonExpired(Boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public Boolean getAccountNonLocked() {
        return accountNonLocked;
    }

    public void setAccountNonLocked(Boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

	public String getMatrix() {
		return matrix;
	}

	public void setMatrix(String matrix) {
		this.matrix = matrix;
	}
}
