package com.dh.catalog.event;

import com.dh.catalog.config.RabbitMQConfig;
import com.dh.catalog.model.MovieEntity;
import com.dh.catalog.repository.MovieRepositoryMongo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;

public class NewMovieEventConsumer {

    private final MovieRepositoryMongo movieRepositoryMongo;

    public NewMovieEventConsumer(MovieRepositoryMongo movieRepositoryMongo) {
        this.movieRepositoryMongo = movieRepositoryMongo;
    }

    //pasamos el nombre de la cola, no el topico, el ruteo ya lo hizo rabbit en el medio
    @RabbitListener(queues = RabbitMQConfig.QUEUE_NEW_MOVIE)
    public void execute(NewMovieEventConsumer.Data data) {  //recibo el dato
        MovieEntity movieNew = new MovieEntity(); // lo mapeo a mi entidad, relacional en este caso
        BeanUtils.copyProperties(data.getMovieDto(), movieNew);
        movieRepositoryMongo.deleteById(data.getMovieDto().getId()); //por si se crea varias veces borro 1ero y despues guardo
        //lo guardo en mi base de datos, min 38 clase.
        movieRepositoryMongo.save(movieNew);
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Data {

        private MovieDto movieDto = new MovieDto();

        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor

        public static class MovieDto {

            private Long id;
            private String name;
            private String genre;
            private String urlStream;

        }

    }

}




