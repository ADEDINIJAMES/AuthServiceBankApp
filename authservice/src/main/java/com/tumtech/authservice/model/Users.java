package com.tumtech.authservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tumtech.authservice.enums.Roles;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@Data
@Getter
@Setter
public class Users implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private Roles userRoles;
@CreationTimestamp
    private LocalDateTime createdAt;
@UpdateTimestamp
    private LocalDateTime updated;
@OneToMany
    private List<Account> accountNo;
    private String firstName;
    @Column(unique = true)
    private String email;
    private String lastName;
    private String otherName;
    private String phone;
    private LocalDate dob;
    private String password;
    @Transient
    private String confirmPassword;
    private String gender;
    @OneToOne
    private FileData profilepic;
    @OneToOne
    private ImageData profilepics;
    private Boolean isEnabled = false;
    private String stateOfOrigin;

    public Users() {
    }

    public Users (String firstName, String stateOfOrigin,LocalDate dob, String lastName, String otherName, String email, String phone, String password, Roles userRoles, Boolean isEnabled){
       this.firstName= firstName;
       this.lastName = lastName;
       this.email = email;
       this.dob = dob;
       this.password =password;
       this.isEnabled = isEnabled;
       this.phone = phone;
       this.otherName = otherName;
       this.stateOfOrigin =stateOfOrigin;


    }

    public Users(Long id, String stateOfOrigin,Roles userRoles,String otherName, LocalDateTime createdAt, LocalDateTime updated,String firstName, String email, String lastName, String phone, LocalDate dob, String password, String confirmPassword, String gender, FileData profilepic, ImageData profilepics, Boolean isEnabled) {
        this.id = id;
        this.userRoles = userRoles;
        this.stateOfOrigin =stateOfOrigin;
        this.createdAt = createdAt;
        this.updated = updated;
//        this.accountNo = accountNo;
        this.firstName = firstName;
        this.email = email;
        this.lastName = lastName;
        this.phone = phone;
        this.dob = dob;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.gender = gender;
        this.profilepic = profilepic;
        this.profilepics = profilepics;
        this.otherName = otherName;
        this.isEnabled = isEnabled;
    }

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>(Collections.singleton(new SimpleGrantedAuthority(this.userRoles.name())));
    }
    @JsonIgnore
    @Override
    public String getPassword() {
        return this.password;
    }
    @JsonIgnore
    @Override
    public String getUsername() {
        return this.email;
    }
    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return this.isEnabled;
    }

    public Long getId() {
        return id;
    }

    public Roles getUserRoles() {
        return userRoles;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdated() {
        return updated;
    }

//    public Account getAccountNo() {
//        return accountNo;
//    }

    public String getFirstName() {
        return firstName;
    }

    public String getOtherName() {
        return otherName;
    }
    public String getStateOfOrigin () {return stateOfOrigin;}

    public String getEmail() {
        return email;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhone() {
        return phone;
    }

    public LocalDate getDob() {
        return dob;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public String getGender() {
        return gender;
    }

    public FileData getProfilepic() {
        return profilepic;
    }

    public ImageData getProfilepics() {
        return profilepics;
    }

    public Boolean getEnabled() {
        return isEnabled;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserRoles(Roles userRoles) {
        this.userRoles = userRoles;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdated(LocalDateTime updated) {
        this.updated = updated;
    }
    public void setOtherName (String otherName){this.otherName=otherName;}
    public void setStateOfOrigin (String stateOfOrigin){this.stateOfOrigin = stateOfOrigin;}

//    public void setAccountNo(Account accountNo) {
//        this.accountNo = accountNo;
//    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setProfilepic(FileData profilepic) {
        this.profilepic = profilepic;
    }

    public void setProfileName(ImageData profilepics) {
        this.profilepics= profilepics;
    }

    public void setEnabled(Boolean enabled) {
        isEnabled = enabled;
    }
}
