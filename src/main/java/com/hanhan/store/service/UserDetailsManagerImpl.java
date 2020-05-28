/**
 * 
 */
package com.hanhan.store.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import com.github.jerryxia.devutil.ObjectId;
import com.github.jerryxia.devutil.SystemClock;
import com.hanhan.store.generated.Consts;
import com.hanhan.store.generated.autoconfigure.AppProperties;
import com.hanhan.store.generated.security.IdUser;
import com.hanhan.store.generated.security.PasswordStorage;
import com.hanhan.store.generated.security.PasswordStorage.CannotPerformOperationException;
import com.vip.vjtools.vjkit.collection.CollectionUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * @author JerryXia
 *
 */
@Slf4j
@Service
public class UserDetailsManagerImpl implements UserDetailsManager {
    @Autowired
    private SecurityProperties security;
    @Autowired
    private AppProperties      appProperties;

    private List<GrantedAuthority> userAuthorities;
    private IdUser                 defaultSecurityUser;

    @PostConstruct
    public void init() {
        this.userAuthorities = userAuthorities(security.getUser().getRole());
        defaultSecurityUser = new IdUser("", security.getUser().getName(), security.getUser().getPassword(), SystemClock.nowDate(), this.userAuthorities);
    }

    @Override
    public IdUser loadUserByUsername(String username) throws UsernameNotFoundException {
        IdUser userDetails = null;
        // TODO: load from customize code
        if (userDetails == null) {
            userDetails = useSecurityUser(username);
            if (userDetails == null) {
                throw new UsernameNotFoundException("Username Not Found.");
            }
        }
        if (userDetails == null) {
            throw new UsernameNotFoundException(String.format("username: %s not found.", username));
        }
        return userDetails;
    }

    public List<GrantedAuthority> buildUserDefaultAuthorities() {
        return AuthorityUtils.createAuthorityList("ROLE_ANONYMOUS", this.appProperties.getSecurity().getUserDefaultAuthority());
    }

    private IdUser useSecurityUser(String username) {
        if (this.security.getUser().getName().equals(username)) {
            return this.defaultSecurityUser;
        } else {
            return null;
        }
    }

    private List<GrantedAuthority> userAuthorities(List<String> roles) {
        // load user default authorities
        List<GrantedAuthority> userDefaultAuthorities = this.buildUserDefaultAuthorities();
        List<GrantedAuthority> userAuthorities = new ArrayList<GrantedAuthority>(roles.size() + userDefaultAuthorities.size());
        for (String role : roles) {
            if(StringUtils.isNotBlank(role)) {
                userAuthorities.add(new SimpleGrantedAuthority(role));
            }
        }
        userAuthorities.addAll(userDefaultAuthorities);
        return userAuthorities;
    }

    @Override
    public void createUser(UserDetails user) {

    }

    @Override
    public void updateUser(UserDetails user) {

    }

    @Override
    public void deleteUser(String username) {

    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {

    }

    @Override
    public boolean userExists(String username) {
        return false;
    }
}
