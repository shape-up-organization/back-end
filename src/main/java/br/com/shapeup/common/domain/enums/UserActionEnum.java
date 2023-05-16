package br.com.shapeup.common.domain.enums;

public enum UserActionEnum {
    POST("post", 1),
    LIKE("like", 1),
    CREATEACCOUNT("createAccount", 20),
    ACCEPTFRIENDSHIPREQUEST("acceptfriendshipRequest", 10),
    SENDFRIENDSHIPREQUEST("sendFriendshipRequest", 10),
    COMPLETETRAINING("completeTraining");

    private final String action;
    private int xp;

    UserActionEnum(String action, int xp) {
        this.action = action;
        this.xp = xp;
    }

    UserActionEnum(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }

    public int getXP() {
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
