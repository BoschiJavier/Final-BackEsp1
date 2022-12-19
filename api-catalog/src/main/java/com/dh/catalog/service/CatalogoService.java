package com.dh.catalog.service;

import com.dh.catalog.client.MovieServiceClient;
import com.dh.catalog.client.SerieServiceClient;
import com.dh.catalog.controller.GetCatalogByGenreResponse;
import com.dh.catalog.repository.MovieRepositoryMongo;
import com.dh.catalog.repository.SerieRepositoryMongo;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class CatalogoService {

    public static Logger LOG = LoggerFactory.getLogger(CatalogoService.class);
    private final MovieRepositoryMongo movieRepositoryMongo;
    private final SerieRepositoryMongo serieRepositoryMongo;
    private final MovieServiceClient movieServiceClient;
    private final SerieServiceClient serieServiceClient;


    public CatalogoService(MovieRepositoryMongo movieRepositoryMongo, SerieRepositoryMongo serieRepositoryMongo, MovieServiceClient movieServiceClient, SerieServiceClient serieServiceClient) {
        this.movieRepositoryMongo = movieRepositoryMongo;
        this.serieRepositoryMongo = serieRepositoryMongo;
        this.movieServiceClient = movieServiceClient;
        this.serieServiceClient = serieServiceClient;
    }

    @Retry(name = "retryCatalog")
    @CircuitBreaker(name = "clientCatalog", fallbackMethod = "getCatalogByGenreFallBack")
    public GetCatalogByGenreResponse getCatalogByGenreResponseOnline(String genre) {
        LOG.info("Se busca el catalogo por tipo de genero: " + genre);
        GetCatalogByGenreResponse response = new GetCatalogByGenreResponse();
        response.setGenre(genre);
        response.setMovies(movieServiceClient.getMovieByGenre(genre));
        response.setSeries(serieServiceClient.getSerieByGenre(genre));
        LOG.info("La busqueda fue exitosa para el tipo de genero" + genre);
        return response;
    }

    /* Si falla getCatalogByGenreResponseOnline ejecutamos getCatalogByGenreFallBack que no hace un llamado a los
    microservicios api-movie ni api-series si no que directamente trae la respuesta de la Base de datos en Mongo */
    public GetCatalogByGenreResponse getCatalogByGenreFallBack(String genre, Throwable t) {
        LOG.info("se ejecuta el metodo getCatalogByGenreFallBack");
        GetCatalogByGenreResponse response = new GetCatalogByGenreResponse();
        response.setGenre(genre);
        var movieFilter = movieRepositoryMongo.findAll().stream().filter(movie -> genre.equals(movie.getGenre())).collect(Collectors.toList());
        response.setMovies(movieFilter);
        var serieFilter = serieRepositoryMongo.findAll().stream().filter(serie -> genre.equals(serie.getGenre())).collect(Collectors.toList());
        response.setSeries(serieFilter);
        return response;

    }


    public GetCatalogByGenreResponse getCatalogByGenreResponseOffline(String genre) {
        GetCatalogByGenreResponse response = new GetCatalogByGenreResponse();
        response.setGenre(genre);
        var movieFilter = movieRepositoryMongo.findAll().stream().filter(movie -> genre.equals(movie.getGenre())).collect(Collectors.toList());
        response.setMovies(movieFilter);

        var serieFilter = serieRepositoryMongo.findAll().stream().filter(serie -> genre.equals(serie.getGenre())).collect(Collectors.toList());
        response.setSeries(serieFilter);
        return response;
    }


}
