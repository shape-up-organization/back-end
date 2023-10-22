package br.com.shapeup.common.config.kafka;

public enum Topic {
    FRIENDSHIP_REQUEST("tp-friendship-request"),

    CONFIRM_EMAIL_TOPIC("tp-send-email-code-verification"),
    RESET_PASSWORD_TOPIC("tp-send-reset-password-code-verification");

    Topic(String name) {
        this.name = name;
    }

    private String name;

    public String getName() {
        return name;
    }
}
