package ru.sbrf.sell.appcore.service;

import org.springframework.stereotype.Service;
import ru.sbrf.sell.appcore.dto.CategoryDto;
import ru.sbrf.sell.appcore.entity.CategoryEntity;
import ru.sbrf.sell.appcore.repository.CategoryRepository;

import java.util.List;

@Service
public class CategoryService {

    private CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public CategoryEntity createCategory(CategoryDto dto){

        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setName(dto.getName());
        categoryEntity.setActive(dto.isActive());

        categoryEntity = categoryRepository.save(categoryEntity);

        return categoryEntity;
    }

    public CategoryEntity getCategory(int id){
        var entity = categoryRepository.findById(id).orElse(null);
        return entity;
    }

    public List<CategoryEntity> getCategoryList(){
        var entityList = categoryRepository.findAll();
        return entityList;
    }

    public CategoryEntity updateCategory(CategoryEntity entity){
        entity = categoryRepository.save(entity);
        return entity;
    }

}
