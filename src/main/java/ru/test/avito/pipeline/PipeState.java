package ru.test.avito.pipeline;

public enum PipeState {
    None("None"),
    Start("Start"),
    Customer("Customer"),
    Seller("Seller"),
    ShowAdverts("ShowAdverts");

    String state;

    PipeState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    @Override
    public String toString() {
        return "PipeState{" +
                "state='" + state + '\'' +
                '}';
    }
}
