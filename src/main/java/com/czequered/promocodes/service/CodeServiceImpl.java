package com.czequered.promocodes.service;

import com.czequered.promocodes.model.Code;
import com.czequered.promocodes.repository.CodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Martin Varga
 */
@Service
public class CodeServiceImpl implements CodeService {
    private CodeRepository codeRepository;

    @Autowired
    public CodeServiceImpl(CodeRepository codeRepository) {
        this.codeRepository = codeRepository;
    }

    @Override
    public List<Code> getCodes(String gameId) {
        return codeRepository.findByGameId(gameId);
    }

    @Override
    public Code getCode(String gameId, String codeId) {
        return codeRepository.findByGameIdAndCodeId(gameId, codeId);
    }

    @Override
    public void deleteCode(String gameId, String codeId) {
        Code toDelete = new Code();
        toDelete.setGameId(gameId);
        toDelete.setCodeId(codeId);
        codeRepository.delete(toDelete);
    }

    @Override
    public Code saveCode(Code code) {
        return codeRepository.save(code);
    }
}
