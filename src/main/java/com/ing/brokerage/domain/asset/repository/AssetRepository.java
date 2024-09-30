package com.ing.brokerage.domain.asset.repository;

import com.ing.brokerage.domain.asset.entity.Asset;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssetRepository extends JpaRepository<Asset, Long> {


  Optional<Asset> findByName(String name);

  Optional<Asset> findByNameAndCustomerId(String name, Long customerId);

  List<Asset> findAllByCustomerId(Long customerId);

}
