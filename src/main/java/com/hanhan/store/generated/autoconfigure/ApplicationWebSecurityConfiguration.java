/**
 * 
 */
package com.hanhan.store.generated.autoconfigure;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.autoconfigure.security.SecurityProperties.Headers;
import org.springframework.boot.autoconfigure.security.SecurityProperties.Headers.ContentSecurityPolicyMode;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.header.writers.HstsHeaderWriter;
import org.springframework.security.web.util.matcher.AnyRequestMatcher;
import org.springframework.util.StringUtils;

import com.hanhan.store.generated.RuntimeVars;
import com.hanhan.store.generated.autoconfigure.AppSecurityProperties.MatchersAuthoritiesPair;
import com.hanhan.store.generated.security.AbstractRememberMeBodyWriteAuthenticationSuccessHandler;
import com.hanhan.store.generated.security.DaoAuthenticationProviderPostProcess;
import com.hanhan.store.generated.security.HeaderAdapterTokenBasedRememberMeServices;
import com.hanhan.store.generated.security.IdUser;
import com.hanhan.store.generated.security.PasswordStoragePasswordEncoder;
import com.hanhan.store.generated.web.ManagementDashboardController;
import com.hanhan.store.service.LoginAuthenticationFailureHandler;

/**
 * {@link org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration}
 * 
 * {@link org.springframework.boot.autoconfigure.security.SpringBootWebSecurityConfiguration}
 * {@link org.springframework.boot.actuate.autoconfigure.ManagementWebSecurityAutoConfiguration}
 * 
 * @author JerryXia
 *
 */
