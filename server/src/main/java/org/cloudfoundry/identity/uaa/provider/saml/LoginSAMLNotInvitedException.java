package org.cloudfoundry.identity.uaa.provider.saml;

import org.springframework.security.authentication.BadCredentialsException;

public class LoginSAMLNotInvitedException extends LoginSAMLException {
    /**
     * Generated serialization id.
     */
    private static final long serialVersionUID = 9115629621572693116L;

    /**
     * Constructs a <code>LoginSAMLNotInvitedAddUserNotAllowException</code> with the
     * specified message.
     *
     * @param msg
     *            the detail message
     */
    public LoginSAMLNotInvitedException(final String msg) {
        super(msg);
    }

    public LoginSAMLNotInvitedException(final String msg, final Throwable e) {
        super(msg, e);
    }
}
