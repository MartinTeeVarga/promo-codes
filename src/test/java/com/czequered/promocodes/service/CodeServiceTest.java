package com.czequered.promocodes.service;

import com.czequered.promocodes.model.Code;
import com.czequered.promocodes.repository.CodeRepository;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collections;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;

/**
 * @author Martin Varga
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class CodeServiceTest {

    private CodeService service;

    private CodeRepository codeRepository;

    @Before
    public void before() {
        codeRepository = mock(CodeRepository.class);
        service = new CodeServiceImpl(codeRepository);
    }

    @Test
    public void getCodesTest() throws Exception {
        Code code = new Code();
        code.setGame("test");
        code.setCode("PUB1");
        Mockito.doReturn(new PageImpl<>(Collections.singletonList(code))).when(codeRepository).findAll(any(Pageable.class));
        Page<Code> codes = service.getCodes(0);
        Assertions.assertThat(codes).containsExactly(code);
    }
}