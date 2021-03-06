package com.rent.RentApp.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

import com.rent.RentApp.forms.UserForm;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
public class Users implements UserDetails {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", updatable = false, unique = true, nullable = false)
  private Long id;
  @Column(name = "name", nullable = false)
  private String name;
  @Column(name = "image", nullable = true)
  private byte[] image;
  @Column(name = "email", nullable = false, unique = true)
  private String email;
  @Column(name = "password", nullable = false)
  private String password;
  @Column(name = "admin", nullable = false)
  private boolean admin = false;
  @Column(name = "created_at")
  @CreatedDate
  private Date created_at = new Date();
  @Column(name = "updated_at")
  @LastModifiedDate
  private Date updated_at = new Date();

  @OneToMany(mappedBy = "client")
  private List<Rentals> rentals = new ArrayList<Rentals>();

  @ManyToMany(fetch = FetchType.EAGER)
  private List<Profiles> profiles = new ArrayList<>();

  protected Users() {
  };

  public Users(UserForm userForm) {
    this.name = userForm.getName();
    this.email = userForm.getEmail();
    this.password = userForm.getPassword();
  }

  public Users(String email, String password) {
    this.email = email;
    this.password = password;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public byte[] getImage() {
    return image;
  }

  public void setImage(byte[] image) {
    this.image = image;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public boolean isAdmin() {
    return admin;
  }

  public void setAdmin(boolean admin) {
    this.admin = admin;
  }

  public Date getCreated_at() {
    return created_at;
  }

  public void setCreated_at(Date created_at) {
    this.created_at = created_at;
  }

  public Date getUpdated_at() {
    return updated_at;
  }

  @PreUpdate
  public void setUpdated_at() {
    this.updated_at = new Date();
  }

  @PreRemove
  public void preRemove() {
    for (Rentals rental : rentals) {
      rental.setClient(null);
    }
  }

  public void addRental(Rentals rental) {
    this.rentals.add(rental);

  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return this.profiles;
  }

  @Override
  public String getUsername() {
    return this.email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

}
