package com.example.fragment_tablayout_viewpager.data.model;

import java.io.Serializable;
import java.util.List;

public class MessageModel implements Serializable {
    private boolean success;
    private String message;
    private List<ImagesSlider> result;
    public List<ImagesSlider> getResult() {
        return result;
    }
}