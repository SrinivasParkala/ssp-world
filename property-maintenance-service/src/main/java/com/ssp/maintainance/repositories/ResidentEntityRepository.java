package com.ssp.maintainance.repositories;

import org.springframework.data.repository.CrudRepository;

import com.ssp.maintainance.beans.ResidentEntity;
import com.ssp.maintainance.meta.keys.EntityIntKey;

public interface ResidentEntityRepository extends CrudRepository<ResidentEntity, EntityIntKey>{

}
