package com.brujastore.dto;

import com.brujastore.entity.Usuario;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class DireccionDTO {

    private Long id;
    private String tipo;
    private String calle;
    private String numero;
    private String ciudad;
    private String codigoPostal;
    private Boolean esPredeterminado;
    private Boolean estado;

}
