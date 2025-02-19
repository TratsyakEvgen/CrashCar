package by.crashcar.core.dto;

import lombok.Data;

import java.util.List;

@Data
public class CreateOrderEvent {
    private long id;
    private String phone;
    private String message;
    private List<String> links;
}
