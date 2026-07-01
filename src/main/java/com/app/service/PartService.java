package com.app.service;

import java.util.List;

import com.app.dtos.partdto.PartResponseDto;

public interface PartService {

	List<PartResponseDto> getAllPartsByCar(Long carId, Long categoryId);

	List<PartResponseDto> getAllParts();

}
