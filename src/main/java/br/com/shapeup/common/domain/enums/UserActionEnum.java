package br.com.shapeup.common.domain.enums;

public enum UserActionEnum {
    POST("post", 2L),
    LIKE("like", 1L),
    CREATEACCOUNT("createAccount", 20L),
    ACCEPTFRIENDSHIPREQUEST("acceptfriendshipRequest", 10L),
    SENDFRIENDSHIPREQUEST("sendFriendshipRequest", 10L),
    COMPLETETRAINING("completeTraining");

    private final String action;
    private Long xp;

        UserActionEnum(String action, Long xp) {
        this.action = action;
        this.xp = xp;
    }

    UserActionEnum(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }

    public Long getXp() {
        return xp;
    }

    public static UserActionEnum getByAction(String action) {
        for (UserActionEnum userAction : values()) {
            if (userAction.getAction().equals(action)) {
                return userAction;
            }
        }
        throw new IllegalArgumentException("Invalid action: " + action);
    }
}
