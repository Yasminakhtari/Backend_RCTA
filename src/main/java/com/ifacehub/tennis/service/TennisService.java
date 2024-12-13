package com.ifacehub.tennis.service;

import com.ifacehub.tennis.entity.Tennis;
import com.ifacehub.tennis.util.ResponseObject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TennisService {
    ResponseObject createTennisService(Tennis tennis);

    List<Tennis> getAllTennis();

    List<Tennis> getAllByStatus(String status);

    ResponseObject updateTennis(Tennis tennis);

    ResponseObject toggleStatus(Long id, String status);

    ResponseObject deleteTennis(Long id);

    List<Object[]> findAllCategoriesAndSubCategories();
}
