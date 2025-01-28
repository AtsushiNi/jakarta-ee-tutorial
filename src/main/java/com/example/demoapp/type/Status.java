package com.example.demoapp.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Status {
    C01_CREATING("作成中"), C02_WAIT_REVIEW("再鑑待ち"), C03_COMPLETED("完了");

    String label;
}
