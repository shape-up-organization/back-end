package br.com.shapeup.core.domain.level;

import br.com.shapeup.common.domain.Entity;
import br.com.shapeup.core.domain.validation.ValidationHandler;

public class Level extends Entity<LevelId> {
    private double min;
    private double max;

    public Level( double min, double max) {
        super(LevelId.unique());
        this.min = min;
        this.max = max;
    }
    public static Level newLevel(double min, double max){
        return newLevel(min,max);
    }

    @Override
    public void validate(ValidationHandler handler) {

    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Level level = (Level) o;

        if (Double.compare(level.min, min) != 0) return false;
        return Double.compare(level.max, max) == 0;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        long temp;
        temp = Double.doubleToLongBits(min);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(max);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
