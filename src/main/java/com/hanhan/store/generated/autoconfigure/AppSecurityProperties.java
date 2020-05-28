/**
 * 
 */
package com.hanhan.store.generated.autoconfigure;

import java.util.ArrayList;

/**
 * @author Administrator
 *
 */
public class AppSecurityProperties {
    private String                             userDefaultAuthority;
    private ArrayList<MatchersAuthoritiesPair> matchs;

    public String getUserDefaultAuthority() {
        return userDefaultAuthority;
    }

    public void setUserDefaultAuthority(String userDefaultAuthority) {
        this.userDefaultAuthority = userDefaultAuthority;
    }

    public ArrayList<MatchersAuthoritiesPair> getMatchs() {
        return matchs;
    }

    public void setMatchs(ArrayList<MatchersAuthoritiesPair> matchs) {
        this.matchs = matchs;
    }

    public static class MatchersAuthoritiesPair {
        private String[] antPatterns;
        private String[] authorities;

        public String[] getAntPatterns() {
            return antPatterns;
        }

        public void setAntPatterns(String[] antPatterns) {
            this.antPatterns = antPatterns;
        }

        public String[] getAuthorities() {
            return authorities;
        }

        public void setAuthorities(String[] authorities) {
            this.authorities = authorities;
        }
    }
}
