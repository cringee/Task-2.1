package com.example.task21;

public class ConversionFactor {
    private final double factor;
    private final double offset;

    public ConversionFactor(double factor) {
        this.factor = factor;
        this.offset = 0;
    }

    public ConversionFactor(double factor, double offset) {
        this.factor = factor;
        this.offset = offset;
    }

    public double getFactor() {
        return factor;
    }

    public double getOffset() {
        return offset;
    }
}
