package com.tinqinacademy.bff.domain.feignclients;


import com.tinqinacademy.bff.domain.config.FeignConfig;
import com.tinqinacademy.hotel.restexport.clients.HotelRestExportFeignClient;
import org.springframework.cloud.openfeign.FeignClient;


@FeignClient(name = "hotel-rest-export", url = "http://localhost:8080", configuration = FeignConfig.class)
public interface HotelClient extends HotelRestExportFeignClient {
}
