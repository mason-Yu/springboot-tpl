package com.hanhan.store.generated.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author JerryXia
 *
 */
@ConfigurationProperties(prefix = "application")
public class AppProperties {
    private AppSysComponentProperties sysComponent = new AppSysComponentProperties();
    private AppSecurityProperties     security     = new AppSecurityProperties();

    public AppSysComponentProperties getSysComponent() {
        return sysComponent;
    }

    public void setSysComponent(AppSysComponentProperties sysComponent) {
        this.sysComponent = sysComponent;
    }

    public AppSecurityProperties getSecurity() {
        return security;
    }

    public void setSecurity(AppSecurityProperties security) {
        this.security = security;
    }
}
