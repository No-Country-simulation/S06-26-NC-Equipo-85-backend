package com.appbit.health.dto;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CheckinResponse {

    private UUID checkinId;

    private String mensaje;

    @JsonProperty("accion_sugerida")
    private String accionSugerida;

    @JsonProperty("derivar_cvv")
    private Boolean derivarCvv;

    @JsonProperty("nota_actual")
    private Integer notaActual;

    private Boolean alerta;
}
