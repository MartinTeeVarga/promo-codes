package com.czequered.promocodes.service;

import com.czequered.promocodes.model.Code;
import com.czequered.promocodes.model.CodeCompositeId;
import com.czequered.promocodes.repository.CodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

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
    public Page<Code> getCodes(int page) {
        return codeRepository.findAll(new PageRequest(page, CodeService.PAGE_LIMIT));
    }

    @Override
    public Code getCode(String game, String code) {
        return codeRepository.findByGameIdAndCodeId(game, code);
    }

    @Override public void deleteCode(String game, String code) {
        codeRepository.delete(new CodeCompositeId(game, code));
    }

    @Override public void saveCode(Code code) {
        codeRepository.save(code);
    }
}
