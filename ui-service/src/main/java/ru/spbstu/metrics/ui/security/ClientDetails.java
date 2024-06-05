package ru.spbstu.metrics.ui.security;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.spbstu.metrics.ui.constants.Role;
import ru.spbstu.metrics.ui.models.Client;

import java.util.Collection;
import java.util.Collections;

public record ClientDetails(Client client) implements UserDetails {
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + Role.STAFF));
    }

    @Override
    public String getPassword() {
        return client.getPasswordHash();
    }

    @Override
    public String getUsername() {
        return client.getUsername();
    }
}
