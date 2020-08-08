package com.ssp.maintainance.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.ssp.maintainance.beans.ContactEntity;
import com.ssp.maintainance.meta.keys.ResidentChildEntityKey;

public interface ContactEntityRepository extends CrudRepository<ContactEntity, ResidentChildEntityKey>{
	@Query("SELECT a FROM ContactEntity a WHERE LOWER(a.contactId.tenantId) = LOWER(:tenantId) and a.contactId.residentId=:residentId")
    public List<ContactEntity> getAllContactByResidentId(@Param("tenantId")  String tenantId, @Param("residentId")  int residentId);
}
