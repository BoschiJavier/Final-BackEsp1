package com.dh.catalog.event;

import com.dh.catalog.config.RabbitMQConfig;
import com.dh.catalog.model.Serie;
import com.dh.catalog.repository.SerieRepositoryMongo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

public class NewSeriesEventConsumer {

    private final SerieRepositoryMongo serieRepositoryMongo;

    public NewSeriesEventConsumer(SerieRepositoryMongo serieRepositoryMongo) {
        this.serieRepositoryMongo = serieRepositoryMongo;
    }

    //pasamos el nombre de la cola, no el topico, el ruteo ya lo hizo rabbit en el medio
    @RabbitListener(queues = RabbitMQConfig.QUEUE_NEW_SERIE)
    public void execute(NewSeriesEventConsumer.Data data) {  //recibo el dato
        Serie serieNew = new Serie(); // lo mapeo a mi entidad, relacional en este caso
        BeanUtils.copyProperties(data.getSerieDto(), serieNew);
        serieRepositoryMongo.deleteById(data.getSerieDto().id); //por si se crea varias veces borro 1ero y despues guardo
        //lo guardo en mi base de datos, min 38 clase.
        serieRepositoryMongo.save(serieNew);
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Data {

        private SerieDto serieDto = new SerieDto();

        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor

        public static class SerieDto {

            private String id;
            private String name;
            private String genre;
            private List<SeasonDto> seasons = new ArrayList<>();

        }

        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        public static class SeasonDto {

            private Integer id;
            private Integer seasonNumber;
            private String genre;
            private List<ChapterDto> chapters = new ArrayList<>();

        }

        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        public static class ChapterDto {

            private Integer id;
            private String name;
            private Integer number;
            private String urlStream;

        }
    }

}