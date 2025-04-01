package ru.mipt.home.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StatusResponse {

    private String status;
}
