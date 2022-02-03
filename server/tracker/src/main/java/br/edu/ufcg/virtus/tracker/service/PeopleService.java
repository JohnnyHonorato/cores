package br.edu.ufcg.virtus.tracker.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.edu.ufcg.virtus.common.exception.BusinessException;
import br.edu.ufcg.virtus.common.service.BaseService;
import br.edu.ufcg.virtus.common.util.HttpUtil;
import br.edu.ufcg.virtus.common.util.JSonUtil;
import br.edu.ufcg.virtus.tracker.dto.PeopleDTO;
import br.edu.ufcg.virtus.tracker.enums.AttributeType;

@Service
public class PeopleService extends BaseService implements IRelatableAttributeService {

    @Value("${wso2.core-api-key}")
    private String apiKey;

    @Value("${server.url}")
    private String url;

    @Autowired
    AttributeValueService attributeValueService;

    @Autowired
    CommentService commentService;

    public ResponseEntity<String> search(HttpServletRequest request, String filter) throws BusinessException {
        return HttpUtil.list(request, this.getBaseUrl(), this.apiKey, filter);
    }

    public ResponseEntity<String> getOne(HttpServletRequest request, Integer id) throws BusinessException {
        final String path = this.getBaseUrl() + "/" + id;
        ResponseEntity<String> response = HttpUtil.getOne(request, path, this.apiKey);
        if (!response.hasBody()) {
            response = this.treatPeopleNotFound(id, response);
        }
        return response;
    }

    public ResponseEntity<String> getAllByModule(HttpServletRequest request, Integer moduleId, String filter) throws BusinessException {
        final String path = this.getBaseUrl() + "/module/" + moduleId;
        return HttpUtil.list(request, path, this.apiKey, filter);
    }

    public ResponseEntity<String> getAllByOrganization(HttpServletRequest request, Integer organizationId, String filter) throws BusinessException {
        final String path = String.format("%s/organizations/%d", this.getBaseUrl(), organizationId);
        return HttpUtil.list(request, path, this.apiKey, filter);
    }

    private ResponseEntity<String> treatPeopleNotFound(Integer id, ResponseEntity<String> response) throws BusinessException {
        final PeopleDTO deletedPeople = new PeopleDTO();
        deletedPeople.setId(id);
        deletedPeople.setDeleted(true);
        this.clearRelatedAttributeValues(AttributeType.PEOPLE, id);
        return new ResponseEntity<String>(JSonUtil.toJSon(deletedPeople), response.getHeaders(), response.getStatusCode());
    }

    @Override
    public AttributeValueService getAttributeValueService() {
        return this.attributeValueService;
    }

    @Override
    public CommentService getCommentService() {
        return this.commentService;
    }

    private String getBaseUrl() {
        return this.url + "core/v1/people";
    }
}
