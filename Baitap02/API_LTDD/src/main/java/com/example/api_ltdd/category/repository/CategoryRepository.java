    package com.example.api_ltdd.category.repository;

    import com.example.api_ltdd.category.model.Category;
    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.stereotype.Repository;

    import java.util.List;
    import java.util.Optional;

    @Repository
    public interface CategoryRepository extends JpaRepository<Category, Long> {
        Optional<Category> findById(Long id);
    }
