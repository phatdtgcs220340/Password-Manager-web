package com.phatdo.passwordmanager.Entity.UserApplication;

import com.phatdo.passwordmanager.Entity.Application.Application;
import com.phatdo.passwordmanager.Entity.User.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Data
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC, force = true)
public class UserApplication implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private final User user;

    @ManyToOne
    @JoinColumn(name = "application_id")
    private final Application application;

    @Override
    public int hashCode() {
        return Objects.hash(id, user, application);
    }
}
