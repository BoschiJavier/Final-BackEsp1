package com.dh.apiseries.Service;

import com.dh.apiseries.Model.Serie;
import com.dh.apiseries.Repository.SerieRepository;
import com.dh.apiseries.event.NewSerieEventProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SerieService {

    private final SerieRepository serieRepository;
    private final NewSerieEventProducer newSerieEventProducer;

    @Autowired
    public SerieService(SerieRepository serieRepository, NewSerieEventProducer newSerieEventProducer) {
        this.serieRepository = serieRepository;
        this.newSerieEventProducer = newSerieEventProducer;
    }

    public List<Serie> getListSeriesByGenre(String genre) {
        List<Serie> series = serieRepository.findByGenre(genre);
        return series;

    }

    public void save(Serie serie) {
    serieRepository.save(serie);
        newSerieEventProducer.execute(serie);

    }


}
