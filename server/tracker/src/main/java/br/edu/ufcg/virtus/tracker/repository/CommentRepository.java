package br.edu.ufcg.virtus.tracker.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.edu.ufcg.virtus.common.dto.PageListDTO;
import br.edu.ufcg.virtus.common.dto.SearchFilterDTO;
import br.edu.ufcg.virtus.common.repository.CrudBaseRepository;
import br.edu.ufcg.virtus.tracker.model.Comment;

@Repository
public interface CommentRepository  extends CrudBaseRepository<Comment, Integer> {

    @Query(value = "SELECT c.* FROM tracker.comment c WHERE c.tracker_id = ?1 ORDER BY c.created_on DESC ",
            countQuery = "SELECT c.* FROM tracker.comment c WHERE c.tracker_id = ?1 ORDER BY c.created_on DESC ", 
            nativeQuery = true)
    Page<Comment> search(Integer trackerId, Pageable pageable);

    default PageListDTO search(Integer trackerId, SearchFilterDTO filter) {
        final Page<Comment> result = this.search(trackerId, this.page(filter));
        return new PageListDTO(result.getTotalPages(), result.getContent());
    }
    
    @Query(value = "SELECT c.* FROM tracker.comment c WHERE c.tracker_id = ?1 AND c.auto_generated = false ORDER BY c.created_on DESC ",
            countQuery = "SELECT c.* FROM tracker.comment c WHERE c.tracker_id = ?1 AND c.auto_generated = false ORDER BY c.created_on DESC ", 
            nativeQuery = true)
    Page<Comment> searchWithoutAutoComments(Integer trackerId, Pageable pageable);

    default PageListDTO searchWithoutAutoComments(Integer trackerId, SearchFilterDTO filter) {
        final Page<Comment> result = this.searchWithoutAutoComments(trackerId, this.page(filter));
        return new PageListDTO(result.getTotalPages(), result.getContent());
    }
    
    @Query(value = "SELECT EXISTS( SELECT c.* FROM tracker.comment c WHERE c.text = ?1 AND c.tracker_id = ?2 )",             
            nativeQuery = true)
    Boolean existsUpdateLog(String text, Integer trackerId);
 
    
}
