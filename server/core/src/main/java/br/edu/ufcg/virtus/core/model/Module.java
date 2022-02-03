package br.edu.ufcg.virtus.core.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import br.edu.ufcg.virtus.common.model.AuditModel;
import br.edu.ufcg.virtus.common.model.Model;
import br.edu.ufcg.virtus.core.domain.OpenMode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "module")
@SQLDelete(sql = "UPDATE module SET deleted = true WHERE id = ?")
@Where(clause = Model.WHERE_DELETED_CLAUSE)
@Getter
@Setter
public class Module extends AuditModel<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "icon", nullable = false)
    private String icon;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "link", nullable = false)
    private String link;

    @Column(name = "deleted")
    private boolean deleted;

    @Column(name = "open_mode", nullable = false)
    @Enumerated(EnumType.STRING)
    private OpenMode openMode;

    @Column(name = "path_name")
    private String pathName;

    @Column(name = "visible")
    private boolean visible;

    @OneToMany(mappedBy = "module")
    private Set<Permission> permissions;

}
