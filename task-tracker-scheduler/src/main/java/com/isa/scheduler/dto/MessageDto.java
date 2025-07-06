package com.isa.scheduler.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class MessageDto {
    private String email;
    private String title;
    private String message;
}
