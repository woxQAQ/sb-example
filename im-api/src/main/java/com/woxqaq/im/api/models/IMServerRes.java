package com.woxqaq.im.api.models;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class IMServerRes implements Serializable {

    private String ip;
    private Integer cimServerPort;
    private Integer httpPort;
}
