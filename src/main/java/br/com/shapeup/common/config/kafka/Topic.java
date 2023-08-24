package br.com.shapeup.common.config.kafka;

public enum Topic {
    FRIENDSHIP_REQUEST("tp-friendship-request");

    Topic(String name) {
        this.name = name;
    }

    private String name;

    public String getName() {
        return name;
    }
}
