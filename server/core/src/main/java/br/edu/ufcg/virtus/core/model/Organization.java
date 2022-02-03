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

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import br.edu.ufcg.virtus.common.model.AuditModel;
import br.edu.ufcg.virtus.common.model.Model;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "organization")
@SQLDelete(sql = "UPDATE organization SET deleted = true WHERE id = ?")
@Where(clause = Model.WHERE_DELETED_CLAUSE)
@Getter
@Setter
@NoArgsConstructor
public class Organization extends AuditModel<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "fantasy_name")
    private String fantasyName;

    @Column(name = "government_code")
    private String governmentCode;

    @Column(name = "deleted")
    private boolean deleted;

    @OneToOne(fetch = FetchType.EAGER, cascade = { CascadeType.ALL })
	@JoinColumn(name = "img_id", referencedColumnName = "id")
	private FilePath filePath;

    @OneToMany(mappedBy = "organization")
    private Set<PeopleOrganization> peopleOrganizations;

    @Transient
    private List<ContactInfo> contacts;

    public Organization(Integer id) {
        this.id = id;
    }

}
