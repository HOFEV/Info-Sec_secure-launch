package com.hofev.securelaunch.modules.userAccessLevel;

public enum AccessLevel {

    // Права доступа
    USER(false),
    EDITOR(true),
    ADMIN(true);

    private final boolean editingRight;

    AccessLevel(boolean editingRight) {
        this.editingRight = editingRight;
    }

    public boolean isEditingRight() {
        return editingRight;
    }
}
