package ru.test.avito.service;

import org.modelmapper.ModelMapper;
import org.springframework.transaction.annotation.Transactional;
import ru.test.avito.DTO.TestDTO;
import ru.test.avito.domain.TestEntity;
import ru.test.avito.repository.TestRepository;

import java.util.List;
import java.util.Random;

import static ru.test.avito.mapper.TestMapper.toDTO;
import static ru.test.avito.mapper.TestMapper.toDTOList;

//@Service
//@Transactional
public class TestService {

    private TestRepository testRepository;
    private Random random;
    private ModelMapper modelMapper;

    public TestService(TestRepository testRepository) {
        this.testRepository = testRepository;
        random = new Random();
        modelMapper = new ModelMapper();
    }

    @Transactional(readOnly = true)
    public List<TestDTO> getAll() {
        return toDTOList(testRepository.findAll());
    }

    @Transactional(readOnly = true)
    public TestDTO findOne(Long id) {
        return toDTO(testRepository.findById(id).get());
    }

    @Transactional
    public void insertRandom() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < random.nextInt(20); i++) {
            sb.append((char) ('a' + random.nextInt(26)));
        }
        testRepository.save(new TestEntity(sb.toString()));
    }


}
