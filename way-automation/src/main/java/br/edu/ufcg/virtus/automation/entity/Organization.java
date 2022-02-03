package br.edu.ufcg.virtus.automation.entity;

public class Organization {
	
	private String fantasyName;
	private String cnpj;
	
	public Organization(String fantasyName, String cnpj) {
		this.fantasyName = fantasyName;
		this.cnpj = cnpj;
	}

	public String getFantasyName() {
		return fantasyName;
	}

	public void setFantasyName(String fantasyName) {
		this.fantasyName = fantasyName;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
}
