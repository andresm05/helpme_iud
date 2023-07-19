package co.edu.iudigital.helpmeiud.auth.filters;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import static co.edu.iudigital.helpmeiud.utils.Constants.*;
import co.edu.iudigital.helpmeiud.models.entities.Consumer;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import co.edu.iudigital.helpmeiud.utils.TokenSetUp;

public class JwtAuthFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    // make sure to add the constructor
    public JwtAuthFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(final HttpServletRequest request, final HttpServletResponse response)
            throws AuthenticationException {
        
        Consumer user = null;
        String username = null;
        String password = null;

        try {
            user = new ObjectMapper().readValue(request.getInputStream(), Consumer.class); //get the user from the request
            username = user.getUsername();
            password = user.getPassword();

            logger.info( "email: " + username);
            logger.info("password: " + password);

        } catch (StreamReadException e) {
            e.printStackTrace();
        } catch (DatabindException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);

        return authenticationManager.authenticate(authToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authResult) throws IOException, ServletException {

                String username = ((User) authResult.getPrincipal())
                .getUsername();

                Collection<? extends GrantedAuthority> roles = authResult.getAuthorities();
                boolean isAdmin = roles.stream().anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));

                Claims claims = Jwts.claims(); //create the claims to add to the token
                claims.put("authorities", new ObjectMapper().writeValueAsString(roles));
                claims.put("isAdmin", isAdmin);
                
                // Generate token
                String token = TokenSetUp.generateToken(claims, username);

                response.addHeader(HEADER_AUTH, PREFIX_TOKEN + token);

                Map<String, Object> body = new HashMap<>();
                body.put("token", token);
                body.put("message", String.format("Hola %s, has iniciado sesión con éxito!", username));
                body.put("email", username);

                response.getWriter().write(new ObjectMapper().writeValueAsString(body)); //write the response
                response.setStatus(200);
                response.setContentType("application/json");
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException failed) throws IOException, ServletException {

        Map<String, Object> body = new HashMap<>();
        body.put("message", "Error de autenticación: email o contraseña incorrectos");
        body.put("error", failed.getMessage());

        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        response.setStatus(401);
        response.setContentType("application/json");

        super.unsuccessfulAuthentication(request, response, failed);
    }

}
