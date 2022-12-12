package com.dh.apiseries.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Season")
public class Season {

    @Id
    private Integer id;
    private Integer seasonNumber;
    private String genre;
    private List<Chapter> chapters;

}
