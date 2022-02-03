package br.edu.ufcg.virtus.tracker.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ufcg.virtus.common.controller.CrudBaseController;
import br.edu.ufcg.virtus.common.dto.PageListDTO;
import br.edu.ufcg.virtus.common.dto.SearchFilterDTO;
import br.edu.ufcg.virtus.common.exception.BusinessException;
import br.edu.ufcg.virtus.common.security.authorization.Authorization;
import br.edu.ufcg.virtus.common.security.authorization.ReadPermission;
import br.edu.ufcg.virtus.common.security.authorization.UpdatePermission;
import br.edu.ufcg.virtus.common.util.JSonUtil;
import br.edu.ufcg.virtus.tracker.dto.TrackerDTO;
import br.edu.ufcg.virtus.tracker.dto.TransitionDTO;
import br.edu.ufcg.virtus.tracker.dto.search.TrackerRelatedDTO;
import br.edu.ufcg.virtus.tracker.dto.search.TrackerSearchDTO;
import br.edu.ufcg.virtus.tracker.dto.search.TrackerSearchFilterDTO;
import br.edu.ufcg.virtus.tracker.dto.search.TrackerSearchMetricsDTO;
import br.edu.ufcg.virtus.tracker.dto.search.TrackerSearchWithMetricsDTO;
import br.edu.ufcg.virtus.tracker.dto.simple.TrackerSimpleDTO;
import br.edu.ufcg.virtus.tracker.model.Tracker;
import br.edu.ufcg.virtus.tracker.model.TrackerMetrics;
import br.edu.ufcg.virtus.tracker.service.TrackerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("trackers")
@Authorization("tracker")
@Api(value = "tracker", tags = "tracker-controller")
public class TrackerController extends CrudBaseController<Tracker, Integer, TrackerDTO> {

    @Autowired
    private TrackerService service;

    @Override
    protected TrackerService getService() {
        return this.service;
    }

    @Override
    protected Class<TrackerSearchDTO> getSearchDTOClass() {
        return TrackerSearchDTO.class;
    }

    @ApiOperation(value = "Update a status of a tracker")
    @PutMapping("/{trackerId}/status/{statusId}")
    @UpdatePermission
    public ResponseEntity<TrackerSimpleDTO> updateStatus(HttpServletRequest request,
                                                         @Valid @PathVariable Integer trackerId,
                                                         @Valid @PathVariable Integer statusId,
                                                         @RequestBody TransitionDTO transitionDTO) throws BusinessException {
        Tracker tracker = this.getService().updateStatus(trackerId, statusId, transitionDTO);
        TrackerSimpleDTO trackerSimpleDTO = this.mapTo(tracker, TrackerSimpleDTO.class);
        return new ResponseEntity<>(trackerSimpleDTO, HttpStatus.OK);
    }

    @GetMapping(params = { "statusId", "filter", "metrics" })
    @ReadPermission
    public ResponseEntity<TrackerSearchWithMetricsDTO> getTrackerByIdStatus(HttpServletRequest request,
            @RequestParam(name = "statusId") Integer statusId,
            @RequestParam(name = "dueDateOrder") Optional<String> dueDateOrder,
            @RequestParam(name = "filter") String filterJSon,
            @RequestParam(name = "metrics") String metricsJson) {
        final TrackerSearchFilterDTO filter = JSonUtil.fromJSon(filterJSon, TrackerSearchFilterDTO.class);
        final TrackerSearchMetricsDTO metrics = JSonUtil.fromJSon(metricsJson, TrackerSearchMetricsDTO.class);
        final TrackerMetrics trackerMetrics = this.service.getTrackersByFilterWithMetrics(statusId, null, filter, metrics, dueDateOrder);
        final TrackerSearchWithMetricsDTO trackerSearchWithMetricsDTO = new TrackerSearchWithMetricsDTO(trackerMetrics);
        trackerSearchWithMetricsDTO.setTrackers(this.toList(trackerMetrics.getTrackers(), TrackerSearchDTO.class));
        return new ResponseEntity<>(trackerSearchWithMetricsDTO, HttpStatus.OK);
    }

    @GetMapping("/related-trackers")
	@ReadPermission
	public PageListDTO searchForRelatedTrackers(HttpServletRequest request,
			@RequestParam(name = "trackerModelId") Integer trackerModelId,
			@RequestParam(name = "trackerId", required = false) Integer trackerId,
			@RequestParam(name = "trackerTitle", required = false) String trackerTitle, SearchFilterDTO filter) {
		PageListDTO pageListDTO = this.service.getTrackersByIdOrName(trackerId, trackerTitle, trackerModelId, filter);
		List<Tracker> trackers = (List<Tracker>) pageListDTO.getItems();
		pageListDTO.setItems(this.toList(trackers, TrackerRelatedDTO.class));
		return pageListDTO;
	}

    @GetMapping("/global-metrics-value")
    @ReadPermission
    public ResponseEntity<TrackerSearchWithMetricsDTO> getTrackerByIdStatus(HttpServletRequest request,
            @RequestParam(name = "trackerModelId") Integer trackerModelId,
            @RequestParam(name = "filter") String filterJSon,
            @RequestParam(name = "metrics") String metricsJson) {
        final TrackerSearchFilterDTO filter = JSonUtil.fromJSon(filterJSon, TrackerSearchFilterDTO.class);
        final TrackerSearchMetricsDTO metrics = JSonUtil.fromJSon(metricsJson, TrackerSearchMetricsDTO.class);
        final TrackerMetrics trackerGlobalMetrics = this.service.getTrackersByFilterWithMetrics(null, trackerModelId, filter, metrics);
        final TrackerSearchWithMetricsDTO trackerSearchWithMetricsDTO = new TrackerSearchWithMetricsDTO(trackerGlobalMetrics);
        return new ResponseEntity<>(trackerSearchWithMetricsDTO, HttpStatus.OK);
    }
}
