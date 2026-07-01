package com.app.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.dtos.categorydto.CategoryResponseDto;
import com.app.entity.PartCategory;
import com.app.entity.ScrapYardParts;
import com.app.mappers.category.CategoryMapper;
import com.app.mappers.scrapyardparts.ScrapYardPartsMapper;
import com.app.repository.CategoryRepository;
import com.app.repository.WriterRepository;

/**
 * Service implementation for handling category-related operations.
 * Responsible for retrieving part categories based on car or globally, and converting them to DTOs.
 */
@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired CategoryRepository categoryRepository;
	@Autowired CategoryMapper categoryMapper;
	
    /**
     * Retrieves all categories that are associated with a specific car ID.
     *
     * @param carId the ID of the car
     * @return List of CategoryResponseDto representing the categories available for that car
     */
	@Override
	public List<CategoryResponseDto> getAllCategoriesByCar(Long carId) {
		List<PartCategory> categories =  categoryRepository.findByCar(carId);
		return categories.stream()
								.map(categoryMapper::toResponse)
								.collect(Collectors.toList());
	}
	
    /**
     * Retrieves all available part categories from the system.
     *
     * @return List of CategoryResponseDto representing all categories
     */
	@Override
	public List<CategoryResponseDto> getAllCategories() {
		List<PartCategory> categories =  categoryRepository.findAll();
		return categories.stream()
								.map(categoryMapper::toResponse)
								.collect(Collectors.toList());
	}

}
