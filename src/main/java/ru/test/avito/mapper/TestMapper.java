package ru.test.avito.mapper;

import org.modelmapper.ModelMapper;
import ru.test.avito.DTO.TestDTO;
import ru.test.avito.domain.TestEntity;

import java.util.ArrayList;
import java.util.List;

public class TestMapper {
    private static ModelMapper mapper;

    static {
        mapper = new ModelMapper();
    }

    private TestMapper() {
    }

    public static TestEntity toEntity(TestDTO testDTO) {
        return mapper.map(testDTO, TestEntity.class);
    }

    public static TestDTO toDTO(TestEntity testEntity) {
        return mapper.map(testEntity, TestDTO.class);
    }

    public static List<TestEntity> toEntityList(List<TestDTO> testDTOList) {
        List<TestEntity> list = new ArrayList<>();
        for (TestDTO testDTO : testDTOList) {
            list.add(mapper.map(testDTO, TestEntity.class));
        }
        return list;
    }

    public static List<TestDTO> toDTOList(List<TestEntity> testEntityList) {
        List<TestDTO> list = new ArrayList<>();
        for (TestEntity testEntity : testEntityList) {
            list.add(mapper.map(testEntity, TestDTO.class));
        }
        return list;
    }
}
