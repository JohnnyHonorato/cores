package br.edu.ufcg.virtus.tracker.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.edu.ufcg.virtus.tracker.dto.search.TrackerSearchFilterDTO;
import br.edu.ufcg.virtus.tracker.model.Tracker;

@Repository
public class TrackerSearchRepository {

    @PersistenceContext
    EntityManager entityManager;

    public List<Tracker> findByFilters(Integer statusId, Integer trackerModelId, TrackerSearchFilterDTO filter,
            String dueDateOrder) {
        final String queryString = this.generateStringQuery(statusId, trackerModelId, filter, dueDateOrder);
        final Query query = this.generateQuery(queryString, statusId, trackerModelId, filter);

        return query.getResultList();
    }

    private String generateStringQuery(Integer statusId, Integer trackerModelId, TrackerSearchFilterDTO filter, String dueDateOrder) {
        final StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("SELECT DISTINCT tracker.* FROM tracker.tracker as tracker ");

        this.getJoinString(filter, queryBuilder);

        if (trackerModelId != null) {
            queryBuilder.append("WHERE tracker.tracker_model_id=:trackerModelId ");
        } else {
            queryBuilder.append("WHERE tracker.status_id=:statusId ");
        }

        this.addWhereConditions(filter, queryBuilder);

        queryBuilder.append("ORDER BY tracker.due_date ");
        queryBuilder.append(dueDateOrder);
        queryBuilder.append(" NULLS LAST");
        
        return queryBuilder.toString();
    }

    private void getJoinString(TrackerSearchFilterDTO filter, StringBuilder queryBuilder) {
        if (filter.getMemberId() != null) {
            queryBuilder.append(
                    "INNER JOIN tracker.tracker_assignee as tracker_assignee ON tracker_assignee.tracker_id=tracker.id "
                            + "INNER JOIN tracker.assignee as assignee ON assignee.id=tracker_assignee.assignee_id ");
        }
        
        if (filter.getTags() != null && filter.getTags().size() > 0) {
        	queryBuilder.append(
        			"INNER JOIN tracker.tracker_tag as tracker_tag ON tracker_tag.tracker_id=tracker.id "
        					+ "INNER JOIN tracker.tag as tag ON tag.id=tracker_tag.tag_id ");
        }
    }

    private void addWhereConditions(TrackerSearchFilterDTO filter, StringBuilder queryBuilder) {
        if (filter.getMemberId() != null) {
            queryBuilder.append("AND assignee.people_id=:memberId ");
        }

        if (filter.getStartDate() != null) {
            queryBuilder.append("AND tracker.due_date >= :startDate ");
        }

        if (filter.getEndDate() != null) {
            queryBuilder.append("AND tracker.due_date <= :endDate ");
        }
        
        if (filter.getTags() != null && filter.getTags().size() > 0) {
        	queryBuilder.append("AND tag.id IN ( ");
        	if(filter.getTags().size() == 1) {
        		String queryWithDinamicParameter = String.format(":tag%d ", filter.getTags().size() - 1);
				queryBuilder.append(queryWithDinamicParameter + ")");
        	} else {
        		for (int i=0; i < filter.getTags().size(); i++) {
    				String queryWithDinamicParameter = String.format(":tag%d ", i);
    				queryBuilder.append(queryWithDinamicParameter);
    				
    				if (i < filter.getTags().size() - 1) {
    					queryBuilder.append(", ");
    				}
    			}
        		queryBuilder.append(") GROUP BY tracker.id HAVING MIN(tag.id) <> MAX(tag.id)");
        	}
        	
        }
        System.out.println(queryBuilder.toString());
    }

    private Query generateQuery(String queryString, Integer statusId, Integer trackerModelId, TrackerSearchFilterDTO filter) {
        final Query query = this.entityManager.createNativeQuery(queryString, Tracker.class);
        if (trackerModelId != null) {
            query.setParameter("trackerModelId", trackerModelId);
        } else {
            query.setParameter("statusId", statusId);
        }

        if (filter.getMemberId() != null) {
            query.setParameter("memberId", filter.getMemberId());
        }

        if (filter.getStartDate() != null) {
            query.setParameter("startDate", filter.getStartDate());
        }

        if (filter.getEndDate() != null) {
            query.setParameter("endDate", filter.getEndDate());
        }
        
        if (filter.getTags() != null && filter.getTags().size() > 0) {
        	for (int i=0; i < filter.getTags().size(); i++) {
				String parameterName = String.format("tag%d", i);
				query.setParameter(parameterName, filter.getTags().get(i));
			}
        }

        return query;
    }
}
