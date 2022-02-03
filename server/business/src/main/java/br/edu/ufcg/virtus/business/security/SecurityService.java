package br.edu.ufcg.virtus.business.security;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Service;

import br.edu.ufcg.virtus.business.repository.CoreRepository;
import br.edu.ufcg.virtus.common.security.authorization.Authorization;
import br.edu.ufcg.virtus.common.service.BaseService;
import br.edu.ufcg.virtus.common.util.TokenUtil;
import lombok.RequiredArgsConstructor;

/**
 * Service to be use in security features.
 *
 * @author Virtus
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SecurityService extends BaseService {
    
    private static final String MODULE_NAME = "BUSINESS";
    private static final String OPERATION_PATTERN = MODULE_NAME + "/%s-%s";

    private final TokenUtil tokenUtil;
    private final CoreRepository coreRepository;

    public boolean allow(Object instance, String operation, HttpServletRequest request) {
        return this.allow(false, instance, operation, request);
    }

    public boolean allow(Object instance, HttpServletRequest request, String... operations) {
        boolean allowed = false;
        for (final String operation : operations) {
            allowed = this.allow(true, instance, operation, request);
            if (allowed) {
                break;
            }
        }
        return allowed;
    }

    public boolean allow(boolean custom, Object instance, String operation, HttpServletRequest request) {

        try {
            final String username = this.tokenUtil.getUserName(request);
            if (username == null) {
                return false;
            }

            final String wildcard = String.format(SecurityService.OPERATION_PATTERN, operation.toLowerCase(), "*");
            if (!custom) {
                final Authorization auth = AnnotationUtils.findAnnotation(instance.getClass(), Authorization.class);
                if (auth == null) {
                    return true;
                }
                operation = String.format(SecurityService.OPERATION_PATTERN, operation.toLowerCase(), auth.value().toLowerCase());
            }

            final List<String> userPermissions = this.coreRepository.getAllPermissions(username);
            return userPermissions.contains(wildcard) || userPermissions.contains(operation);
        } catch (final Exception e) {
            return false;
        }
    }
}
