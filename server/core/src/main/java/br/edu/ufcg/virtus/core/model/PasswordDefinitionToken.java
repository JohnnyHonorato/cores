package br.edu.ufcg.virtus.core.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.SQLDelete;

import br.edu.ufcg.virtus.common.model.Model;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "password_definition_token")
@SQLDelete(sql = "UPDATE password_definition_token SET deleted = true WHERE id = ?")
@Getter
@Setter
public class PasswordDefinitionToken extends Model<Integer>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "deleted")
    private Boolean deleted;

    @NotEmpty
    @Column(name = "token", length = 128, nullable = false, updatable = false)
    private String token;

    @Column(name = "used", nullable = false)
    @NotNull
    private Boolean used;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private User user;

    @Column(name = "validity", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    private Date validity;

    public PasswordDefinitionToken() {

    }

    public PasswordDefinitionToken(String token, Date validity, User user) {
        this.token = token;
        this.validity = validity;
        this.user = user;
    }

    public boolean isExpired() {
        return this.used || this.deleted || this.validity.before(new Date());
    }

}
