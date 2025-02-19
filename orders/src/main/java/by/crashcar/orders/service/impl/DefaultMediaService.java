package by.crashcar.orders.service.impl;

import by.crashcar.core.exception.EntityNotFound;
import by.crashcar.core.exception.ServiceException;
import by.crashcar.orders.dto.MediaResponse;
import by.crashcar.orders.entity.Media;
import by.crashcar.orders.entity.Order;
import by.crashcar.orders.repository.MediaRepository;
import by.crashcar.orders.service.MediaService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class DefaultMediaService implements MediaService {
    private final MediaRepository mediaRepository;
    @Value("${media.storage}")
    private String storage;

    @Override
    public MediaResponse get(long id, String secret) {
        return mediaRepository.findByIdAndSecret(id, secret)
                .map(this::getMediaResponse)
                .orElseThrow(() -> new EntityNotFound("Media mot found"));
    }

    @Override
    public List<Media> save(long orderId, List<MultipartFile> files) {
        return Optional.ofNullable(files)
                .stream()
                .flatMap(Collection::stream)
                .map(file -> saveToStorage(String.valueOf(orderId), file))
                .map(path -> createMedia(orderId, path))
                .map(mediaRepository::save)
                .collect(Collectors.toList());
    }

    private Media createMedia(long orderId, String path) {
        return new Media().setPath(path)
                .setSecret(UUID.randomUUID().toString())
                .setOrder(new Order().setId(orderId));
    }

    private MediaResponse getMediaResponse(Media media) {
        String path = media.getPath();
        try {
            File file = ResourceUtils.getFile(storage + path);
            return new MediaResponse().setFileSizeInBytes(file.length())
                    .setFilename(path)
                    .setInputStreamResource(new InputStreamResource(new FileInputStream(file)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String saveToStorage(String folder, MultipartFile file) {
        String filename = file.getOriginalFilename();
        String path = storage + folder + "/";
        createDirectory(path);
        try (InputStream inputStream = file.getInputStream();
             FileOutputStream fileOutputStream = new FileOutputStream(path + filename)) {
            IOUtils.copy(inputStream, fileOutputStream);
            return folder + "/" + filename;
        } catch (IOException e) {
            throw new ServiceException("Can't save file in storage folder " + folder, e);
        }
    }

    private void createDirectory(String path) {
        try {
            Files.createDirectories(Path.of(path));
        } catch (IOException e) {
            throw new ServiceException("Can't create directory " + path, e);
        }
    }
}
