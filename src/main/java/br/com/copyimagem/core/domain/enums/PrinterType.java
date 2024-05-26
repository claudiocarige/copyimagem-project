package br.com.copyimagem.core.domain.enums;

public enum PrinterType {
    LASER_BLACK_AND_WHITE_EASY(0.04),
    LASER_BLACK_AND_WHITE_MEDIUM(0.05),
    LASER_BLACK_AND_WHITE_HARD(0.08),
    LASER_BLACK_AND_WHITE_EXTREME(0.10),
    LASER_COLOR_EASY(0.15),
    LASER_COLOR_MEDIUM(0.25),
    LASER_COLOR_HARD(0.50),
    LASER_COLOR_EXTREME(0.65),
    INKJET_COLOR_EASY(0.15),
    INKJET_COLOR_MEDIUM(0.20),
    INKJET_COLOR_HARD(0.25);

    private final double rate;

    PrinterType(double rate) {
        this.rate = rate;
    }
    public double getRate() {
        return rate;
    }
}
