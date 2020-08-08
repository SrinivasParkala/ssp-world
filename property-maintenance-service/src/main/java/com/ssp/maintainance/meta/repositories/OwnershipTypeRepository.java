package com.ssp.maintainance.meta.repositories;

import org.springframework.data.repository.CrudRepository;

import com.ssp.maintainance.meta.beans.RefOwnershipType;
import com.ssp.maintainance.meta.keys.RefEntityIntKey;

public interface OwnershipTypeRepository extends CrudRepository<RefOwnershipType, RefEntityIntKey>{

}
