package br.edu.ufcg.virtus.common.util;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import br.edu.ufcg.virtus.common.model.LoggedUser;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SecurityContextUtil {
    
    private SecurityContextUtil() {

    }

    public static void setSecurityContext(SignedJWT signedJWT, Integer userId) {
        try {
            final JWTClaimsSet claims = signedJWT.getJWTClaimsSet();
            final String username = claims.getSubject();
            if (username == null) {
                throw new JOSEException("Username missing from JWT");
            }

            final List<String> authorities = new ArrayList<>(); 
            
            final LoggedUser applicationUser = LoggedUser
                    .builder()
                    .id(userId)
                    .username(username)
                    .role(String.join(",", authorities))
                    .build();
            final UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(applicationUser, null,
                    createAuthorities(authorities));
            auth.setDetails(signedJWT.serialize());

            SecurityContextHolder.getContext().setAuthentication(auth);
        } catch (final Exception e) {
            log.error("Error setting security context ", e);
            SecurityContextHolder.clearContext();
        }
    }
    
    public static LoggedUser getLoggedUser() {
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            return null;
        }

        final Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if ((principal != null) && (principal instanceof LoggedUser)) {
            return (LoggedUser) principal;
        }
        
        return null;
    }

    private static List<SimpleGrantedAuthority> createAuthorities(List<String> authorities) {
        return authorities.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(toList());
    }
}
