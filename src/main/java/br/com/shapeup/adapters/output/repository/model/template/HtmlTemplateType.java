package br.com.shapeup.adapters.output.repository.model.template;

public enum HtmlTemplateType {

    CONFIRM_EMAIL_CODE_VERIFICATION("CONFIRM_EMAIL_CODE_VERIFICATION"),
    RESET_PASSWORD_CODE_VERIFICATION("RESET_PASSWORD_CODE_VERIFICATION");

    private final String value;

    HtmlTemplateType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
