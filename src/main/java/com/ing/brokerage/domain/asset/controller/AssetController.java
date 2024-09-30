package com.ing.brokerage.domain.asset.controller;


import com.ing.brokerage.domain.asset.dto.AssetDTO;
import com.ing.brokerage.domain.asset.service.IAssetService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/brokerage/asset")
@RequiredArgsConstructor
public class AssetController {


  private final IAssetService assetService;

  @GetMapping("/by-customer/{customerId}")
  @PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER')")
  public ResponseEntity<List<AssetDTO>> getAssetList(@PathVariable long customerId) {
    return ResponseEntity.ok(assetService.getAssetsByCustomerId(customerId));
  }

}
