package br.edu.ufcg.virtus.business.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.edu.ufcg.virtus.business.dto.LeadDTO;
import br.edu.ufcg.virtus.common.exception.BusinessException;
import br.edu.ufcg.virtus.common.service.BaseService;
import br.edu.ufcg.virtus.common.util.HttpUtil;
import br.edu.ufcg.virtus.common.util.JSonUtil;

@Service
public class LeadService extends BaseService {

    @Value("${wso2.core-api-key}")
    private String apiKey;

    @Value("${server.url}")
    private String url;

    public ResponseEntity<String> search(HttpServletRequest request) throws BusinessException {
        return HttpUtil.list(request, this.getBaseUrl(), this.apiKey, null);
    }

    public ResponseEntity<String> search(HttpServletRequest request, String filter) throws BusinessException {
        return HttpUtil.list(request, this.getBaseUrl(), this.apiKey, filter);
    }

    public ResponseEntity<String> getOne(HttpServletRequest request, Integer id) throws BusinessException {
        final String newUrl = this.getBaseUrl() + "/" + id;
        return HttpUtil.getOne(request, newUrl, this.apiKey);
    }

    public ResponseEntity<String> update(HttpServletRequest request, Integer id, LeadDTO lead) throws BusinessException {
        final String bodyMap = JSonUtil.toJSon(lead);
        final String newUrl = this.getBaseUrl() + "/" + id;
        return HttpUtil.put(request, newUrl, bodyMap, this.apiKey);
    }

    public ResponseEntity<String> insert(HttpServletRequest request, LeadDTO lead) throws BusinessException {
        final String bodyMap = JSonUtil.toJSon(lead);
        return HttpUtil.post(request, this.getBaseUrl(), bodyMap, this.apiKey);
    }

    public ResponseEntity<String> delete(HttpServletRequest request, Integer id) throws BusinessException {
        final String newUrl = this.getBaseUrl() + "/" + id;
        return HttpUtil.delete(request, newUrl, this.apiKey);
    }

    public ResponseEntity<String> deleteOrganization(HttpServletRequest request, Integer id,  Integer organizationId) throws BusinessException {
        final String newUrl = this.getBaseUrl() + "/" + id + "/organizations/" + organizationId;
        return HttpUtil.delete(request, newUrl, this.apiKey);
    }

    public ResponseEntity<String> deleteContactInfo(HttpServletRequest request,  Integer contactInfoId) throws BusinessException {
        final String newUrl = this.url + "core/v1/contacts-info/" + contactInfoId;
        return HttpUtil.delete(request, newUrl, this.apiKey);
    }

    private String getBaseUrl() {
        return this.url + "core/v1/people";
    }

}

