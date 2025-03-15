package com.example.api_ltdd.course.service;

import com.example.api_ltdd.category.model.Category;
import com.example.api_ltdd.category.repository.CategoryRepository;
import com.example.api_ltdd.category.service.CategoryService;
import com.example.api_ltdd.course.model.Course;
import com.example.api_ltdd.course.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Course> getCoursesByCategory(Long categoryID) {
        Optional<Category> optionalCategory = categoryRepository.findById(categoryID);
        Category category = optionalCategory.get();
        return courseRepository.findByCategory(category);
    }

    public List<Course> getTop10ByPurchaseCount() {
        return courseRepository.findTop10ByOrderByPurchaseCountDesc();
    }

    public List<Course> getTop10CreatedInLast7Days() {
        LocalDateTime sevenDaysAgo = LocalDateTime.now().minusDays(7);
        return courseRepository.findTop10CreatedInLast7Days(sevenDaysAgo);
    }
}
