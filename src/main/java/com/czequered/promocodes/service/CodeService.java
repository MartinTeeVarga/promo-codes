package com.czequered.promocodes.service;

import com.czequered.promocodes.model.Code;

import java.util.List;

/**
 * @author Martin Varga
 */
public interface CodeService {
    List<Code> getCodes(String gameId);

    Code getCode(String gameId, String codeId);

    void deleteCode(String gameId, String codeId);

    Code saveCode(Code code);
}