@EnableConfigurationProperties(AppProperties.class)
@EnableWebSecurity(debug = false)
@Configuration
public class ApplicationWebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    private AppProperties                                           appProperties;
    private SecurityProperties                                      security;
    private UserDetailsService                                      userDetailsService;
    private AbstractRememberMeBodyWriteAuthenticationSuccessHandler successHandler;
    private LoginAuthenticationFailureHandler                       failureHandler;
    private HeaderAdapterTokenBasedRememberMeServices               rememberMeServices;
    private UserCache                                               userCache;
    private ManagementDashboardController                           managementDashboardController;

    @PostConstruct
    public void init() {
        appProperties = getApplicationContext().getBean(AppProperties.class);
        security = getApplicationContext().getBean(SecurityProperties.class);
        userDetailsService = getApplicationContext().getBean(UserDetailsService.class);
        successHandler = getApplicationContext().getBean(AbstractRememberMeBodyWriteAuthenticationSuccessHandler.class);
        failureHandler = getApplicationContext().getBean(LoginAuthenticationFailureHandler.class);
        userCache = getApplicationContext().getBean(UserCache.class);
        managementDashboardController = getApplicationContext().getBean(ManagementDashboardController.class);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        try {
            ErrorController errorController = getApplicationContext().getBean(ErrorController.class);
            String[] ignored = toStringArray(security.getIgnored(), 1);
            ignored[ignored.length - 1] = errorController.getErrorPath();
            web.ignoring().antMatchers(ignored);
        } catch (BeansException ex) {
            String[] ignored = toStringArray(security.getIgnored());
            web.ignoring().antMatchers(ignored);
        }
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        PasswordStoragePasswordEncoder passwordEncoder = new PasswordStoragePasswordEncoder();
        DaoAuthenticationProviderPostProcess daoAuthenticationProviderPostProcess = new DaoAuthenticationProviderPostProcess(this.userCache);
        auth.eraseCredentials(false).userDetailsService(this.userDetailsService).passwordEncoder(passwordEncoder).withObjectPostProcessor(daoAuthenticationProviderPostProcess);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        if (security.isRequireSsl()) {
            http.requiresChannel().anyRequest().requiresSecure();
        }
        if (!security.isEnableCsrf()) {
            http.csrf().disable();
        }
        configureHeaders(http.headers(), security.getHeaders());

        String rememberMeCookieName = "Rmtoken";
        int tokenValiditySeconds = 3600 * 24 * 365;
        String rememberMeKey = "rememberMe_key_g40n0xniud";

        http.cors().configurationSource(RuntimeVars.CORS_CONFIGURATION_SOURCE)
                // org.springframework.web.filter.CorsFilter
                .and().logout().permitAll()
                // org.springframework.security.web.authentication.logout.LogoutFilter
                .and().formLogin().defaultSuccessUrl(this.managementDashboardController.dashboardPath()).permitAll()
                // org.springframework.security.web.authentication.ui.DefaultLoginPageGeneratingFilter
                .and().rememberMe().rememberMeCookieName(rememberMeCookieName).tokenValiditySeconds(tokenValiditySeconds).key(rememberMeKey)
                // org.springframework.security.web.authentication.rememberme.RememberMeAuthenticationFilter
                .and().anonymous().authorities(IdUser.ANONYMOUS_AUTHORITY)
                // ROLE_ANONYMOUS, org.springframework.security.web.authentication.AnonymousAuthenticationFilter
                .and().sessionManagement().sessionCreationPolicy(security.getSessions());
        // org.springframework.security.web.session.SessionManagementFilter

        configureAuthrizeRequests(http);
    }

    private void configureAuthrizeRequests(HttpSecurity http) throws Exception {
        // load config user roles
        String[] roles = new String[security.getUser().getRole().size()];
        security.getUser().getRole().toArray(roles);
        http.authorizeRequests().antMatchers(appProperties.getSysComponent().getUrlPattern()).hasAnyAuthority(roles);

        // load config security matchs
        for (MatchersAuthoritiesPair pair : appProperties.getSecurity().getMatchs()) {
            if (pair.getAuthorities().length > 1) {
                http.authorizeRequests().antMatchers(pair.getAntPatterns()).hasAnyAuthority(pair.getAuthorities());
            } else {
                // if no configuration, throw error
                http.authorizeRequests().antMatchers(pair.getAntPatterns()).hasAuthority(pair.getAuthorities()[0]);
            }
        }

        // add ANONYMOUS authorize request.
        //  org.springframework.security.config.annotation.web.configurers.AnonymousConfigurer
        //http.authorizeRequests().anyRequest().hasAuthority(IdUser.ANONYMOUS_AUTHORITY);
        http.authorizeRequests().anyRequest().permitAll();
    }

    private void configureHeaders(HeadersConfigurer<HttpSecurity> configurer, SecurityProperties.Headers headers) throws Exception {
        if (headers.getHsts() != Headers.HSTS.NONE) {
            boolean includeSubDomains = headers.getHsts() == Headers.HSTS.ALL;
            HstsHeaderWriter writer = new HstsHeaderWriter(includeSubDomains);
            writer.setRequestMatcher(AnyRequestMatcher.INSTANCE);
            configurer.addHeaderWriter(writer);
        }
        if (!headers.isContentType()) {
            configurer.contentTypeOptions().disable();
        }
        if (StringUtils.hasText(headers.getContentSecurityPolicy())) {
            String policyDirectives = headers.getContentSecurityPolicy();
            ContentSecurityPolicyMode mode = headers.getContentSecurityPolicyMode();
            if (mode == ContentSecurityPolicyMode.DEFAULT) {
                configurer.contentSecurityPolicy(policyDirectives);
            } else {
                configurer.contentSecurityPolicy(policyDirectives).reportOnly();
            }
        }
        if (!headers.isXss()) {
            configurer.xssProtection().disable();
        }
        if (!headers.isCache()) {
            configurer.cacheControl().disable();
        }
        if (!headers.isFrame()) {
            configurer.frameOptions().disable();
        }
    }

    private String[] toStringArray(List<String> lists) {
        return toStringArray(lists, 0);
    }

    private String[] toStringArray(List<String> lists, int overflowSize) {
        if (lists == null) {
            return null;
        }
        String[] array = new String[lists.size() + overflowSize];
        lists.toArray(array);
        return array;
    }
}
