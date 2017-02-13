package com.czequered.promocodes.service;

import com.czequered.promocodes.model.Code;

import java.util.List;

/**
 * @author Martin Varga
 */
public interface CodeService {
    List<Code> getCodes(String game);

    Code getCode(String game, String code);

    void deleteCode(String game, String code);

    void saveCode(Code code);
}
