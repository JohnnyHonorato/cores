package br.edu.ufcg.virtus.core.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import br.edu.ufcg.virtus.common.model.AuditModel;
import br.edu.ufcg.virtus.common.model.Model;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "user_account")
@SQLDelete(sql = "UPDATE user_account SET deleted = true WHERE id = ?")
@Where(clause = Model.WHERE_DELETED_CLAUSE)
@Getter
@Setter
public class User extends AuditModel<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "username", nullable = false)
    @NotBlank(message = "{user.username.required}")
    private String username;

    @Column(name = "password")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, targetEntity = Role.class)
    @JoinTable(name = "user_role", joinColumns = {
            @JoinColumn(name = "user_account_id", referencedColumnName = "id") }, inverseJoinColumns = {
                    @JoinColumn(name = "role_id", referencedColumnName = "id") })
    private Set<Role> roles;

    @Column(name = "deleted")
    private boolean deleted;

    @Column(name = "external")
    private boolean external;

}
