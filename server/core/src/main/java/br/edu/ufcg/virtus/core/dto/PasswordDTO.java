package br.edu.ufcg.virtus.core.dto;

import br.edu.ufcg.virtus.common.dto.ModelDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordDTO extends ModelDTO{

    private String password;

    private String passwordConfirmation;

}
