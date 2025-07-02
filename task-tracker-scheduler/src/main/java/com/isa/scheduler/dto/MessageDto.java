package com.isa.scheduler.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class MessageDto {
    String email;
    String title;
    String message;
}
