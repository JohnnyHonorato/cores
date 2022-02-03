package br.edu.ufcg.virtus.tracker.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.edu.ufcg.virtus.common.dto.FilterDTO;
import br.edu.ufcg.virtus.common.dto.PageListDTO;
import br.edu.ufcg.virtus.common.dto.SearchFilterDTO;
import br.edu.ufcg.virtus.common.exception.BusinessException;
import br.edu.ufcg.virtus.common.service.CrudService;
import br.edu.ufcg.virtus.tracker.dto.TransitionDTO;
import br.edu.ufcg.virtus.tracker.dto.search.TrackerSearchFilterDTO;
import br.edu.ufcg.virtus.tracker.dto.search.TrackerSearchMetricsDTO;
import br.edu.ufcg.virtus.tracker.model.*;
import br.edu.ufcg.virtus.tracker.repository.CommentRepository;
import br.edu.ufcg.virtus.tracker.repository.CoreRepository;
import br.edu.ufcg.virtus.tracker.repository.FilePathRepository;
import br.edu.ufcg.virtus.tracker.repository.StatusRepository;
import br.edu.ufcg.virtus.tracker.repository.TrackerRepository;
import br.edu.ufcg.virtus.tracker.repository.TrackerSearchRepository;
import br.edu.ufcg.virtus.tracker.repository.TransitionRepository;
import br.edu.ufcg.virtus.tracker.service.file.FileService;

@Service
public class TrackerService extends CrudService<Tracker, Integer> {

	@Autowired
	private TrackerRepository repository;

	@Autowired
	private TrackerSearchRepository trackerSearchRepository;

	@Autowired
	private AttributeValueService attributeValueService;

	@Autowired
	private AttributeService attributeService;

	@Autowired
	private StatusRepository statusRepository;

	@Autowired
	private TransitionRepository transitionsRepository;

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private CoreRepository coreRepository;

	@Autowired
	private FilePathRepository filePathRepository;

	@Autowired
	private ChecklistService checklistService;

	@Autowired
	private AssigneeService assigneeService;

	@Autowired
	private TransitionService transitionService;

	@Autowired
	private FileService fileService;
	
	@Autowired
	private LinkedTrackerService linkedTrackerService;

	private final int STRING_MAX_LENGTH = 255;

	@Override
	protected TrackerRepository getRepository() {
		return this.repository;
	}

	@Override
	public Tracker insert(Tracker model) throws BusinessException {
		final Tracker tracker = super.insert(model);
		this.attributeValueService.saveAttributeValues(model);
		this.checklistService.saveChecklists(model);
		this.assigneeService.saveAssignees(model);
		this.saveAttachments(model);
		return tracker;
	}

	@Override
	public void update(Integer id, Tracker model) throws BusinessException {
		this.attributeValueService.saveAttributeValues(model);
		this.saveAutomaticComments(model);
		this.checklistService.saveChecklists(model);
		this.assigneeService.saveAssignees(model);
		this.saveAttachments(model);
		super.update(id, model);
	}

	@Override
	public Tracker getOne(Integer id) throws BusinessException {
		final Tracker tracker = super.getOne(id);
		if ((tracker.getAssignees() != null) && !tracker.getAssignees().isEmpty()) {
			for (final Assignee assignee : tracker.getAssignees()) {
				assignee.setName(this.coreRepository.getPersonNameById(assignee.getPeopleId()));
			}
		}		
		tracker.getAttachments().sort(Comparator.comparing(FilePath::getCreatedOn).reversed());

		return tracker;
	}

	@Override
	protected void validateInsert(Tracker model) throws BusinessException {
		this.validateMaxLengthOfFields(model);
	}

	private boolean repeatedFileName(List<FilePath> attachments) {
		for (int i = 0; i < attachments.size(); i++) {
			FilePath file = attachments.get(i);
			List<FilePath> subList = attachments.subList(i + 1, attachments.size());
			for (final FilePath otherFile : subList) {
				if (file.getOriginalName().equalsIgnoreCase(otherFile.getOriginalName())) {
					return true;
				}
			}
		}
		return false;

	}

	@Override
	protected void validateUpdate(Tracker model) throws BusinessException {
		this.validateMaxLengthOfFields(model);
	}

	private void validateMaxLengthOfFields(Tracker model) throws BusinessException {
		if (model.getTitle().length() > this.STRING_MAX_LENGTH) {
			throw new BusinessException("tracker.title.field.max-length", HttpStatus.BAD_REQUEST);
		}

		if ((model.getDescription() != null) && (model.getDescription().length() > this.STRING_MAX_LENGTH)) {
			throw new BusinessException("tracker.description.field.max-length", HttpStatus.BAD_REQUEST);
		}
	}

