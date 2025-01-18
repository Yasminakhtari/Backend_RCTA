package com.ifacehub.tennis.service;

import com.ifacehub.tennis.entity.Tennis;
import com.ifacehub.tennis.responseDto.SessionResponseDto;
import com.ifacehub.tennis.util.ResponseObject;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TennisService {
    ResponseObject createTennisService(Tennis tennis);

    List<Tennis> getAllTennis();

    List<Tennis> getAllByStatus(String status);

    ResponseObject updateTennis(Long id,Tennis tennis);

    ResponseObject toggleStatus(Long id, String status);

    ResponseObject deleteTennis(Long id);

    List<Object[]> findAllCategoriesAndSubCategories();

    ResponseObject getTennisById(Long id);
    
    List<String> getGroups();
    List<String> getCategories(String group);
    List<String> getSubCategories(String group, String category);
    
    List<Tennis> getFilteredTennis(String group, String category, String subcategory);

    SessionResponseDto getTennisDataWithSessions(Long id);
}
