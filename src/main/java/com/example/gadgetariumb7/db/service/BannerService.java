package com.example.gadgetariumb7.db.service;

import com.example.gadgetariumb7.dto.request.BannerRequest;
import com.example.gadgetariumb7.dto.response.SimpleResponse;

public interface BannerService {

    SimpleResponse addBanner(BannerRequest bannerRequest);

}
