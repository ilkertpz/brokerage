package com.ing.brokerage.generator;

import com.ing.brokerage.domain.asset.dto.AssetDTO;
import com.ing.brokerage.domain.asset.entity.Asset;
import java.util.List;

public class AssetDataGenerator {

  public static AssetDTO generateAssetDTO(){
   AssetDTO assetDTO = new AssetDTO();
   assetDTO.setSize(10L);
   assetDTO.setName("TEST_ASSET");
   assetDTO.setCustomerId(1L);

   return assetDTO;
  }

  public static List<Asset> generateAssetList(){
    Asset asset = new Asset();
    asset.setId(1L);
    asset.setSize(10L);
    asset.setName("TEST_ASSET");
    asset.setCustomerId(1L);

    Asset asset2 = new Asset();
    asset2.setId(2L);
    asset2.setSize(11L);
    asset2.setName("TEST_ASSET2");
    asset2.setCustomerId(1L);

    return List.of(asset,asset2);
  }

  public static AssetDTO generateAssetDTOWithId(){
    AssetDTO assetDTO = new AssetDTO();
    assetDTO.setId(1L);
    assetDTO.setSize(10L);
    assetDTO.setName("TEST_ASSET");
    assetDTO.setCustomerId(1L);

    return assetDTO;
  }

  public static Asset generateAssetDTOWithAssetNameAndCustomerIdParams(String assetName, Long customerId){
    Asset asset = new Asset();
    asset.setId(1L);
    asset.setSize(10L);
    asset.setName(assetName);
    asset.setCustomerId(customerId);

    return asset;
  }

  public static Asset generateAsset(){
    Asset asset = new Asset();
    asset.setId(1L);
    asset.setSize(10L);
    asset.setName("TEST_ASSET");
    asset.setCustomerId(1L);

    return asset;
  }

  public static AssetDTO generateAssetDTOWithSize(Long size){
    AssetDTO assetDTO = new AssetDTO();
    assetDTO.setId(1L);
    assetDTO.setSize(size);
    assetDTO.setName("TEST_ASSET");
    assetDTO.setCustomerId(1L);

    return assetDTO;
  }

}
