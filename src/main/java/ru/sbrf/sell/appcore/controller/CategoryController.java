package ru.sbrf.sell.appcore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sbrf.sell.appcore.dto.CategoryDto;
import ru.sbrf.sell.appcore.entity.CategoryEntity;
import ru.sbrf.sell.appcore.service.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService service;

    public CategoryController(CategoryService servicet) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<CategoryEntity>>  getCategoryList(){
        var entityList = service.getCategoryList();
        return response200(entityList);
    }

    @PostMapping
    public ResponseEntity<CategoryEntity> createCategory(@RequestBody CategoryDto dto){
        var entity = service.createCategory(dto);
        return response200(entity);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryEntity>  getCategory(@PathVariable("id") int id){
        var entity = service.getCategory(id);
        return response200(entity);
    }

    @PutMapping
    public ResponseEntity<CategoryEntity> updateCategory(@RequestBody CategoryEntity categoryEntity){
        var entity = service.updateCategory(categoryEntity);
        return response200(entity);
    }

    private <T> ResponseEntity<T> response200(T content){
        return new ResponseEntity<T>(content, HttpStatus.OK);
    }

    private <T> ResponseEntity<T>  response404(T content){
        return new ResponseEntity<T>(content, HttpStatus.NOT_FOUND);
    }


}
