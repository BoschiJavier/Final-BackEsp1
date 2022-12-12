package com.dh.catalog.controller;

import com.dh.catalog.client.MovieServiceClient;
import com.dh.catalog.service.CatalogoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/catalog")
public class CatalogController {

	private final MovieServiceClient movieServiceClient;
	private final CatalogoService catalogoService;

	public CatalogController(MovieServiceClient movieServiceClient, CatalogoService catalogoService) {
		this.movieServiceClient = movieServiceClient;
		this.catalogoService = catalogoService;
	}

	@GetMapping("/{genre}")
	ResponseEntity<List<MovieServiceClient.MovieDto>> getGenre(@PathVariable String genre) {
		return ResponseEntity.ok(movieServiceClient.getMovieByGenre(genre));
	}


	@GetMapping("/offline/{genre}")
	@ResponseStatus(code = HttpStatus.OK)
	public ResponseEntity<GetCatalogByGenreResponse> getCatalogByGenreResponseOffline(@PathVariable String genre) {
		return ResponseEntity.ok(catalogoService.getCatalogByGenreResponseOffline(genre));
	}



}