	public Tracker updateStatus(Integer id, Integer statusId, TransitionDTO transition) throws BusinessException {
		if (!this.repository.existsById(id)) {
			throw new BusinessException("tracker.not.found", HttpStatus.BAD_REQUEST);
		}

		if (!this.transitionService.isAllowedTransition(transition)) {
			throw new BusinessException("transition.not.allowed", HttpStatus.BAD_REQUEST);
		}

		if (!this.statusRepository.existsById(statusId)) {
			throw new BusinessException("tracker.status.not.found", HttpStatus.BAD_REQUEST);
		}

		final Tracker trackerDB = this.repository.findById(id).get();
		final Status trackerStatusDB = this.statusRepository.findById(statusId).get();
		final Transition trackerTransitionsDB = this.transitionsRepository
				.findBySourceIdAndTargetId(transition.getSource().getId(), transition.getTarget().getId());

		if (!this.transitionsRepository.existsById(trackerTransitionsDB.getId())) {
			throw new BusinessException("tracker.status.not.found", HttpStatus.BAD_REQUEST);
		}

		this.saveAutomaticCommentsForStatus(trackerDB, trackerStatusDB);
		trackerDB.setStatus(trackerStatusDB);
		trackerDB.setTransition(trackerTransitionsDB);

		return this.repository.save(trackerDB);
	}

	@Override
	protected void validateDelete(Integer id) throws BusinessException {
		if (!this.repository.existsById(id)) {
			throw new BusinessException("tracker.not.found", HttpStatus.BAD_REQUEST);
		}
	}

	private void saveAutomaticComments(Tracker tracker) {
		final Tracker trackerDB = this.repository.findById(tracker.getId()).get();

		String text = "";

		if (!tracker.getTitle().equals(trackerDB.getTitle())) {
			text += "Mudou o título de " + trackerDB.getTitle() + " para " + tracker.getTitle() + ".";
		}

		if ((tracker.getDescription() != null) && !tracker.getDescription().equals(trackerDB.getDescription())) {
			if (!text.isBlank()) {
				text += "\n ";
			}

			if ((trackerDB.getDescription() == null) || trackerDB.getDescription().equals("")) {
				text += "Adicionou a descrição " + tracker.getDescription() + ".";
			} else if (tracker.getDescription().equals("")) {
				text += "Removeu a descrição " + trackerDB.getDescription() + ".";
			} else {
				text += "Mudou a descrição de " + trackerDB.getDescription() + " para " + tracker.getDescription()
						+ ".";
			}
		}

		if (!text.isBlank()) {
			final Comment comment = new Comment();
			comment.setText(text);
			comment.setAutoGenerated(true);
			comment.setTracker(trackerDB);
			this.commentRepository.save(comment);
		}
	}

	private void saveAutomaticCommentsForStatus(Tracker trackerDB, Status newStatus) {
		final String text = "Mudou o status de " + trackerDB.getStatus().getName() + " para " + newStatus.getName()
				+ ".";
		final Comment comment = new Comment();
		comment.setText(text);
		comment.setAutoGenerated(true);
		comment.setTracker(trackerDB);
		this.commentRepository.save(comment);
	}

	public List<Tracker> searchTrackerByIdStatus(Integer statusId, Optional<String> dueDateOrder) {
		final String orderByDueDate = dueDateOrder.orElse("ASC");

		List<Tracker> trackers;

		if ("ASC".equals(orderByDueDate)) {
			trackers = this.repository.findByStatusIdOrderByDueDateAsc(statusId);
		} else {
			trackers = this.repository.findByStatusIdOrderByDueDateDesc(statusId);
		}
		return trackers;
	}

	public TrackerMetrics getTrackersByFilterWithMetrics(Integer statusId, Integer trackerModelId,
			TrackerSearchFilterDTO filters, TrackerSearchMetricsDTO metrics) {
		final List<Tracker> resultFiltered = this.searchTrackerByFilter(statusId, trackerModelId, filters,
				Optional.empty());
		final TrackerMetrics trackerMetrics = new TrackerMetrics(metrics.getMetricField(), metrics.getMetric(),
				metrics.getAttributeType(), resultFiltered);
		trackerMetrics.setValueComplement(this.attributeService.getCurrencyByAttributeNameAndTrackerModel(
				trackerModelId, metrics.getMetricField() != null ? metrics.getMetricField() : ""));
		return trackerMetrics;
	}

