package br.com.shapeup.common.domain.enums;

public enum ClassificationEnum {
    BRONZE,
    SILVER,
    GOLD;

    public java.lang.String getClassification(Integer xp) {
        if (xp <= 80) {
            return BRONZE.name();
        } else if (xp > 80 && xp < 220) {
            return SILVER.name();
        } else {
            return GOLD.name();
        }
    }

    public Integer getMinXp() {
        if (this.equals(BRONZE)) {
            return 0;
        } else if (this.equals(SILVER)) {
            return 80;
        } else {
            return 220;
        }
    }

    public Integer getMaxXp() {
        if (this.equals(BRONZE)) {
            return 80;
        } else if (this.equals(SILVER)) {
            return 220;
        } else {
            return Integer.MAX_VALUE;
        }
    }

    public static ClassificationEnum toEnum(java.lang.String classification) {
        if (classification.equals(BRONZE.name())) {
            return BRONZE;
        } else if (classification.equals(SILVER.name())) {
            return SILVER;
        } else {
            return GOLD;
        }
    }
}
