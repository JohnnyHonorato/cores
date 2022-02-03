package br.edu.ufcg.virtus.tracker.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ufcg.virtus.common.controller.CrudBaseController;
import br.edu.ufcg.virtus.common.dto.ModelDTO;
import br.edu.ufcg.virtus.common.dto.PageListDTO;
import br.edu.ufcg.virtus.common.dto.SearchFilterDTO;
import br.edu.ufcg.virtus.common.security.authorization.ReadPermission;
import br.edu.ufcg.virtus.common.util.JSonUtil;
import br.edu.ufcg.virtus.tracker.dto.CommentDTO;
import br.edu.ufcg.virtus.tracker.model.Comment;
import br.edu.ufcg.virtus.tracker.service.CommentService;
import io.swagger.annotations.Api;

@RestController
@RequestMapping("trackers/{trackerId}/comments")
@Api(value = "comment", tags = "comment-controller")
public class CommentController extends CrudBaseController<Comment, Integer, CommentDTO> {

	@Autowired
	private CommentService service;

	@Override
	protected CommentService getService() {
		return this.service;
	}

	@GetMapping(value = "/without-auto-comments", params = { "filter" })
	@ReadPermission
	public ResponseEntity<ModelDTO> search(HttpServletRequest request, @PathVariable Integer trackerId,
			@RequestParam(value = "filter", required = false) String filterJSon) {
		SearchFilterDTO filter = JSonUtil.fromJSon(filterJSon, SearchFilterDTO.class);
		filter = this.customSearchFilter(request, filter);
		final PageListDTO response = this.service.searchWithoutAutoComments(trackerId, filter);
		response.setItems(this.toSearchListDTO(response.getItems()));

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@Override
	protected PageListDTO searchInService(HttpServletRequest request, SearchFilterDTO filter) {
		final Integer trackerId = Integer.valueOf(this.getPathVariable(request, "trackerId"));
		return this.service.search(trackerId, filter);
	}

}
