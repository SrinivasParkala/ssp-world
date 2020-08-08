package com.ssp.maintainance.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.ssp.maintainance.beans.AddressEntity;
import com.ssp.maintainance.meta.keys.ResidentChildEntityKey;

public interface AddressEntityRepository extends CrudRepository<AddressEntity, ResidentChildEntityKey>{

	@Query("SELECT a FROM AddressEntity a WHERE LOWER(a.addressId.tenantId) = LOWER(:tenantId) and a.addressId.residentId=:residentId")
    public List<AddressEntity> getAllAddressByResidentId(@Param("tenantId")  String tenantId, @Param("residentId")  int residentId);
}
