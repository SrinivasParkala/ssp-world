package com.ssp.maintainance.meta.repositories;

import org.springframework.data.repository.CrudRepository;

import com.ssp.maintainance.meta.beans.RefTitleType;
import com.ssp.maintainance.meta.keys.RefEntityIntKey;

public interface TitleTypeRepository extends CrudRepository<RefTitleType, RefEntityIntKey>{

}
