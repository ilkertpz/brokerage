package com.ing.brokerage.domain.asset.mapper;


import com.ing.brokerage.domain.asset.entity.Asset;
import com.ing.brokerage.domain.asset.dto.AssetDTO;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AssetMapper {

  Asset toEntity(AssetDTO assetDTO);

  AssetDTO toDTO(Asset asset);

  List<AssetDTO> toDTO(List<Asset> asset);

}
