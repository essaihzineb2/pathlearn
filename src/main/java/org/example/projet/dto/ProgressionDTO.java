package org.example.projet.dto;

import lombok.Data;

@Data
public class ProgressionDTO {
    private Integer pourcentage; // 0 to 100
    private String notes;
}
