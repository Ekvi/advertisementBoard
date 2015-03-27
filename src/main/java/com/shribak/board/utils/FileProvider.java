package com.shribak.board.utils;


import com.shribak.board.model.Advertisement;

import java.util.List;

public interface FileProvider {
    public List<Advertisement> read(String path);
    public void write(List<Advertisement> advertisements);
}
