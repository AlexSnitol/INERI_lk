package com.ineri.ineri_lk.repository;

import com.ineri.ineri_lk.model.EstateObjectType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstateObjectTypeRepository extends JpaRepository<EstateObjectType, Long> {
}
