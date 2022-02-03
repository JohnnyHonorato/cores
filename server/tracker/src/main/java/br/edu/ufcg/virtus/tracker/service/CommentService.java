package br.edu.ufcg.virtus.tracker.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.edu.ufcg.virtus.common.dto.PageListDTO;
import br.edu.ufcg.virtus.common.dto.SearchFilterDTO;
import br.edu.ufcg.virtus.common.exception.BusinessException;
import br.edu.ufcg.virtus.common.model.LoggedUser;
import br.edu.ufcg.virtus.common.service.CrudService;
import br.edu.ufcg.virtus.common.util.SecurityContextUtil;
import br.edu.ufcg.virtus.tracker.model.Comment;
import br.edu.ufcg.virtus.tracker.repository.CommentRepository;
import br.edu.ufcg.virtus.tracker.repository.CoreRepository;

@Service
public class CommentService extends CrudService<Comment, Integer> {
    
    @Autowired
    private CommentRepository repository;
    
    @Autowired
    private CoreRepository coreRepository;

    @Override
    protected CommentRepository getRepository() {
        return this.repository;
    }
    
    @Override
    public Comment getOne(Integer id) throws BusinessException {
        Comment comment = super.getOne(id);
        if (comment.getCreatedBy() != null) {
            comment.setUser(this.coreRepository.getPersonNameByUserId(comment.getCreatedBy()));
            comment.setUsername(this.coreRepository.getPersonUsernameById(comment.getCreatedBy()));
        }
        return comment;
    }
    
    public Boolean existsUpdateLog(String text, Integer trackerId) throws BusinessException {
        return repository.existsUpdateLog(text, trackerId);
    }
    
    @SuppressWarnings("unchecked")
    public PageListDTO search(Integer trackerId, SearchFilterDTO filter) {
        PageListDTO result = this.repository.search(trackerId, filter);
        
        List<Comment> comments = (List<Comment>) result.getItems();
        for(Comment comment : comments) {
            if (comment.getCreatedBy() != null) {
                comment.setUser(this.coreRepository.getPersonNameByUserId(comment.getCreatedBy()));
                comment.setUsername(this.coreRepository.getPersonUsernameById(comment.getCreatedBy()));
            }
        }
        
        return result;
    }
    
    @SuppressWarnings("unchecked")
    public PageListDTO searchWithoutAutoComments(Integer trackerId, SearchFilterDTO filter) {
        PageListDTO result = this.repository.searchWithoutAutoComments(trackerId, filter);
        
        List<Comment> comments = (List<Comment>) result.getItems();
        for(Comment comment : comments) {
            if (comment.getCreatedBy() != null) {
                comment.setUser(this.coreRepository.getPersonNameByUserId(comment.getCreatedBy()));
                comment.setUsername(this.coreRepository.getPersonUsernameById(comment.getCreatedBy()));
            }
        }
        
        return result;
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public PageListDTO search(SearchFilterDTO filter) {
        PageListDTO result = super.search(filter);
        
        List<Comment> comments = (List<Comment>) result.getItems();
        for(Comment comment : comments) {
            comment.setUser(this.coreRepository.getPersonNameByUserId(comment.getCreatedBy()));
            comment.setUsername(this.coreRepository.getPersonUsernameById(comment.getCreatedBy()));
        }
        
        return result;
    }
    
    @Override
    protected void validateUpdate(Comment model) throws BusinessException {
        LoggedUser loggedUser = SecurityContextUtil.getLoggedUser();
        String loggedInUsername = loggedUser == null ? null : loggedUser.getUsername();
        if (!loggedInUsername.equalsIgnoreCase(model.getUsername())) {
            throw new BusinessException("comment.updater-different-from-creator", HttpStatus.BAD_REQUEST);
        }
    }
    
    @Override
    protected void validateDelete(Integer id) throws BusinessException {
        Comment comment = this.getOne(id);
        LoggedUser loggedUser = SecurityContextUtil.getLoggedUser();
        String loggedInUsername = loggedUser == null ? null : loggedUser.getUsername();
        if (!loggedInUsername.equalsIgnoreCase(comment.getUsername())) {
            throw new BusinessException("comment.remover-different-from-creator", HttpStatus.BAD_REQUEST);
        }
    }

}
