package br.gov.camara.codis.entidade;

import java.io.Serializable;

public interface DomainObject<KeyType extends Serializable> extends Serializable {
	public KeyType getId();
}
