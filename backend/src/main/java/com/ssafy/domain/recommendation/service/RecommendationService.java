package com.ssafy.domain.recommendation.service;

import com.ssafy.domain.company.dto.response.CompanyResponse;
import com.ssafy.domain.company.entity.Company;
import com.ssafy.domain.recommendation.repository.RecommendationRepository;
import static com.ssafy.domain.company.dto.CompanyDtoMapper.companyEntityListToDtoList;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RecommendationService {

    private final RecommendationRepository recommendationRepository;

    public List<CompanyResponse> findCompanyByRecommendationId(Integer id) {
        List<Company> getCompanyList = recommendationRepository.findCompanyByRecommendationId(id);

        return companyEntityListToDtoList(getCompanyList);
    }

}
