package com.czequered.promocodes.service;

import com.czequered.promocodes.model.Code;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Martin Varga
 */
@Service
@Profile("dev")
@Primary
public class CodeServiceDev implements CodeService {

    Map<Code, Code> localCache;

    public CodeServiceDev() {
        localCache = new HashMap<>();
        for (int i = 1; i <= 99; i++) {
            Code code = new Code();
            String gameId = "GAME-" + (i % 3);
            code.setGameId(gameId);
            String codeId = gameId + "-CODE-" + i;
            code.setCodeId(codeId);
            code.setFrom(Instant.now().minus(i, ChronoUnit.DAYS).toString());
            code.setTo(Instant.now().plus(i, ChronoUnit.DAYS).toString());
            code.setPub(i > 50);
            code.setPayload("Payload: " + gameId + " - " + codeId);
            saveCode(code);
        }
    }

    @Override
    public List<Code> getCodes(String gameId) {
        return localCache.keySet().stream()
                .filter(k -> k.getGameId().equals(gameId))
                .map(localCache::get)
            .collect(Collectors.toList());
    }

    @Override
    public Code getCode(String gameId, String codeId) {
        return localCache.get(new Code(gameId, codeId));
    }

    @Override
    public void deleteCode(String gameId, String codeId) {
        localCache.remove(new Code(gameId, codeId));
    }

    @Override
    public Code saveCode(Code code) {
        Code key = new Code(code.getGameId(), code.getCodeId());
        localCache.put(key, code);
        return code;
    }
}
