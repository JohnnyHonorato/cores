package br.edu.ufcg.virtus.business.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.edu.ufcg.virtus.business.dto.OrganizationDTO;
import br.edu.ufcg.virtus.common.exception.BusinessException;
import br.edu.ufcg.virtus.common.service.BaseService;
import br.edu.ufcg.virtus.common.util.HttpUtil;
import br.edu.ufcg.virtus.common.util.JSonUtil;
import lombok.SneakyThrows;

@Service
public class OrganizationService extends BaseService {

    @Value("${wso2.core-api-key}")
    private String apiKey;

    @Value("${server.url}")
    private String url;

    public ResponseEntity<String> search(HttpServletRequest request) throws BusinessException {
        return HttpUtil.list(request, this.getBaseUrl(), this.apiKey, null);
    }

    @SneakyThrows
    public ResponseEntity<String> search(HttpServletRequest request, String filter) throws BusinessException {
        return HttpUtil.list(request, this.getBaseUrl(), this.apiKey, filter);
    }

    public ResponseEntity<String> getOne(HttpServletRequest request, Integer id) throws BusinessException {
        final String newUrl = this.getBaseUrl() + "/" + id;
        return HttpUtil.getOne(request, newUrl, this.apiKey);
    }

    public ResponseEntity<String> update(HttpServletRequest request, Integer id, OrganizationDTO org) throws BusinessException {
        final String bodyMap = JSonUtil.toJSon(org);
        final String newUrl = this.getBaseUrl() + "/" + id;
        return HttpUtil.put(request, newUrl, bodyMap, this.apiKey);
    }

    public ResponseEntity<String> insert(HttpServletRequest request, OrganizationDTO org) throws BusinessException {
        final String bodyMap = JSonUtil.toJSon(org);
        return HttpUtil.post(request, this.getBaseUrl(), bodyMap, this.apiKey);
    }

    public ResponseEntity<String> delete(HttpServletRequest request, Integer id) throws BusinessException {
        final String newUrl = this.getBaseUrl() + "/" + id;
        return HttpUtil.delete(request, newUrl, this.apiKey);
    }

    public ResponseEntity<String> getOrganizationByCNPJ(HttpServletRequest request, String cnpj) throws BusinessException {
        final String newUrl = this.getBaseUrl() + "/cnpj/" + cnpj;
        return HttpUtil.getOne(request, newUrl, this.apiKey);
    }

    public ResponseEntity<String> deleteLead(HttpServletRequest request, Integer id, Integer peopleId)
            throws BusinessException {
        final String newUrl = this.getBaseUrl() + "/" + id + "/people/" + peopleId;
        return HttpUtil.delete(request, newUrl, this.apiKey);
    }

    public ResponseEntity<String> deleteContactInfo(HttpServletRequest request, Integer contactInfoId) throws BusinessException {
        final String newUrl = this.url + "core/v1/contacts-info/" + contactInfoId;
        return HttpUtil.delete(request, newUrl, this.apiKey);
    }

    private String getBaseUrl() {
        return this.url + "core/v1/organizations";
    }
}