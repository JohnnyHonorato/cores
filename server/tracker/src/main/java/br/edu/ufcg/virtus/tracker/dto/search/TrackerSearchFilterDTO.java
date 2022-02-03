package br.edu.ufcg.virtus.tracker.dto.search;

import java.util.Date;
import java.util.List;

import br.edu.ufcg.virtus.common.dto.FilterDTO;
import br.edu.ufcg.virtus.common.dto.ModelDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrackerSearchFilterDTO extends ModelDTO{

    public Integer memberId;

    public Date startDate;

    public Date endDate;

    public String title;

    public String description;
    
    public List<Integer> tags;

    public List<FilterDTO> filters;

}
