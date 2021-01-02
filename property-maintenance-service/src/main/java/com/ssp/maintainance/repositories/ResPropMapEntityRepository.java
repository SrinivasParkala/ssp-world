package com.ssp.maintainance.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.ssp.maintainance.beans.ResPropMapEntity;
import com.ssp.maintainance.meta.keys.ResPropKey;

public interface ResPropMapEntityRepository extends CrudRepository<ResPropMapEntity, ResPropKey>{

	@Query("SELECT a FROM ResPropMapEntity a WHERE LOWER(a.id.tenantId) = LOWER(:tenantId) and a.resident.residentId.keyId=:residentId")
    public List<ResPropMapEntity> getAllPropertyByResidentId(@Param("tenantId")  String tenantId, @Param("residentId")  int residentId);
	
}
