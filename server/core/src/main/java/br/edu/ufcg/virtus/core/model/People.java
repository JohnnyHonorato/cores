package br.edu.ufcg.virtus.core.model;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import br.edu.ufcg.virtus.common.model.AuditModel;
import br.edu.ufcg.virtus.common.model.Model;
import br.edu.ufcg.virtus.core.domain.PeopleType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "people")
@SQLDelete(sql = "UPDATE people SET deleted = true WHERE id = ?")
@Where(clause = Model.WHERE_DELETED_CLAUSE)
@Getter
@Setter
@NoArgsConstructor
public class People extends AuditModel<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name", nullable = false)
    @NotBlank(message = "{people.name.required}")
    private String name;

    @Column(name = "government_code")
    private String governmentCode;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "deleted")
    private boolean deleted;

    @OneToMany(mappedBy = "people")
    private Set<PeopleOrganization> peopleOrganizations;

    @OneToOne(fetch = FetchType.EAGER, cascade = { CascadeType.REMOVE })
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToOne(fetch = FetchType.EAGER, mappedBy = "people")
    private PeopleHistory history;

    @Transient
    private PeopleType peopleType;

    @Transient
    private List<ContactInfo> contacts;

    public People(Integer id) {
        this.id = id;
    }
}
