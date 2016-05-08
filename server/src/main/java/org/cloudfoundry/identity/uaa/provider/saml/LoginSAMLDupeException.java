package org.cloudfoundry.identity.uaa.provider.saml;

import org.springframework.security.authentication.BadCredentialsException;

public class LoginSAMLDupeException extends LoginSAMLException {
    /**
     * Generated serialization id.
     */
    private static final long serialVersionUID = 9115629621572693116L;

    /**
     * Constructs a <code>LoginSAMLDupeAddUserNotAllowException</code> with the
     * specified message.
     *
     * @param msg
     *            the detail message
     */
    public LoginSAMLDupeException(final String msg) {
        super(msg);
    }

    public LoginSAMLDupeException(final String msg, final Throwable e) {
        super(msg, e);
    }
}
