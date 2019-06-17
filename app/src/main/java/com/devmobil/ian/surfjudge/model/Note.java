package com.devmobil.ian.surfjudge.model;

public class Note {
    private int id, wave, champion_surver_id;
    private Double value;

    public Note() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getChampion_surver_id() {
        return champion_surver_id;
    }

    public void setChampion_surver_id(int champion_surver_id) {
        this.champion_surver_id = champion_surver_id;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public int getWave() {
        return wave;
    }

    public void setWave(int wave) {
        this.wave = wave;
    }
}
