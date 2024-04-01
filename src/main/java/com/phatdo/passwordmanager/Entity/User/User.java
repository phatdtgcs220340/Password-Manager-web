package com.phatdo.passwordmanager.Entity.User;

import java.util.*;
import java.util.stream.Collectors;

import com.phatdo.passwordmanager.Entity.Application.Application;
import com.phatdo.passwordmanager.Entity.Account.Account;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@Table(name = "\"user\"")
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC, force = true)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;
<<<<<<< HEAD
    @Column(name = "name")
    private final String name;
    @Column(name = "email", unique = true)
    private final String email;
=======
    @Column(name = "full_name")
    private final String fullName;
    @Column(name = "username", unique = true)
    private final String username;
>>>>>>> refs/remotes/origin/develop-oauth2
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Account> accounts = new HashSet<>();

    public Set<Application> getApplications() {
        return this.accounts.stream()
                .map(Account::getApplication)
                .collect(Collectors.toSet());
    }

    @Override
    public boolean equals(Object o) {

        return this.id == ((User) o).getId();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getClass());
    }
}
