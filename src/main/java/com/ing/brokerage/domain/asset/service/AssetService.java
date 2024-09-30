package com.ing.brokerage.domain.asset.service;


import com.ing.brokerage.domain.asset.dto.AssetDTO;
import com.ing.brokerage.domain.asset.entity.Asset;
import com.ing.brokerage.domain.asset.mapper.AssetMapper;
import com.ing.brokerage.domain.asset.repository.AssetRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AssetService implements IAssetService {

  private final AssetRepository assetRepository;
  private final AssetMapper assetMapper;



  @Override
  @Transactional
  public AssetDTO create(AssetDTO assetDTO) {
    Asset entity = assetMapper.toEntity(assetDTO);
    Asset createdAsset = assetRepository.save(entity);
    return assetMapper.toDTO(createdAsset);
  }

  @Override
  @Transactional
  public AssetDTO update(AssetDTO assetDTO) {
    Asset entity = assetMapper.toEntity(assetDTO);
    Asset updatedAsset = assetRepository.save(entity);
    return assetMapper.toDTO(updatedAsset);
  }

  @Override
  public Optional<AssetDTO> findByNameAndCustomerId(String name, Long customerId) {
    Optional<Asset> assetOptional = assetRepository.findByNameAndCustomerId(name, customerId);
    return assetOptional.map(assetMapper::toDTO);

  }

  @Override
  @Transactional(readOnly = true)
  public List<AssetDTO> getAssetsByCustomerId(Long customerId) {
    List<Asset> assetList = assetRepository.findAllByCustomerId(customerId);
    return assetMapper.toDTO(assetList);
  }

}
