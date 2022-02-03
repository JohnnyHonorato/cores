package br.edu.ufcg.virtus.tracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ufcg.virtus.common.exception.BusinessException;
import br.edu.ufcg.virtus.common.service.CrudService;
import br.edu.ufcg.virtus.tracker.dto.TransitionDTO;
import br.edu.ufcg.virtus.tracker.model.Status;
import br.edu.ufcg.virtus.tracker.model.TrackerModel;
import br.edu.ufcg.virtus.tracker.model.Transition;
import br.edu.ufcg.virtus.tracker.repository.TransitionRepository;

@Service
public class TransitionService extends CrudService<Transition, Integer> {

    @Autowired
    private TransitionRepository repository;

    @Autowired
    private StatusService statusService;

    @Override
    protected TransitionRepository getRepository() {
        return this.repository;
    }

    public boolean isAllowedTransition(TransitionDTO model) throws BusinessException {
        final Transition transition = this.getRepository().findBySourceIdAndTargetId(
                model.getSource().getId(), model.getTarget().getId());
        return transition != null;
    }

    public void insertMultipleTransitionsFromTrackerModel(TrackerModel model) throws BusinessException {
        if (model.getTransitions() != null && !model.getTransitions().isEmpty()) {
            for (final Transition transition : model.getTransitions()) {
                Status source = this.statusService.getStatusByIdTrackerModelAndName(model.getId(),
                        transition.getSource().getName());
                Status target = this.statusService.getStatusByIdTrackerModelAndName(model.getId(),
                        transition.getTarget().getName());
                if (source!=null && target!=null) {
                    transition.setSource(source);
                    transition.setTarget(target);
                    transition.setTrackerModel(model);
                    this.insert(transition);
                }
            }
        }
    }

}
