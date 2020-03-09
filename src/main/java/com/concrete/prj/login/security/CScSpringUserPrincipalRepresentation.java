package com.concrete.prj.login.security;

import com.concrete.prj.login.bl.model.CScUserEO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Description: User information which is later encapsulated into {@link Authentication}
 * Concrete API Challenge.
 *
 * @author : Sergio Kriz
 **/

public class CScSpringUserPrincipalRepresentation implements UserDetails {

    private Long id;
    private String name;
    private String username;

    @JsonIgnore
    private String email;

    @JsonIgnore
    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    /**
     * Constructs the User Principal
     *
     * @param aId              the identifier of the user
     * @param aName            user aName
     * @param aUserName        unique user aName
     * @param aEmail           aEmail
     * @param aPassword        aPassword
     * @param aUserAuthorities aUserAuthorities of the user
     */
    public CScSpringUserPrincipalRepresentation(Long aId, String aName, String aUserName, String aEmail, String aPassword, Collection<? extends GrantedAuthority> aUserAuthorities) {
        this.id = aId;
        this.name = aName;
        this.username = aUserName;
        this.email = aEmail;
        this.password = aPassword;
        this.authorities = aUserAuthorities;
    }

    /**
     * Builds a User Principal object from a user EO
     *
     * @param aUser user EO
     * @return user principal
     */
    public static CScSpringUserPrincipalRepresentation resolveUserPrincipalFromEO(CScUserEO aUser) {

        List<GrantedAuthority> authorities =
                aUser.getUserRoleSet().stream()
                        .map(role -> new SimpleGrantedAuthority(role.getUserRole().name()))
                        .collect(Collectors.toList());

        CScSpringUserPrincipalRepresentation userPrincipal = new CScSpringUserPrincipalRepresentation(
                aUser.getUserId(),
                aUser.getUserName(),
                aUser.getUserName(),
                aUser.getEmail(),
                aUser.getPassword(),
                authorities);

        return userPrincipal;
    }

    /**
     * @return the identifier of the user
     */
    public Long getId() {
        return id;
    }

    /**
     * @return user name
     */
    public String getName() {
        return name;
    }

    /**
     * @return user email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @return user name
     */
    @Override
    public String getUsername() {
        return username;
    }

    /**
     * @return password
     */
    @Override
    public String getPassword() {
        return password;
    }

    /**
     * @return user authorities
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    /**
     * @return expiration of the account
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * @return locking of the account
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * @return expiration of the credential
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * @return is the user account enabled
     */
    @Override
    public boolean isEnabled() {
        return true;
    }

    /**
     * @param o another object
     * @return if the object is equal to the other
     */
    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CScSpringUserPrincipalRepresentation that = (CScSpringUserPrincipalRepresentation) o;
        return Objects.equals(id, that.id);
    }

    /**
     * @return hash code of a given object
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
