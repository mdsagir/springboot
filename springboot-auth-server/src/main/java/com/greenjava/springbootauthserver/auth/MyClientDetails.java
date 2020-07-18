package com.greenjava.springbootauthserver.auth;

import com.greenjava.springbootauthserver.entity.Client;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.ClientDetails;

import java.util.*;

public class MyClientDetails implements ClientDetails {


    private final String clientId;
    private final String clientSecret;
    private final String scope;
    private final Set<String> authorizedGrantTypes;

    public MyClientDetails(Client client) {
        this.clientId = client.getClientId();
        this.clientSecret = client.getClientSecret();
        this.scope = client.getScope();
        this.authorizedGrantTypes = new HashSet<>();
        this.authorizedGrantTypes.add("password");
    }


    @Override
    public String getClientId() {
        return this.clientId;
    }

    @Override
    public Set<String> getResourceIds() {
        Set<String> resourceIdStrings = new HashSet<>();
        return resourceIdStrings;
    }

    @Override
    public boolean isSecretRequired() {
        return false;
    }

    @Override
    public String getClientSecret() {
        return this.clientSecret;
    }

    @Override
    public boolean isScoped() {
        return true;
    }

    @Override
    public Set<String> getScope() {
        Set<String> scopesSet = new HashSet<>();
        scopesSet.add(scope);
        return scopesSet;
    }

    @Override
    public Set<String> getAuthorizedGrantTypes() {
        return this.authorizedGrantTypes;
    }

    @Override
    public Set<String> getRegisteredRedirectUri() {
        Set<String> registerRedirect = new HashSet<>();
        return registerRedirect;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return AuthorityUtils.createAuthorityList("ROLE_USER");
    }

    @Override
    public Integer getAccessTokenValiditySeconds() {
        return 50000;
    }

    @Override
    public Integer getRefreshTokenValiditySeconds() {
        return 50000;
    }

    @Override
    public boolean isAutoApprove(String s) {
        return true;
    }

    @Override
    public Map<String, Object> getAdditionalInformation() {
        Map<String, Object> map = new HashMap<>();
        return map;
    }
}
