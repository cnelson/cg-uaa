package org.cloudfoundry.identity.uaa.authentication;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.ExceptionMappingAuthenticationFailureHandler;

import org.cloudfoundry.identity.uaa.constants.OriginKeys;
import org.cloudfoundry.identity.uaa.provider.IdentityProvider;
import org.cloudfoundry.identity.uaa.provider.IdentityProviderProvisioning;
import org.cloudfoundry.identity.uaa.util.DomainFilter;
import org.cloudfoundry.identity.uaa.zone.IdentityZoneHolder;



public class IDPAwareAuthenticationFailureHandler extends ExceptionMappingAuthenticationFailureHandler {

    private IdentityProviderProvisioning providers;
    private String spEntityID;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
            HttpServletResponse response, AuthenticationException exception)
            throws IOException, ServletException {

        String email = request.getParameter("username");

        // make sure we have an @ sign, otherwise the DomainFilter is useless
        // it returns all providers if the username doesn't contain a domain
        if (email!=null && email.contains("@")) {

            // find identity Providers that can service this domain
            DomainFilter filter = new DomainFilter();
            List<IdentityProvider> activeProviders = providers.retrieveActive(IdentityZoneHolder.get().getId());
            List<IdentityProvider> providers = filter.filter(activeProviders, null, email);

            // if our first provider isn't UAA, then generate a SAML url for it and bounce
            // yes we choose the first one only, this is the same logic as the invite API
            if (!providers.isEmpty() && !providers.get(0).getOriginKey().equals(OriginKeys.UAA)) {
                getRedirectStrategy().sendRedirect(request, response, "/saml/discovery?returnIDParam=idp&isPassive=true&entityID=" + spEntityID + "&idp=" + providers.get(0).getOriginKey());
                return;
            }
        }

        // if we reached here then no IDP was found for the username
        // so let the parent do what the upstream would have done originally.
        super.onAuthenticationFailure(request, response, exception);
    }

    public void setProviders(IdentityProviderProvisioning p) {
        this.providers = p;
    }

    public void setspEntityID(String s) {
        this.spEntityID = s;
    }

}

