package com.MrCherry.app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Data
public class MpNotification {
    @NotNull
    private String action;
    @NotNull
    private String type;
    @NotNull
    private Data data;

    @NoArgsConstructor
    @Getter
    @Setter
    public static class Data {
        @JsonProperty("id")
        @NotNull
        private Long paymentId;
    }
}
