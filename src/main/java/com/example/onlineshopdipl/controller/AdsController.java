package com.example.onlineshopdipl.controller;

import com.example.onlineshopdipl.dto.*;
import com.example.onlineshopdipl.entity.Ads;
import com.example.onlineshopdipl.entity.Comment;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/ads")
public class AdsController {

    @Operation(
            tags = "Объявления",
            operationId = "getAllAds",
            responses = @ApiResponse(responseCode = "200", description = "OK", content =
            @Content(mediaType = "*/*", schema = @Schema(implementation = ResponseWrapperAds.class)))
    )
    @GetMapping()
    public ResponseEntity<ResponseWrapperAds> getAllAds() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(
            tags = "Объявления",
            summary = "addAds",
            operationId = "addAds",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Created", content =
                    @Content(mediaType = "*/*", schema = @Schema(implementation = Ads.class))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @PostMapping()
    public ResponseEntity<Ads> addAds(
            @Parameter(name = "properties", required = true) @RequestParam(value = "properties") CreateAds properties,
            @Parameter(name = "image", required = true) @RequestParam(value = "image") MultipartFile image) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(
            tags = "Объявления",
            summary = "getComments",
            operationId = "getComments",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content =
                    @Content(mediaType = "*/*", schema = @Schema(implementation = ResponseWrapperComment.class))),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @GetMapping("/{ad_pk}/comments")
    public ResponseEntity<ResponseWrapperComment> getComments(
            @Parameter(name = "ad_pk", in = ParameterIn.PATH, required = true) @PathVariable("ad_pk") String adPk
    ) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(tags = "Объявления",
            summary = "addComments",
            operationId = "addComments",
            requestBody = @RequestBody(content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Comment.class))),
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content =
                    @Content(mediaType = "*/*", schema = @Schema(implementation = Comment.class))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            })
    @PostMapping(value = "/{ad_pk}/comments")
    public ResponseEntity<Comment> addComments(
            @Parameter(name = "ad_pk", in = ParameterIn.PATH, required = true) @PathVariable("ad_pk") String adPk
    ) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(
            operationId = "getAds",
            summary = "getFullAd",
            tags = "Объявления",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = {
                            @Content(mediaType = "*/*", schema = @Schema(implementation = FullAds.class))
                    }),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )

    @GetMapping("/{id}")
    public ResponseEntity<FullAds> getAds(
            @Parameter(name = "id", required = true) @PathVariable("id") Integer id
    ) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(
            operationId = "removeAds",
            summary = "removeAds",
            tags = "Объявления",
            responses = {
                    @ApiResponse(responseCode = "204", description = "No Content"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeAds(
            @Parameter(name = "id", required = true) @PathVariable("id") Integer id
    ) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(
            operationId = "updateAds",
            summary = "updateAds",
            tags = "Объявления",
            requestBody = @RequestBody(content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = {
                            @Content(mediaType = "*/*", schema = @Schema(implementation = Ads.class))
                    }),
                    @ApiResponse(responseCode = "404", description = "Not Found"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")
            }
    )
    @PatchMapping("/{id})")
    public ResponseEntity<Ads> updateAds(
            @Parameter(name = "id", required = true) @PathVariable("id") Integer id,
            @Parameter(name = "CreateAds", required = true) @RequestBody CreateAds ads
    ) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(
            operationId = "getComments_1",
            summary = "getComments",
            tags = "Объявления",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = {
                            @Content(mediaType = "*/*", schema = @Schema(implementation = Comment.class))
                    }),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @GetMapping("/{ad_pk}/comments/{id}")
    public ResponseEntity<Comment> getComments_1(
            @Parameter(name = "ad_pk", required = true) @PathVariable("ad_pk") String adPk,
            @Parameter(name = "id", required = true) @PathVariable("id") Integer id
    ) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(
            operationId = "deleteComments",
            summary = "deleteComments",
            tags = "Объявления",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "404", description = "Not Found"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")
            }
    )
    @DeleteMapping("/{ad_pk}/comments/{id}")
    public ResponseEntity<Void> deleteComments(
            @Parameter(name = "ad_pk", required = true) @PathVariable("ad_pk") String adPk,
            @Parameter(name = "id", required = true) @PathVariable("id") Integer id
    ) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(
            tags = "Объявления",
            summary = "updateComments",
            operationId = "updateComments",
            requestBody = @RequestBody(content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = {
                            @Content(mediaType = "*/*", schema = @Schema(implementation = Comment.class))
                    }),
                    @ApiResponse(responseCode = "404", description = "Not Found"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")
            }
    )
    @PatchMapping("/{ad_pk}/comments/{id}")
    public ResponseEntity<Comment> updateComments(
            @Parameter(name = "ad_pk", required = true) @PathVariable("ad_pk") String adPk,
            @Parameter(name = "id", required = true) @PathVariable("id") Integer id, @RequestBody Comment comment
    ) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(
            operationId = "getAdsMeUsingGET",
            summary = "getAdsMe",
            tags = { "Объявления" },
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = {
                            @Content(mediaType = "*/*", schema = @Schema(implementation = ResponseWrapperAds.class))
                    }),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            },
            parameters = {
                    @Parameter(name = "authenticated"),
                    @Parameter(name = "authorities[0].authority"),
                    @Parameter(name = "credentials"),
                    @Parameter(name = "details"),
                    @Parameter(name = "principal")
            }
    )
    @GetMapping("/me")
    public ResponseEntity<ResponseWrapperAds> getAdsMeUsingGET(
            @RequestParam(value = "authenticated", required = false) Boolean authenticated,
            @RequestParam(value = "authorities[0].authority", required = false) String authorities0Authority,
            @RequestParam(required = false) Object credentials,
            Object details,
            Object principal
    ) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
