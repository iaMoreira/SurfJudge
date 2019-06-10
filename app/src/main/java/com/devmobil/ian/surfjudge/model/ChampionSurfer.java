package com.devmobil.ian.surfjudge.model;

public class ChampionSurfer {
    private int id, champion_id, surfer_id;
    private int color;

    public ChampionSurfer() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getChampion_id() {
        return champion_id;
    }

    public void setChampion_id(int champion_id) {
        this.champion_id = champion_id;
    }

    public int getSurfer_id() {
        return surfer_id;
    }

    public void setSurfer_id(int surfer_id) {
        this.surfer_id = surfer_id;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
