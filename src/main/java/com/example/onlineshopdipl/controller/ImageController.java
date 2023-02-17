package com.example.onlineshopdipl.controller;

import com.example.onlineshopdipl.service.ImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
public class ImageController {

    private final ImageService imageService;
    public ImageController(ImageService imageService){
        this.imageService=imageService;
    }

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
    @PreAuthorize("hasRole('USER')or hasRole('ADMIN')")
    @RequestMapping(
            method = RequestMethod.PATCH,
            value = "/image/{id}",
            produces = { "application/octet-stream" },
            consumes = { "multipart/form-data" }
    )
    public ResponseEntity<byte[]> updateImage(
            @Parameter(name = "id", required = true) @PathVariable("id") Integer id,
            @Parameter(name = "image", required = true) @RequestPart(value = "image") MultipartFile image,
            Authentication authentication)
    {
        return ResponseEntity.ok(imageService.updateAdsImage(id, image, authentication));
    }


}
