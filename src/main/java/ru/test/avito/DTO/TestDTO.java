package ru.test.avito.DTO;

public class TestDTO {

    private Long id;
    private String name;
    private String name1;

    public TestDTO() {
    }

    public TestDTO(Long id, String name, String name1) {
        this.id = id;
        this.name = name;
        this.name1 = name1;
    }

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
