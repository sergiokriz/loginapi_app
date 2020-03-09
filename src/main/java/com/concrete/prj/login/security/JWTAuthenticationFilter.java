package com.concrete.prj.login.security;

import com.concrete.prj.login.to.signup.CScLoginRequestTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// It's not being used :/

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    //private AuthenticationManager authenticationManager;
    //public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
    //    this.authenticationManager = authenticationManager;
    // }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

/*
        if (isLoginPage(req)) {

            attemptAuthentication(req, res);

            LoginRequestTO loginRequestTO = resolveLoginRequestTO(new LoginRequestTO(), req);
            AuthenticationManager authenticationManager = this.getAuthenticationManager();

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequestTO.getEmail(),
                            loginRequestTO.getPassword()
                    )
            );

            try {
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            catch (Exception e) {
                System.out.println(e);
            }
        }

 */

        chain.doFilter(request, response);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException {

        try {
            CScLoginRequestTO creds = new ObjectMapper()
                    .readValue(req.getInputStream(), CScLoginRequestTO.class);
        } catch (IOException e) {
            e.printStackTrace();
        }


        /*
        try {
            ApplicationUser creds = new ObjectMapper()
                    .readValue(req.getInputStream(), ApplicationUser.class);

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            creds.getUsername(),
                            creds.getPassword(),
                            new ArrayList<>())
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
         */
         return null;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {
        System.out.println("bla");

        /*
        String token = JWT.create()
                .withSubject(((User) auth.getPrincipal()).getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(HMAC512(SECRET.getBytes()));
        res.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
         */
    }

    private boolean isLoginPage(HttpServletRequest aHttpServletRequest) {

        String uri = aHttpServletRequest.getRequestURI().substring(aHttpServletRequest.getContextPath().length());

        String pattern = "(.+?)(?:/|$)";
        Pattern r = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
        Matcher m = r.matcher(uri);

        if (m.find() && m.groupCount() == 1) {
            String parsedURI = m.group(1);
            return parsedURI.equals("/login") || parsedURI.equals("/login.json");
        }

        return false;
    }

    CScLoginRequestTO resolveLoginRequestTO(CScLoginRequestTO aLoginRequestTO, HttpServletRequest aHttpServletRequest) {

        ObjectMapper mapper = new ObjectMapper();

        //ContentCachingRequestWrapper request = new ContentCachingRequestWrapper(aHttpServletRequest);

        aLoginRequestTO.setEmail("joao@silva.org");
        aLoginRequestTO.setPassword("hunter2");

/*
        try {
            //aLoginRequestTO = mapper.readValue(request.getReader(), LoginRequestTO.class);
        } catch (JsonGenerationException | JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
 */

        return aLoginRequestTO;
    }

}
