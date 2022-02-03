package br.edu.ufcg.virtus.common.token;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.filter.OncePerRequestFilter;

import com.nimbusds.jwt.SignedJWT;

import br.edu.ufcg.virtus.common.config.JwtConfig;
import br.edu.ufcg.virtus.common.util.SecurityContextUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class JwtTokenAuthorizationFilter extends OncePerRequestFilter {

    protected final JwtConfig jwtConfiguration;
    protected final TokenConverter tokenConverter;
    private final EntityManager em;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain chain)
            throws ServletException, IOException {
        final String header = request.getHeader(this.jwtConfiguration.getHeader().getName());

        if (header == null || !header.startsWith(this.jwtConfiguration.getHeader().getPrefix())) {
            chain.doFilter(request, response);
            return;
        }

        final String token = header.replace(this.jwtConfiguration.getHeader().getPrefix(), "").trim();
        final SignedJWT signedToken = this.parseToken(token);
        final String sql = "SELECT id FROM core.user_account WHERE username = '" + this.getUsername(signedToken) + "'";
        final Query query = this.em.createNativeQuery(sql);
        try {
            final Integer userId = ((Number) query.getSingleResult()).intValue();
            SecurityContextUtil.setSecurityContext(signedToken, userId);
        } catch (final Exception e) {
            log.info("User not found on login");
        }

        chain.doFilter(request, response);
    }

    @SneakyThrows
    private SignedJWT parseToken(String token) {
        final SignedJWT signedToken = SignedJWT.parse(token);
        return signedToken;
    }

    @SneakyThrows
    private String getUsername(SignedJWT token) {
        return token.getJWTClaimsSet().getSubject();
    }

}
