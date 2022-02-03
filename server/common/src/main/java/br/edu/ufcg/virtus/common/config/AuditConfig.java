package br.edu.ufcg.virtus.common.config;

import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.CurrentDateTimeProvider;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import br.edu.ufcg.virtus.common.model.LoggedUser;
import br.edu.ufcg.virtus.common.util.SecurityContextUtil;


@Configuration
@EnableJpaAuditing(modifyOnCreate = false, dateTimeProviderRef = "dateTimeProvider", auditorAwareRef = "createAuditorProvider")
public class AuditConfig {

    @Bean
    public DateTimeProvider dateTimeProvider() {
        return CurrentDateTimeProvider.INSTANCE;
    }

    @Bean
    public AuditorAware<Integer> createAuditorProvider() {
        return new SecurityAuditor();
    }

    public class SecurityAuditor implements AuditorAware<Integer> {
        @Override
        public Optional<Integer> getCurrentAuditor() {
            final LoggedUser loggedUser = SecurityContextUtil.getLoggedUser();
            final Integer userId = loggedUser == null ? null : loggedUser.getId();
            return Optional.ofNullable(userId);
        }
    }
    

}
