package com.czequered.promocodes.service;

import com.czequered.promocodes.model.Code;
import org.springframework.data.domain.Page;

/**
 * @author Martin Varga
 */
public interface CodeService {
    int PAGE_LIMIT = 20;

    Page<Code> getCodes(int page);

    Code getCode(String game, String code);
}
