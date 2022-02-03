package br.edu.ufcg.virtus.tracker.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ufcg.virtus.common.exception.BusinessException;
import br.edu.ufcg.virtus.common.service.CrudService;
import br.edu.ufcg.virtus.tracker.model.Assignee;
import br.edu.ufcg.virtus.tracker.model.Tracker;
import br.edu.ufcg.virtus.tracker.repository.AssigneeRepository;
import br.edu.ufcg.virtus.tracker.repository.TrackerRepository;

@Service
public class AssigneeService extends CrudService<Assignee, Integer> {

    @Autowired
    private AssigneeRepository repository;

    @Override
    protected AssigneeRepository getRepository() {
        return this.repository;
    }

    public void saveAssignees(Tracker model) throws BusinessException {
        if (model.getAssignees() != null && !model.getAssignees().isEmpty()) {
            for (final Assignee assignee : model.getAssignees()) {
                if (assignee != null) {
                    assignee.setTrackerModel(model.getTrackerModel());
                    if (assignee.getId() != null) {
                        this.update(assignee.getId(), assignee);
                    } else {
                        this.insert(assignee);
                    }
                }
            }
            if (model.getId() != null) {
                final List<Assignee> existingAssignees = this.repository.findByTrackerId(model.getId());
                final List<Assignee> deletedAssignees =
                        existingAssignees.stream()
                        .filter(existingContact -> existingContact.getId() != null &&
                        model.getAssignees().stream().noneMatch(modelContact -> modelContact.getId() == existingContact.getId())
                                ).collect(Collectors.toList());
                for (final Assignee assignee : deletedAssignees) {
                	this.repository.removeTrackerAssigneeRelationship(model.getId(), assignee.getId());
                    this.delete(assignee.getId());
                }
            }
        }

    }

}