	public TrackerMetrics getTrackersByFilterWithMetrics(Integer statusId, Integer trackerModelId,
			TrackerSearchFilterDTO filters, TrackerSearchMetricsDTO metrics, Optional<String> dueDateOrder) {
		final List<Tracker> resultFiltered = this.searchTrackerByFilter(statusId, trackerModelId, filters,
				dueDateOrder);
		for (final Tracker tracker : resultFiltered) {
			tracker.setNumberComments(tracker.getComment().size());
			tracker.setNumberAttachments(tracker.getAttachments().size());
			if ((tracker.getAssignees() != null) && !tracker.getAssignees().isEmpty()) {
				for (final Assignee assignee : tracker.getAssignees()) {
					assignee.setName(this.coreRepository.getPersonNameById(assignee.getPeopleId()));
				}
			}
			tracker.getAttachments().sort(Comparator.comparing(FilePath::getCreatedOn).reversed());
		}

		final TrackerMetrics trackerMetrics = new TrackerMetrics(metrics.getMetricField(), metrics.getMetric(),
				metrics.getAttributeType(), resultFiltered);
		return trackerMetrics;
	}

	private List<Tracker> searchTrackerByFilter(Integer statusId, Integer trackerModelId,
			TrackerSearchFilterDTO filters, Optional<String> dueDateOrder) {
		List<Tracker> result = new ArrayList<>();
		result = this.trackerSearchRepository.findByFilters(statusId, trackerModelId, filters,
				dueDateOrder.orElse("ASC"));
		this.filterByCustomAttributes(filters, result);
		return result;
	}

	private void filterByCustomAttributes(TrackerSearchFilterDTO filters, List<Tracker> result) {
		List<Tracker> aux = new ArrayList<>();

		if ((filters.getFilters() != null) && (filters.getFilters().size() > 0)) {
			for (final FilterDTO filter : filters.getFilters()) {
				aux = new ArrayList<>(result);
				result.clear();
				for (final Tracker tracker : aux) {
					if (tracker.getAttributesValue().stream()
							.anyMatch(att -> att.getAttribute().getTitle().equals(filter.getField())
									&& (att.getValue() != null) && att.getValue().equals(filter.getValue()))) {
						result.add(tracker);
					}
				}
			}
		}
	}

	private void saveAttachments(Tracker tracker) throws BusinessException {
		List<FilePath> files = this.filePathRepository.findAllByTrackerId(tracker.getId());
		files.addAll(tracker.getAttachments());
		if (this.repeatedFileName(files)) {
			this.fileService.deleteFiles(tracker.getAttachments());
			throw new BusinessException("tracker.repeated.filename", HttpStatus.BAD_REQUEST);
		}
		if (!this.getAttachmentsTypes(tracker).isEmpty() && this.validationAttachments(tracker, files)) {
			this.fileService.deleteFiles(tracker.getAttachments());
			throw new BusinessException("tracker.file-type-restrictions", HttpStatus.BAD_REQUEST);
		}
		if (tracker.getAttachments() != null && !tracker.getAttachments().isEmpty()) {
			for (final FilePath attachment : tracker.getAttachments()) {
				if (attachment != null) {
					attachment.setTracker(tracker);
					this.filePathRepository.save(attachment);
				}
			}
		}
	}

	private boolean validationAttachments(Tracker tracker, List<FilePath> files) {
		for (final FilePath file : files) {
			ArrayList<String> attachmentsTypes = this.getAttachmentsTypes(tracker);
			boolean contains = attachmentsTypes.contains(file.getContentType());
			if (!contains && file.getId() == null) {
				return true;
			}
		}
		return false;
	}

	private ArrayList<String> getAttachmentsTypes(Tracker tracker) {
		ArrayList<String> attachmentsTypes = new ArrayList<>();
		try {
			String[] receiveTypes = tracker.getTrackerModel().getFileTypesRestrictions().split(";");
			for (final String type : receiveTypes) {
				String[] contentTypes = type.split(":");
				attachmentsTypes.add(contentTypes[1]);
			}
			return attachmentsTypes;
		} catch (Exception e) {
			return attachmentsTypes;
		}
	}
	
	@Override
	public void delete(Integer id) throws BusinessException {
		this.linkedTrackerService.deleteTrackerLinkBySecondLink(id);
		super.delete(id);
	}

	public PageListDTO getTrackersByIdOrName(Integer trackerId, String trackerTitle, Integer trackerModelId, SearchFilterDTO filter) {
		if (trackerId != null) {
			return this.repository.searchById(trackerId, trackerModelId, filter);
		} else if (trackerTitle != null) {
			return this.repository.searchByTitle(trackerTitle, trackerModelId, filter);
		}
		return null;

	}

}
