package com.app.service;

import java.util.List;

import com.app.dtos.categorydto.CategoryResponseDto;

public interface CategoryService {
	
	List<CategoryResponseDto> getAllCategoriesByCar(Long carId);
	/*ScrapYardResponseDto getBookById(Long bookId);
	ScrapYardResponseDto updateBook(Long bookId, ScrapYardRequestDto scrapYardRequestDto);
	void deleteBook(Long bookId);
	
	ScrapYardResponseDto addWriterToBook(Long bookId, Long writerId);*/

	List<CategoryResponseDto> getAllCategories();


	//List<CategoryResponseDto> getPartByName(String partName);

}
