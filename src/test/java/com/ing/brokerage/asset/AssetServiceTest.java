package com.ing.brokerage.asset;

import static com.ing.brokerage.generator.AssetDataGenerator.generateAsset;
import static com.ing.brokerage.generator.AssetDataGenerator.generateAssetDTO;
import static com.ing.brokerage.generator.AssetDataGenerator.generateAssetList;
import static com.ing.brokerage.generator.AssetDataGenerator.generateAssetDTOWithAssetNameAndCustomerIdParams;
import static com.ing.brokerage.generator.AssetDataGenerator.generateAssetDTOWithId;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.ing.brokerage.domain.asset.dto.AssetDTO;
import com.ing.brokerage.domain.asset.entity.Asset;
import com.ing.brokerage.domain.asset.mapper.AssetMapperImpl;
import com.ing.brokerage.domain.asset.repository.AssetRepository;
import com.ing.brokerage.domain.asset.service.AssetService;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AssetServiceTest {


  @Mock
  AssetRepository assetRepository;

  @Mock
  AssetMapperImpl assetMapper;

  @InjectMocks
  AssetService assetService;

  @Test
  void givenAssetDTO_whenCreate_thenReturnCreatedAssetDTO() {

    // Arrange
    AssetDTO assetDTO = generateAssetDTO();
    Asset asset = generateAsset();

    when(assetRepository.save(any())).thenReturn(asset);
    when(assetMapper.toDTO(any(Asset.class))).thenCallRealMethod();
    when(assetMapper.toEntity(any())).thenCallRealMethod();


    // Act
    AssetDTO assetDTOResult = assetService.create(assetDTO);

    // Assert
    assertThat(assetDTOResult).isNotNull();
    assertThat(assetDTOResult.getId()).isNotNull();
    assertThat(assetDTOResult.getCustomerId()).isNotNull();
    assertThat(assetDTOResult.getCustomerId()).isEqualTo(assetDTO.getCustomerId());
    assertThat(assetDTOResult.getSize()).isNotNull();
    assertThat(assetDTOResult.getSize()).isEqualTo(assetDTO.getSize());
    assertThat(assetDTOResult.getName()).isNotNull();
    assertThat(assetDTOResult.getName()).isEqualTo(assetDTO.getName());

    verify(assetRepository, times(1)).save(any());

  }

  @Test
  void givenAssetDTO_whenUpdate_thenReturnCreatedAssetDTO() {

    // Arrange
    AssetDTO assetDTO = generateAssetDTOWithId();
    Asset asset = generateAsset();

    when(assetRepository.save(any())).thenReturn(asset);
    when(assetMapper.toDTO(any(Asset.class))).thenCallRealMethod();
    when(assetMapper.toEntity(any())).thenCallRealMethod();


    // Act
    AssetDTO assetDTOResult = assetService.update(assetDTO);

    // Assert
    assertThat(assetDTOResult).isNotNull();
    assertThat(assetDTOResult.getId()).isNotNull();
    assertThat(assetDTOResult.getId()).isEqualTo(assetDTO.getId());
    assertThat(assetDTOResult.getCustomerId()).isNotNull();
    assertThat(assetDTOResult.getCustomerId()).isEqualTo(assetDTO.getCustomerId());
    assertThat(assetDTOResult.getSize()).isNotNull();
    assertThat(assetDTOResult.getSize()).isEqualTo(assetDTO.getSize());
    assertThat(assetDTOResult.getName()).isNotNull();
    assertThat(assetDTOResult.getName()).isEqualTo(assetDTO.getName());

    verify(assetRepository, times(1)).save(any());

  }

  @Test
  void givenNameAndCustomerId_whenFindByNameAndCustomerId_thenReturnAssetDTOResult() {

    // Arrange

    String assetName = "TEST-ASSET";
    Long customerId = 1L;

    Asset asset = generateAssetDTOWithAssetNameAndCustomerIdParams(assetName, customerId);

    when(assetRepository.findByNameAndCustomerId(assetName, customerId)).thenReturn(Optional.of(asset));
    when(assetMapper.toDTO(any(Asset.class))).thenCallRealMethod();


    // Act
    Optional<AssetDTO> assetResult = assetService.findByNameAndCustomerId(assetName, customerId);

    // Assert
    assertThat(assetResult.isPresent()).isTrue();
    assertThat(assetResult.get().getId()).isNotNull();
    assertThat(assetResult.get().getName()).isEqualTo(assetName);
    assertThat(assetResult.get().getCustomerId()).isEqualTo(customerId);

    verify(assetRepository, times(1)).findByNameAndCustomerId(assetName, customerId);

  }

  @Test
  void givenCustomerId_whenGetAssetsByCustomerId_thenReturnAssetListDTO() {

    // Arrange
    Long customerId = 1L;

    List<Asset> assetList = generateAssetList();

    when(assetRepository.findAllByCustomerId(customerId)).thenReturn(assetList);
    when(assetMapper.toDTO(anyList())).thenCallRealMethod();


    // Act
    List<AssetDTO> assetResultList = assetService.getAssetsByCustomerId(customerId);

    // Assert
    assertThat(assetResultList).isNotNull();
    assertThat(assetResultList).isNotEmpty();
    assertThat(assetResultList.size()).isEqualTo(assetList.size());

    verify(assetRepository, times(1)).findAllByCustomerId(customerId);

  }
}
