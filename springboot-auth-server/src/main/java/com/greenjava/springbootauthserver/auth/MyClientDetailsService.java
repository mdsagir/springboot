package com.greenjava.springbootauthserver.auth;

import com.greenjava.springbootauthserver.repository.ClientRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;

@Service
public class MyClientDetailsService implements ClientDetailsService {

    private final ClientRepository clientRepository;

    public MyClientDetailsService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;

    }

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {

        return this.clientRepository.findByClientId(clientId)
                .map(MyClientDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException(" client not found!"));

    }
}
