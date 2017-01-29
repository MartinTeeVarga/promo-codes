package com.czequered.promocodes.service;

import com.czequered.promocodes.model.Code;
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
    private CodeRepository repository;

    @Autowired
    public CodeServiceImpl(CodeRepository repository) {
        this.repository = repository;
    }

    @Override
    public Page<Code> getCodes(int page) {
        return repository.findAll(new PageRequest(page, CodeService.PAGE_LIMIT));
    }
}
