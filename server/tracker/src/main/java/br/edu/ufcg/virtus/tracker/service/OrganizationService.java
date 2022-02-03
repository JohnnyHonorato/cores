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
import br.edu.ufcg.virtus.tracker.dto.OrganizationDTO;
import br.edu.ufcg.virtus.tracker.enums.AttributeType;

@Service
public class OrganizationService extends BaseService implements IRelatableAttributeService {

    @Value("${wso2.core-api-key}")
    private String apiKey;

    @Value("${server.url}")
    private String url;

    @Autowired
    private AttributeValueService attributeValueService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private AttributeService attributeService;

    public ResponseEntity<String> search(HttpServletRequest request, String filter) throws BusinessException {
        return HttpUtil.list(request, this.getBaseUrl(), this.apiKey, filter);
    }

    public ResponseEntity<String> getOne(HttpServletRequest request, Integer id) throws BusinessException {
        final String path = this.getBaseUrl() + "/" + id;
        ResponseEntity<String> response = HttpUtil.getOne(request, path, this.apiKey);
        if (!response.hasBody()) {
            response = this.treatOrganizationNotFound(id, response);
        }
        return response;
    }

    private ResponseEntity<String> treatOrganizationNotFound(Integer id, ResponseEntity<String> response) throws BusinessException {
        final OrganizationDTO deletedOrganization = new OrganizationDTO();
        deletedOrganization.setId(id);
        deletedOrganization.setDeleted(true);
        this.clearRelatedAttributeValues(AttributeType.ORGANIZATION, id);
        return new ResponseEntity<String>(JSonUtil.toJSon(deletedOrganization), response.getHeaders(), response.getStatusCode());
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
        return this.url + "core/v1/organizations";
    }

}
