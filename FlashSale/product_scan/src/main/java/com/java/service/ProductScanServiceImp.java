package com.java.service;

import com.java.mapper.ProductScanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ProductScanServiceImp implements ProductScanMapper, ProductScanService{

    @Autowired
    private ProductScanMapper mapper;

    public List<Map<String, Object>> getToBeStarted() {
        return mapper.getToBeStarted();
    }

    public int changeStateForBeingSale() {
        mapper.changeStateForBeingSale();
        return 0;
    }

    public int changeStateForAfterSale() {
        mapper.changeStateForAfterSale();
        return 0;
    }

    public List<Map<String, Object>> getBeingSale() {
        return mapper.getBeingSale();
    }

    public List<Map<String, Object>> getAfterSale() {
        return mapper.getAfterSale();
    }

    @Override
    public List<Map<String, Object>> getAllSaleId() {
        return mapper.getAllSaleId();
    }
}
