package com.java.service;

import java.util.List;
import java.util.Map;

public interface ProductScanService {
    List<Map<String, Object>> getToBeStarted();

    int changeStateForBeingSale();

    int changeStateForAfterSale();

    List<Map<String, Object>> getAfterSale();

    List<Map<String, Object>> getBeingSale();
}
