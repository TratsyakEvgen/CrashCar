package by.crashcar.orders.service;

import by.crashcar.orders.dto.MediaResponse;
import by.crashcar.orders.entity.Media;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MediaService {
    MediaResponse get(long id, String secret);

    List<Media> save(long orderId, List<MultipartFile> files);
}
