package com.example.onlineshopdipl.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
public class ImageController {

    @Operation(
            tags = "Изображения",
            summary = "updateAdsImage",
            operationId = "updateImage",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = {
                            @Content(mediaType = "application/octet-stream", schema = @Schema(implementation = byte[].class))
                    }),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @RequestMapping(
            method = RequestMethod.PATCH,
            value = "/image/{id}",
            produces = { "application/octet-stream" },
            consumes = { "multipart/form-data" }
    )
    public ResponseEntity<byte[]> updateImage(
            @Parameter(name = "id", required = true) @PathVariable("id") Integer id,
            @Parameter(name = "image", required = true) @RequestPart(value = "image") MultipartFile image)
    {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
