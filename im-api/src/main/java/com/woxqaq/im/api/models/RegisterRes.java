package com.woxqaq.im.api.models;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RegisterRes implements Serializable {
    private Long userId;
    private String userName;
}
