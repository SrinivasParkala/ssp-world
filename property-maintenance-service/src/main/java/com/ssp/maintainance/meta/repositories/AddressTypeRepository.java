package com.ssp.maintainance.meta.repositories;

import org.springframework.data.repository.CrudRepository;

import com.ssp.maintainance.meta.beans.RefAddressType;
import com.ssp.maintainance.meta.keys.RefEntityIntKey;

public interface AddressTypeRepository extends CrudRepository<RefAddressType, RefEntityIntKey>{

}
