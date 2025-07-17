package com.brujastore.dto;



import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@Data
public class ReporteVentasDTO {

    private Object periodo;
    private Double totalVentas;

    public ReporteVentasDTO(LocalDate periodo, Double totalVentas) {
        this.periodo = periodo;
        this.totalVentas = totalVentas;
    }

    // Constructor para el reporte mensual (recibe un string como "2025-07")
    public ReporteVentasDTO(String periodo, Double totalVentas) {
        this.periodo = periodo;
        this.totalVentas = totalVentas;
    }
}
