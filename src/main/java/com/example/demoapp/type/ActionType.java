package com.example.demoapp.type;

import lombok.Getter;

@Getter
public enum ActionType {
    C01_CREATE("作成"), C02_APPLY("再鑑依頼"), C03_APPROVE("再鑑"), C04_REMAND("差し戻し");

    String label;

    ActionType(String label) {
        this.label = label;
    }
}
