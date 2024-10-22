package com.nc13.moviemates.component.model;

import lombok.*;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PosterModel {
    private Long id;
    private Long movieId;
    private String filepath;
    private String filename;

    public PosterModel(String posterPath, String uploadName) {
        this.filepath = posterPath;
        this.filename = uploadName;
    }
    private String url;
}
