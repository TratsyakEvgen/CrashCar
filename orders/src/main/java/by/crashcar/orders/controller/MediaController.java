package by.crashcar.orders.controller;

import by.crashcar.orders.dto.MediaResponse;
import by.crashcar.orders.service.MediaService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MediaController {
    private final MediaService mediaService;

    @GetMapping(value = "/media/{id}", params = "secret")
    public ResponseEntity<InputStreamResource> get(@PathVariable long id, @Param("secret") String secret) {
        MediaResponse fileReport = mediaService.get(id, secret);
        return ResponseEntity.ok()
                .contentLength(fileReport.getFileSizeInBytes())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header("Content-Disposition", "attachment; filename=\"" + fileReport.getFilename() + "\"")
                .body(fileReport.getInputStreamResource());

    }
}
