package by.crashcar.orders.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.core.io.InputStreamResource;

@Data
@Accessors(chain = true)
public class MediaResponse {
    private long fileSizeInBytes;
    private String filename;
    private InputStreamResource inputStreamResource;
}
