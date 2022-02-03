package br.edu.ufcg.virtus.tracker.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import br.edu.ufcg.virtus.common.model.AuditModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "filepath")
@Getter
@Setter
@NoArgsConstructor
public class FilePath extends AuditModel<Integer> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "name")
    private String name;
    
    @Column(name = "original_name")
    private String originalName;

    @Column(name = "file_size")
    private Integer fileSize;

    @Column(name = "directory")
    private String directory;

    @Column(name = "file_type")
    private String contentType;

    @ManyToOne
    @JoinColumn(name = "tracker_id", referencedColumnName = "id")
    private Tracker tracker;

}
