package com.liaw.dev.Conference.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.liaw.dev.Conference.enums.Role;
import com.liaw.dev.Conference.enums.State;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_user")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer age;

    @Enumerated(EnumType.STRING)
    private State state;
    private String city;
    private String cpf;
    private String email;
    private String password;

    @ManyToOne
    @JoinColumn(name = "conference_id")
    private Conference conference;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Payment> payments;

    @Enumerated(EnumType.STRING)
    private Role role;

    public User(Long id, String name, Integer age, State state, String city, String cpf, String email, Role role) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.state = state;
        this.city = city;
        this.cpf = cpf;
        this.email = email;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
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
