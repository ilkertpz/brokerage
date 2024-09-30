package com.ing.brokerage.domain.asset.service;

import com.ing.brokerage.domain.asset.dto.AssetDTO;
import java.util.List;
import java.util.Optional;

public interface IAssetService {

  AssetDTO update(AssetDTO assetDTO);

  AssetDTO create(AssetDTO assetDTO);

  Optional<AssetDTO> findByNameAndCustomerId(String name, Long customerId);

  List<AssetDTO> getAssetsByCustomerId(Long customerId);
}
