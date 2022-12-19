package com.dh.catalog.client;

import com.dh.catalog.model.MovieEntity;
import com.dh.catalog.model.Serie;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name="api-series")
public interface SerieServiceClient {


    @GetMapping("/api/v1/series/{genre}")
    List<Serie> getSerieByGenre(@PathVariable(value = "genre") String genre);

}
