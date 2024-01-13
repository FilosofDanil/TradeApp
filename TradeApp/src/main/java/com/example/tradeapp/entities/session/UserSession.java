package com.example.tradeapp.entities.session;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RedisHash("session")
public class UserSession {
    private Long id;
    private Map<String, String> userData;
    private String handler;
}
