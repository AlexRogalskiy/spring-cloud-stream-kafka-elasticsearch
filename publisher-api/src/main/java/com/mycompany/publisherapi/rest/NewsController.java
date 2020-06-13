package com.mycompany.publisherapi.rest;

import com.mycompany.publisherapi.exception.NewsNotFoundException;
import com.mycompany.publisherapi.model.News;
import com.mycompany.publisherapi.rest.dto.SearchDto;
import com.mycompany.publisherapi.service.NewsService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/news")
public class NewsController {

    private final NewsService newsService;

    @ApiOperation(
            value = "Get News",
            notes = "To sort the results by a specified field (ex. 'datetime'), use in 'sort' field a string like: datetime,[asc|desc]")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @GetMapping
    public Page<News> getNews(Pageable pageable) {
        return newsService.listAllNewsByPage(pageable);
    }

    @ApiOperation(value = "Get News by Id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @GetMapping("/{id}")
    public News getNewsById(@PathVariable String id) throws NewsNotFoundException {
        return newsService.validateAndGetNewsById(id);
    }

    @ApiOperation(
            value = "Search for News",
            notes = "This endpoint does a query for the 'string' informed in the fields 'title', 'text' and 'category'\n" +
                    "To sort the results by a specified field (ex. 'datetime'), use in 'sort' field a string like: datetime,[asc|desc]")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @PutMapping("/search")
    public Page<News> searchNews(@Valid @RequestBody SearchDto searchDto, Pageable pageable) {
        return newsService.search(searchDto.getText(), pageable);
    }

}
