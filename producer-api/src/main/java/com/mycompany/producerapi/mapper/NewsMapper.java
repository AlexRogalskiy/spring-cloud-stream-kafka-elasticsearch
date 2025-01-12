package com.mycompany.producerapi.mapper;

import com.mycompany.producerapi.model.News;
import com.mycompany.producerapi.rest.dto.CreateNewsRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NewsMapper {

    News toNews(CreateNewsRequest createNewsRequest);
}
