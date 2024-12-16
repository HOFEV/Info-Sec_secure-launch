package com.hofev.securelaunch.modules.userAccessLevel;

public enum AccessLevel {

    // Права доступа
    USER(true , false),
    EDITOR(true, true),
    ADMIN(false, true);

    private final boolean editingRight;
    private final boolean settingRight;

    AccessLevel(boolean settingRight ,boolean editingRight) {
        this.settingRight = settingRight;
        this.editingRight = editingRight;
    }

    public boolean isEditingRight() {
        return editingRight;
    }

    public boolean isSettingRight() {
        return settingRight;
    }
}
