package com.example.onlineshopdipl.controller;

import com.example.onlineshopdipl.dto.*;
import com.example.onlineshopdipl.entity.Ads;
import com.example.onlineshopdipl.entity.Comment;
import com.example.onlineshopdipl.repository.CommentRepository;
import com.example.onlineshopdipl.service.AdsService;
import com.example.onlineshopdipl.service.CommentService;
import com.example.onlineshopdipl.service.ImageService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
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
import org.springframework.web.server.ResponseStatusException;


import javax.persistence.EntityNotFoundException;
import java.io.IOException;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/ads")
public class AdsController {
    private final AdsService adsService;
    private final CommentService commentService;
    private final ImageService imageService;
    private final CommentRepository commentRepository;
    private static final String IMAGE_PATH = "/ads";

    public AdsController(AdsService adsService, CommentService commentService, ImageService imageService, CommentRepository commentRepository) {
        this.adsService = adsService;
        this.commentService = commentService;
        this.imageService = imageService;
        this.commentRepository = commentRepository;
    }


    @Operation(
            tags = "Объявления",
            operationId = "getAllAds",
            responses = @ApiResponse(responseCode = "200", description = "OK", content =
            @Content(mediaType = "*/*", schema = @Schema(implementation = ResponseWrapperAds.class)))
    )
    @GetMapping()
    public ResponseEntity<ResponseWrapperAds> getAllAds() {
        return ResponseEntity.ok(adsService.getAllAds());
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
    @PreAuthorize("isAuthentificated()")
    @PostMapping()
    public ResponseEntity<AdsDto> addAds(
            @Parameter(name = "properties", required = true) @RequestParam(value = "properties") CreateAds properties,
            @Parameter(name = "image", required = true) @RequestParam(value = "image") MultipartFile image,
            Authentication authentication) throws IOException {
        Integer imagePath = imageService.saveImage(image);

        AdsDto adsDto = adsService.createAds(authentication, properties, image);

        return ResponseEntity.ok(adsDto);
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
    public ResponseWrapperComment getComments(
            @Parameter(name = "ad_pk", in = ParameterIn.PATH, required = true) @PathVariable("ad_pk") Integer adPk
    ) {
        ResponseWrapperComment wrapperComment = commentService.getAllCommentsByAd(adPk);
        return wrapperComment;
    }

    @Operation(tags = "Объявления",
            summary = "addComments",
            operationId = "addComments",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content =
                    @Content(mediaType = "*/*", schema = @Schema(implementation = Comment.class))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            })
    @PreAuthorize("isAuthentificated()")
    @PostMapping(value = "/{ad_pk}/comments")
    public ResponseEntity<CommentDto> addComments(@RequestBody CommentDto commentDto,
            @Parameter(name = "ad_pk", in = ParameterIn.PATH, required = true) @PathVariable("ad_pk") Integer adPk
    ) {
        CommentDto comment = commentService.addComments(commentDto, adPk);
        return ResponseEntity.ok(comment);
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
    public Ads getAds(
            @Parameter(name = "id", required = true) @PathVariable("id") Integer id
    ) {
        Ads ads = adsService.getAds(id);

        if (ads == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return ads;
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
    @PreAuthorize("isAuthentificated()")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeAds(@PathVariable int id, Authentication authentication) {
        adsService.deleteAds(id, authentication);
        return ResponseEntity.noContent().build();
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
    @PreAuthorize("isAuthentificated()")
    @PatchMapping("/{id})")
    public AdsDto updateAds(
            @Parameter(name = "id", required = true) @PathVariable("id") Integer id,
            @Parameter(name = "CreateAds", required = true) @RequestBody CreateAds ads,
            Authentication authentication)
    {
        AdsDto adsDto = adsService.updateAds(authentication, id, ads);
        return adsDto;
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
    public ResponseEntity<CommentDto> getComments_1(
            @Parameter(name = "ad_pk", required = true) @PathVariable("ad_pk") Integer adPk,
            @Parameter(name = "id", required = true) @PathVariable("id") Integer id
    ) {
        CommentDto commentDto = commentService.getComments_1(adPk, id);
        if (commentDto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(commentDto);
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
    @PreAuthorize("isAuthentificated()")
    @DeleteMapping("/{ad_pk}/comments/{id}")
    public ResponseEntity<Void> deleteComments(
            @Parameter(name = "ad_pk", required = true) @PathVariable("ad_pk") Integer adPk,
            @Parameter(name = "id", required = true) @PathVariable("id") Integer id,
            Authentication authentication
    ) {
        commentService.deleteComment(authentication, adPk, id);
        return ResponseEntity.ok().build();
    }

    @Operation(
            tags = "Объявления",
            summary = "updateComments",
            operationId = "updateComments",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = {
                            @Content(mediaType = "*/*", schema = @Schema(implementation = Comment.class))
                    }),
                    @ApiResponse(responseCode = "404", description = "Not Found"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")
            }
    )
    @PreAuthorize("isAuthenticated()")
    @PatchMapping("/{ad_pk}/comments/{id}")
    public ResponseEntity<CommentDto> updateComments(
            @Parameter(name = "ad_pk", required = true) @PathVariable("ad_pk") Integer adPk,
            @Parameter(name = "id", required = true) @PathVariable("id") Integer id, @RequestBody CommentDto commentDto,
            Authentication authentication
    ) {
        CommentDto comment = commentService.updateComments(commentDto, adPk, id, authentication);
        return ResponseEntity.ok(comment);
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
    @PreAuthorize("isAuthentificated()")
    @GetMapping("/me")
    public ResponseEntity<ResponseWrapperAds> getAdsMeUsingGET(Authentication authentication) {
        ResponseWrapperAds wrapperAds = adsService.getMyAds(authentication.getName());
        return ResponseEntity.ok(wrapperAds);
    }

    @PreAuthorize("isAuthenticated()")
    @PatchMapping(value = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
            summary = "updateAdsImage",
            responses = {
                    @ApiResponse(responseCode = "200", useReturnTypeSchema = true),
                    @ApiResponse(responseCode = "204", content = @Content()),
                    @ApiResponse(responseCode = "404", content = @Content())
            }
    )
    public ResponseEntity<byte[]> updateAdsImage(@PathVariable("id") Integer id, @RequestPart MultipartFile image,
                                 Authentication authentication) throws IOException {

        byte[] imageBytes = imageService.updateAdsImage(id, image);
        return ResponseEntity.ok(imageBytes);
    }
}
