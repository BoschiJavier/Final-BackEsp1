package com.dh.catalog.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Chapter")
public class Chapter {

    @Id
    private Integer id;
    private String name;
    private Integer number;
    private String urlStream;

}
