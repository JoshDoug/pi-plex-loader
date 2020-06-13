package com.jds.loaderapi.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

// TODO - unused so far

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

    // TODO - so...what does this really do?
    //  Also...there's a specific LoginUrlAuthenticationEntryPoint, should we use that instead/as well?
    //  Hmm, if it's just used to redirect to the login page...that's something the SPA router should handle instead
    //  The redirect is only handy for a SpringMVC, or static site with no client routing (so doesn't make sense for React, Angular, etc)

    // This might serve as a useful response to allow a SPA to handle any unauthorised access attempts though.

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException {
        httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorised");
    }
}
