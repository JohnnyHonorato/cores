package br.edu.ufcg.virtus.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import br.edu.ufcg.virtus.common.model.Model;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "filepath")
@SQLDelete(sql = "UPDATE filepath SET deleted = true WHERE id = ?")
@Where(clause = Model.WHERE_DELETED_CLAUSE)
@Getter
@Setter
@NoArgsConstructor
public class FilePath extends Model<Integer> {

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
	
	@Column(name = "deleted")
    private boolean deleted;

}
