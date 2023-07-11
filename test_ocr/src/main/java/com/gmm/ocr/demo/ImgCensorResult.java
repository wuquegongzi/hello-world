package com.gmm.ocr.demo;

public class ImgCensorResult {

    private Double threshold;
    private Double deviation;
    private String conclusion;

    public ImgCensorResult(Double threshold, Double deviation, String conclusion) {
        this.threshold = threshold;
        this.deviation = deviation;
        this.conclusion = conclusion;
    }

    public Double getThreshold() {
        return threshold;
    }

    public void setThreshold(Double threshold) {
        this.threshold = threshold;
    }

    public Double getDeviation() {
        return deviation;
    }

    public void setDeviation(Double deviation) {
        this.deviation = deviation;
    }

    public String getConclusion() {
        return conclusion;
    }

    public void setConclusion(String conclusion) {
        this.conclusion = conclusion;
    }
}
