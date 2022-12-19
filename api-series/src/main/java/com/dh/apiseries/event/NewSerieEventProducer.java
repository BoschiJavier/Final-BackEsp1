package com.dh.apiseries.event;

import com.dh.apiseries.Config.RabbitMQConfig;
import com.dh.apiseries.Model.Chapter;
import com.dh.apiseries.Model.Season;
import com.dh.apiseries.Model.Serie;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class NewSerieEventProducer {

    private final RabbitTemplate rabbitTemplate;


    public NewSerieEventProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void execute(Serie serie) {
        NewSerieEventProducer.Data data= new NewSerieEventProducer.Data();
        BeanUtils.copyProperties(serie,data.getSerieDto());
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.TOPIC_NEW_SERIE, data);
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
