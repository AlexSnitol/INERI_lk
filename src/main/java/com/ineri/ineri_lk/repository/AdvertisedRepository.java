package com.ineri.ineri_lk.repository;

import com.ineri.ineri_lk.model.Advertised;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdvertisedRepository extends JpaRepository<Advertised, Long> {
}
