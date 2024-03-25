package com.phatdo.passwordmanager.Entity.Application;

import com.phatdo.passwordmanager.Entity.Account.Account;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "\"application\"")
@Data
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC, force = true)
public class Application implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;
    @Column(name = "application_name", unique = true)
    private final String applicationName;
    @Column(name = "url")
    private final String url;
    @OneToMany(mappedBy = "application", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Account> accounts = new HashSet<>();

    public String getRedirectURL() {
        return String.format("?applicationId=%d", this.id);
    }

    @Override
    public boolean equals(Object o) {

        return this.id == ((Application) o).getId();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getClass());
    }
}
