package com.hofev.securelaunch.modules.blockingUsers;

public class LoginAttemptService {
    private static final LoginAttemptService LOGIN_ATTEMPT_SERVICE = new LoginAttemptService();

    private static final int MAX_ATTEMPTS = 3; // Максимум попыток
    private static final int BLOCK_TIME_MS = 10000; // Время блокировки в миллисекундах

    private int attempts; // Количество текущих попыток
    private long lockEndTime; // Время окончания блокировки (в миллисекундах)

    private LoginAttemptService() {
        this.attempts = 0;
        this.lockEndTime = 0;
    }

    public static LoginAttemptService getInstance() {
        return LOGIN_ATTEMPT_SERVICE;
    }

    // Увеличить количество попыток
    public void incrementAttempts() {
        this.attempts++;
        if (this.attempts >= MAX_ATTEMPTS) {
            lock();
        }
    }

    // Возвращает кол-во оставшихся попыток
    public int getRemainingAttempts() {
        return MAX_ATTEMPTS - attempts;
    }

    // Сбросить попытки
    public void resetAttempts() {
        this.attempts = 0;
    }

    // Проверка, заблокировано ли
    public boolean isLocked() {
        return System.currentTimeMillis() < lockEndTime;
    }

    // Вернуть время оставшейся блокировки
    public long getRemainingLockTime() {
        long remainingTime = lockEndTime - System.currentTimeMillis();
        return Math.max(remainingTime, 0);
    }

    // Заблокировать на определённое время
    private void lock() {
        this.lockEndTime = System.currentTimeMillis() + BLOCK_TIME_MS;
    }
}