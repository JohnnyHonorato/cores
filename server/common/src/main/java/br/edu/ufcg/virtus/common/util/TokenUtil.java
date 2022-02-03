package br.edu.ufcg.virtus.common.util;

import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import br.edu.ufcg.virtus.common.config.JwtConfig;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public final class TokenUtil {

    private final JwtConfig jwtConfiguration;

    public String getUserName(HttpServletRequest request) throws ParseException {
        final JWTClaimsSet claims = this.getClaimSet(request);
        final String username = claims.getSubject();
        return username;
    }

    public String getName(HttpServletRequest request) throws ParseException {
        final JWTClaimsSet claims = this.getClaimSet(request);
        final String name = claims.getClaim("name").toString();
        return name;
    }
    
    public String getEmail(HttpServletRequest request) throws ParseException {
        final JWTClaimsSet claims = this.getClaimSet(request);
        final String email = claims.getClaim("email").toString();
        return email;
    }

    private JWTClaimsSet getClaimSet(HttpServletRequest request) throws ParseException {
        final String header = request.getHeader(this.jwtConfiguration.getHeader().getName());
        final String token = header.replace(this.jwtConfiguration.getHeader().getPrefix(), "").trim();
        final SignedJWT signedToken = SignedJWT.parse(token);
        return signedToken.getJWTClaimsSet();
    }

}
