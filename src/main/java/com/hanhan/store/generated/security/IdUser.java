/**
 * 
 */
package com.hanhan.store.generated.security;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.authority.AuthorityUtils;

/**
 * General User
 * 
 * @author JerryXia
 *
 */
public class IdUser extends org.springframework.security.core.userdetails.User {
    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    public static final String ANONYMOUS_AUTHORITY = "ANONYMOUS";
    public static final IdUser ANONYMOUS_USER = anonymousUser();

    private final String id;
    private final Date   createDate;

    private static IdUser anonymousUser() {
        return new IdUser(null, ANONYMOUS_AUTHORITY + "-" + UUID.randomUUID().toString(), "", null, AuthorityUtils.createAuthorityList(ANONYMOUS_AUTHORITY));
    }

    public IdUser(String id, String userName, String encodedPassword, Date createDate, Collection<? extends GrantedAuthority> authorities) {
        this(id, userName, encodedPassword, true, true, true, true, createDate, authorities);
    }

    public IdUser(String id, String userName, String encodedPassword, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked,
            Date createDate, Collection<? extends GrantedAuthority> authorities) {
        super(userName, encodedPassword, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.id = id;
        this.createDate = createDate;
    }

    public String getId() {
        return id;
    }

    public Date getCreateDate() {
        return createDate;
    }
}
