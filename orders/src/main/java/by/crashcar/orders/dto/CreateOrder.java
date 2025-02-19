package by.crashcar.orders.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@NotNull
public class CreateOrder {
    @NotBlank
    private String phone;
    private String message;
    private List<MultipartFile> files;
}
