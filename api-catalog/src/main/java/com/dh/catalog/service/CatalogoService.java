package com.dh.catalog.service;

import com.dh.catalog.controller.GetCatalogByGenreResponse;
import com.dh.catalog.repository.MovieRepositoryMongo;
import com.dh.catalog.repository.SerieRepositoryMongo;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class CatalogoService {


    private final MovieRepositoryMongo movieRepositoryMongo;
    private final SerieRepositoryMongo serieRepositoryMongo;


    public CatalogoService(MovieRepositoryMongo movieRepositoryMongo, SerieRepositoryMongo serieRepositoryMongo) {
        this.movieRepositoryMongo = movieRepositoryMongo;
        this.serieRepositoryMongo = serieRepositoryMongo;
    }


    public GetCatalogByGenreResponse getCatalogByGenreResponseOffline(String genre) {
        GetCatalogByGenreResponse response = new GetCatalogByGenreResponse();
        var movieFilter = movieRepositoryMongo.findAll().stream().filter(movie -> genre.equals(movie.getGenre())).collect(Collectors.toList());
        response.setMovies(movieFilter);

        var serieFilter = serieRepositoryMongo.findAll().stream().filter(serie -> genre.equals(serie.getGenre())).collect(Collectors.toList());
        response.setSeries(serieFilter);
        return response;
    }



}
