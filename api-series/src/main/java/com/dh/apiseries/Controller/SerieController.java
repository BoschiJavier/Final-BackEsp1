package com.dh.apiseries.Controller;

import com.dh.apiseries.Model.Serie;
import com.dh.apiseries.Service.SerieService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/series")
public class SerieController {

    private final SerieService serieService;

    public SerieController(SerieService serieService) {
        this.serieService = serieService;
    }

    @GetMapping("/{genre}")
    ResponseEntity<List<Serie>> getSerieByGenre(@PathVariable String genre) {
        return ResponseEntity.ok().body(serieService.getListSeriesByGenre(genre));
    }

    @PostMapping("/save")
    ResponseEntity<String> saveSerie(@RequestBody Serie serie) {
        serieService.save(serie);
        return ResponseEntity.ok(serie.getId());
    }



}
