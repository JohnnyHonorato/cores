package br.edu.ufcg.virtus.tracker.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ufcg.virtus.common.exception.BusinessException;
import br.edu.ufcg.virtus.common.service.CrudService;
import br.edu.ufcg.virtus.tracker.model.Checklist;
import br.edu.ufcg.virtus.tracker.model.ChecklistItem;
import br.edu.ufcg.virtus.tracker.model.Tracker;
import br.edu.ufcg.virtus.tracker.repository.ChecklistItemRepository;
import br.edu.ufcg.virtus.tracker.repository.ChecklistRepository;

@Service
public class ChecklistService extends CrudService<Checklist, Integer> {

    @Autowired
    private ChecklistRepository repository;

    @Autowired
    private ChecklistItemRepository checklistItemRepository;

    @Override
    protected ChecklistRepository getRepository() {
        return this.repository;
    }

    protected void preDelete(Integer checklistId) throws BusinessException {
        Checklist checklistDB = this.repository.findById(checklistId).get();
        for (final ChecklistItem checklistItem : checklistDB.getItems()) {
            this.checklistItemRepository.deleteById(checklistItem.getId());
        }
    }
    
    @Transactional
    public void deleteChecklistItem(Integer checklistItemId) throws BusinessException {
        this.checklistItemRepository.deleteById(checklistItemId);
    }
    
    public void saveChecklists(Tracker model) throws BusinessException {
		if (model.getChecklists() != null && !model.getChecklists().isEmpty()) {
			for (final Checklist checklist : model.getChecklists()) {
				if (checklist.getTracker() == null) {
					checklist.setTracker(model);
				}
				Checklist checklistDB = this.insert(checklist);
				if (checklist.getItems() != null && !checklist.getItems().isEmpty()) {
					for (final ChecklistItem checklistItem : checklist.getItems()) {
						if (checklistItem.getChecklist() == null) {
							checklistItem.setChecklist(checklistDB);
						}
						this.checklistItemRepository.save(checklistItem);
					}
				}
			}
		}
	}
    
	public void updateValueChecklistItem(ChecklistItem checklistItem) {
		this.checklistItemRepository.updateChecklistItemValue(checklistItem.getId(), checklistItem.isDone());
	}

}
