package com.dh.catalog.controller;

import com.dh.catalog.model.MovieEntity;
import com.dh.catalog.model.Serie;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetCatalogByGenreResponse {

    private String genre;
    private List<MovieEntity> movies =new ArrayList<>() ;
    private List<Serie> series= new ArrayList<>();

}
