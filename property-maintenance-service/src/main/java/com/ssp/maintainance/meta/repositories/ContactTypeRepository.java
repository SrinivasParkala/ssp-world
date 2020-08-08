package com.ssp.maintainance.meta.repositories;

import org.springframework.data.repository.CrudRepository;

import com.ssp.maintainance.meta.beans.RefContactType;
import com.ssp.maintainance.meta.keys.RefEntityIntKey;

public interface ContactTypeRepository extends CrudRepository<RefContactType, RefEntityIntKey>{

}
